package cn.heshiqian.lycoris.core.spi;

import cn.heshiqian.lycoris.core.exception.NotAvailableServerException;
import cn.heshiqian.lycoris.core.server.LycorisServer;

/**
 * This interface provide some way to get or create {@link LycorisServer}.
 * And this class is expose to SPI.
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/4
 */
public interface LycorisServerFactory {

    /**
     * <p>Return a default available server.</p>
     * If this factory contains multi server instances, the return's instance it will depend on subclass behaviour.
     * @return A default available server. It can be null, when not have any available server.
     */
    LycorisServer getServer();

    /**
     * <p>Return a server </p>
     * @param specificServerName specific server name, will use {@link LycorisServer#getServerName()} return value.
     * @return A specific server.
     * @throws NotAvailableServerException Not match any server instance by input name.
     */
    LycorisServer getServer(String specificServerName) throws NotAvailableServerException;

    /**
     * <p>Specify instance implements by {@link LycorisServer} interface.</p>
     * If
     * @param targetClass A class extends {@link LycorisServer}.
     * @return The parameter specify server instance.
     */
    LycorisServer getServer(Class<? extends LycorisServer> targetClass);

}
