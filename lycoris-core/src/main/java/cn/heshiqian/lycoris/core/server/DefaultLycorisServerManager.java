package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.exception.NotAvailableServerException;
import cn.heshiqian.lycoris.core.properties.ManagerConfig;
import cn.heshiqian.lycoris.core.spi.LycorisServer;

import java.util.*;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public class DefaultLycorisServerManager extends AbstractLycorisServerManager{

    private final ManagerConfig managerConfig;
    private final List<LycorisServer> serverInstanceList = new ArrayList<>(10);
    private LycorisServer specifiedServer;

    public DefaultLycorisServerManager(ManagerConfig managerConfig) {
        this.managerConfig = managerConfig == null ? new ManagerConfig() : managerConfig;
        discoverAllServerInstance();
    }

    @Override
    public LycorisServer getServer() {
        if (specifiedServer != null) {
            return specifiedServer;
        }

        if (serverInstanceList.isEmpty()) {
            throw new NotAvailableServerException("server count: 0");
        }

        return serverInstanceList.get(0);
    }

    @Override
    public LycorisServer getServer(Class<? extends LycorisServer> instanceClass) {
        Objects.requireNonNull(instanceClass, "provided instance class is null");

        return serverInstanceList.stream()
                .filter(lycorisServer -> instanceClass.equals(lycorisServer.getClass()))
                .findFirst()
                .orElseThrow(() -> new NotAvailableServerException("Not found:[" + instanceClass + "] server instance, is loaded into jvm?"));
    }

    @Override
    public LycorisServer getServer(String instanceClassName) {
        Objects.requireNonNull(instanceClassName, "provided instance class name is null");

        return serverInstanceList.stream()
                .map(lycorisServer -> Collections.singletonMap(lycorisServer.getClass().getTypeName(), lycorisServer))
                .flatMap(serverMap -> serverMap.entrySet().stream())
                .filter(entry -> entry.getKey().equals(instanceClassName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new NotAvailableServerException("Not found:[" + instanceClassName + "] server instance, is loaded into jvm?"));
    }

    private void discoverAllServerInstance() {
        // By default, load from SPI.
        serverInstanceList.addAll(spiDiscover());

        // check configuration and load some server.
        String targetServer = managerConfig.get(ManagerConfig.PROP_KEY_TARGET_SERVER, () -> null, String.class);
        if (targetServer != null && targetServer.trim().length() != 0) {
            LycorisServer lycorisServer = checkAndLoadClassFromClassName(targetServer);
            if (lycorisServer != null) {
                serverInstanceList.add(lycorisServer);
                // getServer() will return this instance;
                specifiedServer = lycorisServer;
            }
        }
    }

    private LycorisServer checkAndLoadClassFromClassName(String targetServer) {
        try {
            Class<?> foundClass = Class.forName(targetServer);
            if (LycorisServer.class.isAssignableFrom(foundClass)) {
                // simple create instance.
                return (LycorisServer) foundClass.newInstance();
            }
        } catch (Exception ignore) { }
        return null;
    }
}
