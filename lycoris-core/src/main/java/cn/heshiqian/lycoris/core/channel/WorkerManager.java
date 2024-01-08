package cn.heshiqian.lycoris.core.channel;

import cn.heshiqian.lycoris.core.exception.WorkerPoolException;
import cn.heshiqian.lycoris.core.properties.WorkerConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/3
 */
public class WorkerManager {

    private final WorkerConfig workerConfig;
    private final AtomicInteger loopStatus = new AtomicInteger(0);
    private final ThreadPoolExecutor corePool;

    private Thread mainLooper;

    public WorkerManager(WorkerConfig workerConfig) {
        this.workerConfig = workerConfig;

        int coreSize = workerConfig.getCoreSize();
        corePool = new ThreadPoolExecutor(coreSize,
                coreSize,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(coreSize),
                (r, executor) -> {
                    throw new WorkerPoolException("Core pool size: " + coreSize + " is up to max.");
                });
        initMainLooper();
        initWorkerThreadPool();
    }

    public void joinCore(Runnable runnable) {
        corePool.submit(runnable);
    }

    public void joinLooper(Runnable runnable) {
        if (!mainLooper.isAlive() && loopStatus.get() <= 1) {

        }
    }

    private void initMainLooper() {
        mainLooper = new Thread(() -> {
            // in SET
            loopStatus.compareAndSet(0, 1);







        });
        mainLooper.setName("worker-main");
    }

    private void initWorkerThreadPool() {

    }
}
