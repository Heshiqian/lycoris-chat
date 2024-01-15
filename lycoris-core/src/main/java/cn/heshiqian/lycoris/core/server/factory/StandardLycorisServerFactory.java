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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
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
            Constructor<?>[] constructors = serverType.getConstructors();
            Optional<? extends Class<?>> anyExistsConstructor = Arrays.stream(constructors)
                    .filter(constructor -> constructor.getParameterCount() == 1)
                    .filter(constructor -> ServerConfig.class.isAssignableFrom(constructor.getParameters()[0].getType()))
                    .map(constructor -> constructor.getParameters()[0])
                    .map(Parameter::getType)
                    .findAny();

            if (anyExistsConstructor.isPresent()) {
                Class<?> configClass = anyExistsConstructor.get();

                Constructor<?> configClassConstructor = configClass.getConstructor();
                ServerConfig configObj = (ServerConfig) configClassConstructor.newInstance();
                configObj.load(properties);

                Constructor<? extends LycorisServer> constructor = serverType.getConstructor(configClass);
                this.server = constructor.newInstance(configObj);
                this.server.setChannel(standardChannel);
            } else {
                throw new NoSuchMethodException("Not found any constructor with Class<? extends cn.heshiqian.lycoris.core.properties.ServerConfig>");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new LycorisServerException("Init server failure.", e);
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
