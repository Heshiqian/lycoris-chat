package cn.heshiqian.lycoris.core.util;

import cn.heshiqian.lycoris.core.exception.LycorisException;

import java.io.File;
import java.io.IOException;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/11
 */
public class FileCounter {

    private static final String COUNTER_FILE_PREFIX = ".count-";
    private static final File userHome = new File(System.getProperty("user.home"));

    private final String name;
    private final String thisCounterFileName;

    public FileCounter(String name) {
        this.name = name;
        this.thisCounterFileName = COUNTER_FILE_PREFIX + name;
        initCounterFile();
    }

    private void initCounterFile() {
        try {
            File cFile = new File(userHome, thisCounterFileName);
            ShutdownRemovableFile shutdownRemovableFile = new ShutdownRemovableFile(cFile);

            shutdownRemovableFile.createNewFile();

        } catch (IOException e) {
            throw new FileCounterException(String.format("Fail to create counter file: [%s]", name), e);
        }
    }

    public String getName() {
        return name;
    }

    public String getThisCounterFileName() {
        return thisCounterFileName;
    }


    static class FileCounterException extends LycorisException {
        public FileCounterException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
