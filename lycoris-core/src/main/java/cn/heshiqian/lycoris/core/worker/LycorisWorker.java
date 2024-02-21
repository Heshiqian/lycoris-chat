package cn.heshiqian.lycoris.core.worker;

import cn.heshiqian.lycoris.core.LycorisServerThreadFactory;
import cn.heshiqian.lycoris.core.exception.WorkerPoolException;
import cn.heshiqian.lycoris.core.properties.WorkerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/3
 */
public class LycorisWorker {

    private static final Logger logger = LoggerFactory.getLogger(LycorisWorker.class);

    private static final int STATUS_CREATE = 0;
    private static final int STATUS_RUNNING = 1;
    private static final int STATUS_SHUTDOWN = 2;

    private final WorkerConfig workerConfig;
    private final AtomicInteger loopStatus = new AtomicInteger(0);
    private final ThreadPoolExecutor corePool;
    private Thread mainLooper;
    private volatile Runnable mainRunnable;

    public LycorisWorker(WorkerConfig workerConfig) {
        this.workerConfig = workerConfig;

        int coreSize = workerConfig.getCoreSize();
        corePool = new ThreadPoolExecutor(coreSize,
                coreSize,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(coreSize),
                LycorisServerThreadFactory.getSharedInstance(),
                (r, executor) -> {
                    throw new WorkerPoolException("Core pool size: " + coreSize + " is up to max.");
                });
        initMainLooper();
        initWorkerThreadPool();
    }

    public void loopInMain(Runnable runnable) {
        mainRunnable = runnable;
    }

    public void shutdown() {
        loopStatus.compareAndSet(STATUS_RUNNING, STATUS_SHUTDOWN);
        mainLooper.interrupt();
    }

    private void initMainLooper() {
        mainLooper = new Thread(() -> {
            if (loopStatus.get() == STATUS_SHUTDOWN) {
                return;
            }
            loopStatus.compareAndSet(STATUS_CREATE, STATUS_RUNNING);

            while (mainRunnable == null) {
                Thread.onSpinWait();
            }

            while (loopStatus.get() == STATUS_RUNNING) {
                if (loopStatus.get() == STATUS_SHUTDOWN) {
                    break;
                }
                try {
                    mainRunnable.run();
                } catch (Exception e) {
                    logger.error("Main looper throws exception.", e);
                }
            }
        });
        mainLooper.setName("lycoris-worker-main");
        mainLooper.start();
    }

    private void initWorkerThreadPool() {
        corePool.prestartCoreThread();
        int coreSize = workerConfig.getCoreSize();
        for (int i = 0; i < coreSize; i++) {

        }
    }
}
