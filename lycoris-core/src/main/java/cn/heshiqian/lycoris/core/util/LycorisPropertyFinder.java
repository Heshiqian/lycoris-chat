package cn.heshiqian.lycoris.core.util;

import cn.heshiqian.lycoris.core.exception.CannotParsePropertyException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public final class LycorisPropertyFinder {

    private static final Logger logger = LoggerFactory.getLogger(LycorisPropertyFinder.class);

    private static final String PROPERTY_FILENAME = "lycoris.properties";
    private static final String CLASSPATH_PROPERTY = PROPERTY_FILENAME;

    public static Properties findProperties(boolean useDefault) {

        String propertyStr = null;

        propertyStr = findClasspath();

        if (propertyStr == null) {
            propertyStr = findInRuntimeFolder();
        }

        if (propertyStr == null && !useDefault) {
            throw new CannotParsePropertyException("Not found property file in classpath or runtime folder.",
                    new FileNotFoundException(PROPERTY_FILENAME));
        }

        Properties properties = new Properties();
        try {
            if (propertyStr != null) {
                properties.load(new StringReader(propertyStr));
            }
        } catch (IOException e) {
            throw new CannotParsePropertyException("Parse property file failure.", e);
        }
        return properties;
    }

    private static String findInRuntimeFolder() {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir, PROPERTY_FILENAME);

        if (file.exists()) {
            try {
                List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

                return String.join("\n", lines);
            } catch (IOException e) {
                logger.warn(String.format("Not find property:[%s] in runtime folder (read from system property:[user.dir])", PROPERTY_FILENAME));
            }

        }

        return null;
    }

    private static String findClasspath() {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CLASSPATH_PROPERTY)) {
            if (stream == null) return null;
            List<String> strings = IOUtils.readLines(stream, StandardCharsets.UTF_8);

            return String.join("\n", strings);
        } catch (IOException e) {
            logger.warn(String.format("Not find property:[%s] in classpath", PROPERTY_FILENAME));
            return null;
        }
    }

}
