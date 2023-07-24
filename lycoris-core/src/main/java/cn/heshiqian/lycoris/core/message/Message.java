package cn.heshiqian.lycoris.core.message;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface Message {

    Messenger getFrom();

    Messenger getTo();

    MessageType getType();


}
