package cn.heshiqian.lycoris.core.channel.impl;

import cn.heshiqian.lycoris.core.channel.ChannelData;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/10/1
 */
public class StringData implements ChannelData<String> {

    private final String privateData;

    public StringData(String privateData) {
        this.privateData = privateData;
    }

    @Override
    public String getData() {
        return privateData;
    }

    @Override
    public Serializable serialize() {
        return privateData.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public ChannelData<String> deserialize(Serializable serialData) {
        String string = guessType(String.class, serialData);
        if (string != null) return new StringData(string);
        // if is not string type, direct cast to byte[], because #serialize() method is return byte[].
        byte[] bytes = (byte[]) serialData;
        return new StringData(new String(bytes, StandardCharsets.UTF_8));
    }

}
