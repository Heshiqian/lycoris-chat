package cn.heshiqian.lycoris.core.server.connection.impl;

import cn.heshiqian.lycoris.core.server.connection.ConnectionReader;
import cn.heshiqian.lycoris.core.util.stream.CloseEventInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class ReactiveConnectionReader implements ConnectionReader {

    private static final int MAX_BYTES_READ = 4096;
    private static final int MAX_WAIT = 10;

    private final InputStream in;
    private final AtomicBoolean loop = new AtomicBoolean(true);
    private final SubmissionPublisher<byte[]> publisher;
    private final ArrayList<byte[]> waitList = new ArrayList<>();

    private Throwable exception;

    public ReactiveConnectionReader(InputStream in) {
        this(in, MAX_BYTES_READ);
    }

    public ReactiveConnectionReader(InputStream in, int maxByte) {
        publisher = new SubmissionPublisher<>();

        InputStreamSubscriber subscriber
                = new InputStreamSubscriber(waitList, throwable -> exception = throwable);

        publisher.subscribe(subscriber);

        // create looper to handle input read blocked.
        publisher.getExecutor().execute(() -> {
            byte[] tinyBuffer = new byte[maxByte];
            int readBytes = 0;

            try {
                while (loop.get()) {
                    while ((readBytes = in.read(tinyBuffer)) != -1) {
                        // normally write buffer
                        publisher.submit(Arrays.copyOf(tinyBuffer, readBytes));
                    }
                }
            } catch (IOException ioException) {
                publisher.closeExceptionally(ioException);
            }
        });

        this.in = new CloseEventInputStream(in, unused -> {
            loop.compareAndSet(false, true);
            if (!publisher.isClosed()) {
                publisher.close();
            }
        });
    }

    @Override
    public InputStream getInput() {
        return in;
    }

    @Override
    public byte[] read() throws IOException{
        exceptionThrown();
        return waitList.size() > 0 ? waitList.get(0) : null;
    }

    @Override
    public boolean hasMore() {
        try {
            exceptionThrown();
        } catch (IOException e) {
            // warp with RuntimeException, for don't need to modify method signature.
            throw new RuntimeException(e);
        }
        return !waitList.isEmpty();
    }

    private void exceptionThrown() throws IOException{
        if (exception != null) throw new IOException(exception);
    }

    static class InputStreamSubscriber implements Flow.Subscriber<byte[]> {

        private Flow.Subscription subscription;
        private final List<byte[]> waitList;
        private final Consumer<Throwable> throwableConsumer;

        public InputStreamSubscriber(List<byte[]> waitList, Consumer<Throwable> throwableConsumer) {
            this.waitList = waitList;
            this.throwableConsumer = throwableConsumer;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            // request next
            subscription.request(1);
        }

        @Override
        public void onNext(byte[] item) {
            waitList.add(item);
            if (waitList.size() <= MAX_WAIT) {
                subscription.request(1);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            throwableConsumer.accept(throwable);
        }

        @Override
        public void onComplete() {}
    }

}
