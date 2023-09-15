package cn.heshiqian.lycoris.core.session.impl;

import cn.heshiqian.lycoris.core.session.SessionReader;
import cn.heshiqian.lycoris.core.util.stream.CloseEventInputStream;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
public class ReactiveSessionReader implements SessionReader {

    private static final int MAX_WAIT = 10;
    private static final int INT_OF_END = 255;

    private final InputStream in;
    private final InputStreamSubscriber subscriber;
    private final SubmissionPublisher<byte[]> publisher;
    private final LinkedList<byte[]> waitList = new LinkedList<>();

    public ReactiveSessionReader(InputStream in) {
        publisher = new SubmissionPublisher<>();
        subscriber = new InputStreamSubscriber(waitList);
        publisher.subscribe(subscriber);

        // create looper to handle input read blocked.
        publisher.getExecutor().execute(() -> {
            while (true) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int readByte;
                    while ((readByte = in.read()) != INT_OF_END) {
                        baos.write(readByte);
                    }
                    publisher.submit(baos.toByteArray());
                } catch (IOException ignore) {}
            }
        });

        this.in = new CloseEventInputStream(in, unused -> {
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
    public byte[] read() {
        return waitList.pollFirst();
    }

    @Override
    public boolean hasMore() {
        return !waitList.isEmpty();
    }

    static class InputStreamSubscriber implements Flow.Subscriber<byte[]> {

        private Flow.Subscription subscription;
        private final List<byte[]> waitList;

        public InputStreamSubscriber(List<byte[]> waitList) {
            this.waitList = waitList;
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

        }

        @Override
        public void onComplete() {

        }
    }

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(322);
        Socket accept = serverSocket.accept();
        OutputStream outputStream = accept.getOutputStream();
        while (true) {
            outputStream.write("TTTTTTT".getBytes(StandardCharsets.UTF_8));
            outputStream.write(-1);
            outputStream.flush();
            Thread.sleep(5000);
        }
    }
}
