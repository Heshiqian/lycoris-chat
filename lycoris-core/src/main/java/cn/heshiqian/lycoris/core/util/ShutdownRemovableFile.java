package cn.heshiqian.lycoris.core.util;

import java.io.File;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/8/11
 */
public final class ShutdownRemovableFile extends File{

    private final File originalFile;

    public ShutdownRemovableFile(String filePath) {
        this(new File(filePath));
    }

    public ShutdownRemovableFile(File originalFile) {
        super(originalFile.getAbsolutePath());
        this.originalFile = originalFile;
        this.originalFile.deleteOnExit();
    }

    public File getOriginalFile() {
        return originalFile;
    }
}
