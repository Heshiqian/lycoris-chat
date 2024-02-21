package cn.heshiqian.lycoris;

import cn.heshiqian.lycoris.core.LycorisVersion;
import cn.heshiqian.lycoris.core.channel.ChannelData;
import cn.heshiqian.lycoris.core.channel.impl.StringData;
import cn.heshiqian.lycoris.core.properties.ManagerConfig;
import cn.heshiqian.lycoris.core.util.stream.NoClosableInputStream;
import cn.heshiqian.lycoris.core.util.stream.NoClosableOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

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
    public void testStringChannelDataCanSerializeAndUnserialize() {
        File tempFile = ((Supplier<File>) () -> {
            try {
                if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                    Path path = Files.createTempFile("lycoris.channel.test.serialize", ".dat", PosixFilePermissions.asFileAttribute(
                            PosixFilePermissions.fromString("rw-------")));
                    return path.toFile();
                }
                return File.createTempFile("lycoris.channel.test.serialize", ".dat");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).get();

        ChannelData<String> channelData = new StringData("test-string");
        Assertions.assertNotNull(channelData);
        Assertions.assertNotNull(channelData.getData());
        System.out.println("channelData.getData() = " + channelData.getData());

        final Serializable serialize = channelData.serialize();
        Assertions.assertNotNull(serialize);

        Assertions.assertDoesNotThrow(() -> {

            try (OutputStream fos = new FileOutputStream(tempFile);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(serialize);
            }
        });

        AtomicReference<Serializable> ref = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(tempFile.exists());

            try (InputStream fis = new FileInputStream(tempFile);
                 ObjectInputStream ois = new ObjectInputStream(fis)){
                Serializable readObject = (Serializable) ois.readObject();
                ref.set(readObject);
            }
        });

        Serializable serializable = ref.get();
        Assertions.assertNotNull(serializable);

        ChannelData<String> data = channelData.deserialize(serializable);
        System.out.println("data = " + data);
        System.out.println("data.getData() = " + data.getData());

        Assertions.assertNotNull(data);
        Assertions.assertNotNull(data.getData());

        Assertions.assertEquals(channelData.getData(), data.getData());

    }

}
