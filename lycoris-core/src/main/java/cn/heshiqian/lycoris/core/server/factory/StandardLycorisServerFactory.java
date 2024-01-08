package cn.heshiqian.lycoris.core.server.factory;

import cn.heshiqian.lycoris.core.channel.WorkerManager;
import cn.heshiqian.lycoris.core.channel.impl.StandardChannel;
import cn.heshiqian.lycoris.core.exception.LycorisServerException;
import cn.heshiqian.lycoris.core.exception.NotAvailableServerException;
import cn.heshiqian.lycoris.core.properties.ServerConfig;
import cn.heshiqian.lycoris.core.properties.WorkerConfig;
import cn.heshiqian.lycoris.core.server.LycorisServer;
import cn.heshiqian.lycoris.core.spi.LycorisServerFactory;
import cn.heshiqian.lycoris.core.util.LycorisPropertyFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public class StandardLycorisServerFactory implements LycorisServerFactory {

    private static final Logger logger = LoggerFactory.getLogger(StandardLycorisServerFactory.class);

    private static final ReentrantLock shardLock = new ReentrantLock();

    private final Class<? extends LycorisServer> serverType;
    private LycorisServer server;

    public StandardLycorisServerFactory(Class<? extends LycorisServer> serverType) {
        this.serverType = serverType;
        shardLock.lock();
        try {
            initServer();
        } catch (LycorisServerException serverException) {
            logger.error("Init/Create server error.", serverException);
        } finally {
            shardLock.unlock();
        }
    }

    private void initServer() throws LycorisServerException{

        // 1. create server channel
        StandardChannel standardChannel = new StandardChannel();

        // 2. find and load properties
        Properties properties = LycorisPropertyFinder.findProperties();

        WorkerConfig workerConfig = new WorkerConfig();
        workerConfig.load(properties);

        WorkerManager workerManager = new WorkerManager(workerConfig);

        workerManager.joinCore(standardChannel);

        try {
            Constructor<? extends LycorisServer> constructor = serverType.getConstructor(ServerConfig.class);
        } catch (NoSuchMethodException e) {
            throw new LycorisServerException("");
        }

    }

    @Override
    public LycorisServer getServer() {
        return server;
    }

    @Override
    public LycorisServer getServer(String specificServerName) throws NotAvailableServerException {
        throw new UnsupportedOperationException("Use #getServer() to get server.");
    }

    @Override
    public LycorisServer getServer(Class<? extends LycorisServer> targetClass) {
        throw new UnsupportedOperationException("Use #getServer() to get server.");
    }
}
