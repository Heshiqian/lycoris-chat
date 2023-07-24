package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.message.Message;
import cn.heshiqian.lycoris.core.message.MessageType;
import cn.heshiqian.lycoris.core.message.Messenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
}
