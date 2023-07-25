package cn.heshiqian.lycoris.core.server;

import cn.heshiqian.lycoris.core.spi.LycorisServer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public abstract class AbstractLycorisServerManager {

    public abstract LycorisServer getServer();

    public abstract LycorisServer getServer(Class<? extends LycorisServer> instanceClass);

    public abstract LycorisServer getServer(String instanceClassName);

    /**
     * <p>Get {@link LycorisServer} from {@link ServiceLoader}.</p>
     * Returned all available instance list.
     * @return all available {@link LycorisServer} instance.
     */
    protected List<LycorisServer> spiDiscover() {
        ServiceLoader<LycorisServer> lycorisServers = ServiceLoader.load(LycorisServer.class);

        List<LycorisServer> instanceList = new ArrayList<>(10);

        for (LycorisServer someAvailableInstance : lycorisServers) {
            instanceList.add(someAvailableInstance);
        }

        return instanceList;
    }





}
