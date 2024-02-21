package cn.heshiqian.lycoris.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/7
 */
public final class LycorisServerThreadFactory implements ThreadFactory {

    private static final String THREAD_NAME_PREFIX = "lycoris-thread-";

    private final AtomicInteger threadCount = new AtomicInteger(1);

    public static ThreadFactory getSharedInstance() {
        return ThreadFactoryHolder.SHARED;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(getCountName());
        return thread;
    }

    private String getCountName() {
        return THREAD_NAME_PREFIX + threadCount.getAndIncrement();
    }

    enum ThreadFactoryHolder implements ThreadFactory{

        SHARED {
            final LycorisServerThreadFactory instance = new LycorisServerThreadFactory();

            @Override
            public Thread newThread(Runnable r) {
                return instance.newThread(r);
            }
        },

    }
}
