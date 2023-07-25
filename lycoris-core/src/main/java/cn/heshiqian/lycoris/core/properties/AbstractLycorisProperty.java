package cn.heshiqian.lycoris.core.properties;

import java.util.Properties;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public abstract class AbstractLycorisProperty implements LycorisProperty{

    private final Properties prop = new Properties();

    public AbstractLycorisProperty() {
        initDefault();
    }

    protected abstract void initDefault();


}
