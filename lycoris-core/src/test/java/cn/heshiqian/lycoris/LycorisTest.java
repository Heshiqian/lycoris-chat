package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.message.Message;
import cn.heshiqian.lycoris.core.message.MessageType;
import cn.heshiqian.lycoris.core.message.Messenger;
import cn.heshiqian.lycoris.core.properties.ManagerConfig;
import cn.heshiqian.lycoris.core.server.tcp.TCPLycorisServer;
import cn.heshiqian.lycoris.core.server.tcp.TCPServerConfig;
import cn.heshiqian.lycoris.core.session.impl.ReactiveSessionReader;
import cn.heshiqian.lycoris.core.util.stream.NoClosableInputStream;
import cn.heshiqian.lycoris.core.util.stream.NoClosableOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public class LycorisTest {

    @BeforeAll
    public static void helloScreen() {
        System.out.println("LycorisVersion.MAIN_VERSION = " + LycorisVersion.MAIN_VERSION);
        System.out.println("LycorisVersion.SUB_VERSION = " + LycorisVersion.SUB_VERSION);
        System.out.println("LycorisVersion.RELEASE_TIMES = " + LycorisVersion.RELEASE_TIMES);
        System.out.println("LycorisVersion.VER = " + LycorisVersion.VER);

        System.out.println(LycorisVersion.BANNER);
    }

    @Test
    public void testLycorisVersionCanNormallyPrintToSystemConsole() {
        // invoke @BeforeAll method
        helloScreen();
    }


    @Test
    public void testLycorisPropertyCanLoadCorrect() {
        Properties properties = new Properties();
        properties.setProperty(ManagerConfig.PROP_KEY_TARGET_SERVER, "LoadFromProperty");

        // 1. load from property
        ManagerConfig loadProperty = new ManagerConfig();
        loadProperty.load(properties);

        Assertions.assertNotNull(loadProperty.get(ManagerConfig.PROP_KEY_TARGET_SERVER));
        Assertions.assertEquals(loadProperty.get(ManagerConfig.PROP_KEY_TARGET_SERVER), "LoadFromProperty");

        // 2. load from string
        String propertyStr = ManagerConfig.PROP_KEY_TARGET_SERVER + "=LoadFromString";
        ManagerConfig loadString = new ManagerConfig();
        loadString.load(propertyStr);

        Assertions.assertNotNull(loadString.get(ManagerConfig.PROP_KEY_TARGET_SERVER));
        Assertions.assertEquals(loadString.get(ManagerConfig.PROP_KEY_TARGET_SERVER), "LoadFromString");

    }

    @Test
    public void testNoClosableStreamWhenOnCloseCalledDoesNotThrow() {
        InputStream inputStream = new NoClosableInputStream(InputStream.nullInputStream());
        OutputStream outputStream = new NoClosableOutputStream(OutputStream.nullOutputStream());
        Assertions.assertDoesNotThrow(inputStream::close);
        Assertions.assertDoesNotThrow(outputStream::close);
    }

    @Test
    public void testNoClosableStreamWhenOnCloseCalledThrowException() {
        InputStream inputStream = new NoClosableInputStream(InputStream.nullInputStream());
        OutputStream outputStream = new NoClosableOutputStream(OutputStream.nullOutputStream());
        Assertions.assertNotNull(inputStream);
        Assertions.assertNotNull(outputStream);
        // This test need go to 'cn/heshiqian/lycoris/core/util/stream/package-info.java'
        // To set 'cn.heshiqian.lycoris.core.util.stream.CloseMethodPolicy#thrown' is true
        // Assertions.assertThrows(RuntimeException.class, inputStream::close);
        // Assertions.assertThrows(RuntimeException.class, outputStream::close);
    }

    @Test
    public void testTCPServer() throws Exception{
        TCPServerConfig tcpServerConfig = new TCPServerConfig();
        tcpServerConfig.load("");
        TCPLycorisServer tcpLycorisServer = new TCPLycorisServer(tcpServerConfig);
        tcpLycorisServer.start();
        Thread.sleep(Duration.ofDays(1).toMillis());
    }

    @Test
    public void testReader() throws Exception{
        Socket socket = new Socket("127.0.0.1", 322);
        ReactiveSessionReader reader = new ReactiveSessionReader(socket.getInputStream());

        while (true) {
            if (reader.hasMore()) {
                System.err.println("reader.read() = " + new String(reader.read()));
                continue;
            }
            Thread.sleep(200);
        }
    }
}
