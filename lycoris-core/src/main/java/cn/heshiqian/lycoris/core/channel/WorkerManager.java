package cn.heshiqian.lycoris.core.channel;

import cn.heshiqian.lycoris.core.properties.WorkerConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/3
 */
public class WorkerManager {

    private final WorkerConfig workerConfig;

    public WorkerManager(WorkerConfig workerConfig) {
        this.workerConfig = workerConfig;
        initWorkerThreadPool();
    }

    private void initWorkerThreadPool() {

    }
}
