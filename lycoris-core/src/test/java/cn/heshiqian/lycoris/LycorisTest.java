package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.exception.NotAvailableServerException;
import cn.heshiqian.lycoris.core.message.Message;
import cn.heshiqian.lycoris.core.message.MessageType;
import cn.heshiqian.lycoris.core.message.Messenger;
import cn.heshiqian.lycoris.core.properties.ManagerConfig;
import cn.heshiqian.lycoris.core.server.DefaultLycorisServerManager;
import cn.heshiqian.lycoris.core.session.Session;
import cn.heshiqian.lycoris.core.spi.LycorisChannel;
import cn.heshiqian.lycoris.core.spi.LycorisServer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
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
    public void testManuallyNewInstanceOfMessage() {
        TestMessage testMessage = new TestMessage();
        Assertions.assertNotNull(testMessage);
        System.out.println("testMessage = " + testMessage);

        Messenger from = testMessage.getFrom();
        Messenger to = testMessage.getTo();
        Assertions.assertNotNull(from);
        Assertions.assertNotNull(to);

        System.out.println("testMessage.getFrom() = " + from);
        System.out.println("testMessage.getTo() = " + to);

        System.out.println("\tfrom.getUri() = " + from.getLocation());
        System.out.println("\tto.getUri() = " + to.getLocation());

        MessageType messageType = testMessage.getType();
        Assertions.assertNotNull(messageType);

        System.out.println("messageType = " + messageType);
        byte[] byteType = messageType.getByteType();
        String stringType = messageType.getStringType();
        int intType = messageType.getIntType();
        System.out.println("\tstringType = " + stringType);
        System.out.println("\tintType = " + intType);
        System.out.println("\tbyteType = " + Arrays.toString(byteType));

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
    public void testLycorisServerManagerCanInstanceSomeServer() {
        Properties properties = new Properties();
        properties.setProperty(ManagerConfig.PROP_KEY_TARGET_SERVER, "cn.heshiqian.lycoris.LycorisTest$TestLycorisServer");
        ManagerConfig managerConfig = new ManagerConfig();
        managerConfig.load(properties);

        // 1. test all default.
        DefaultLycorisServerManager defaultLycorisServerManager = new DefaultLycorisServerManager(null);
        LycorisServer server = defaultLycorisServerManager.getServer();
        Assertions.assertNotNull(server);

        // 2. test specific.
        DefaultLycorisServerManager specificManager = new DefaultLycorisServerManager(managerConfig);
        LycorisServer specificServer = specificManager.getServer();
        Assertions.assertNotNull(specificServer);
        Assertions.assertEquals(specificServer.getClass(), TestLycorisServer.class);

        // 3. test not found
        Assertions.assertThrows(NotAvailableServerException.class, () -> {
            defaultLycorisServerManager.getServer("cn.heshiqian.lycoris.LycorisTest$TestLycorisServer");
        });
        Assertions.assertThrows(NotAvailableServerException.class, () -> {
            defaultLycorisServerManager.getServer(TestLycorisServer.class);
        });

        // 4. test null
        Assertions.assertThrows(NullPointerException.class, () -> {
            // force set invoke getServer(Class) method
            defaultLycorisServerManager.getServer((Class<? extends LycorisServer>) null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            // force set invoke getServer(String) method
            defaultLycorisServerManager.getServer((String) null);
        });

        // 5. test not have any server
        DefaultLycorisServerManager emptyManager = new DefaultLycorisServerManager(null);
        hackSetServerListToEmpty(emptyManager);
        Assertions.assertThrows(NotAvailableServerException.class, emptyManager::getServer);
    }

    @SuppressWarnings("unchecked")
    private void hackSetServerListToEmpty(DefaultLycorisServerManager emptyManager) {
        try {
            Field serverInstanceList = emptyManager.getClass().getDeclaredField("serverInstanceList");
            serverInstanceList.setAccessible(true);
            List<LycorisServer> lycorisServers = (List<LycorisServer>) serverInstanceList.get(emptyManager);
            lycorisServers.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class TestMessage implements Message {

        @Override
        public Messenger getFrom() {
            return Messenger.of("alice", "192.168.1.2");
        }

        @Override
        public Messenger getTo() {
            return Messenger.of("bob", "192.168.1.3");
        }

        @Override
        public MessageType getType() {
            return new TestMessageType();
        }

        static class TestMessageType implements MessageType {

            @Override
            public String getStringType() {
                return "test";
            }

            @Override
            public int getIntType() {
                return 0;
            }

            @Override
            public byte[] getByteType() {
                return new byte[]{0x00,0x00,0x00,0x00};
            }
        }
    }

    public static class TestLycorisServer implements LycorisServer {

        @Override
        public void start() {

        }

        @Override
        public void shutdown() {

        }

        @Override
        public String getServerName() {
            return null;
        }

        @Override
        public void setChannel(LycorisChannel channel) {

        }

    }
}
