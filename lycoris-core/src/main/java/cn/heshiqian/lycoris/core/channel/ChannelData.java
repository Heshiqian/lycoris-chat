package cn.heshiqian.lycoris.core.channel;

import java.io.Serializable;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public interface ChannelData<T extends Serializable> extends Serializable {

    T getData();

    Serializable serialize();

    ChannelData<T> deserialize(Serializable serialData);

    default <GuessType> GuessType guessType(Class<GuessType> guessType, Serializable serializable) {
        Class<? extends Serializable> serializableClass = serializable.getClass();
        if (guessType.isAssignableFrom(serializableClass)) return guessType.cast(serializable);
        return null;
    }


}
