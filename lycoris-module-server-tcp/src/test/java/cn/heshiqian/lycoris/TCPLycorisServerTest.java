package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.server.LycorisServer;
import cn.heshiqian.lycoris.core.server.factory.StandardLycorisServerFactory;
import cn.heshiqian.lycoris.module.server.tcp.TCPLycorisServer;
import org.junit.jupiter.api.Test;

import javax.swing.*;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2024/1/15
 */
public class TCPLycorisServerTest {

    @Test
    public void testServerServed() throws Exception {
        StandardLycorisServerFactory serverFactory = new StandardLycorisServerFactory(TCPLycorisServer.class);
        LycorisServer server = serverFactory.getServer();

        server.start();

        JOptionPane.showMessageDialog(null, "aaa");
    }

}
