package cn.heshiqian.lycoris.core.message;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface MessageType {

    String getStringType();

    int getIntType();

    /**
     * in 4 array length identity byte;
     * @return byte array;
     */
    byte[] getByteType();

}
