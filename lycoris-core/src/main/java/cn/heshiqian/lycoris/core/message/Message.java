package cn.heshiqian.lycoris.core.message;

import java.io.Serializable;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface Message extends Serializable {

    Messenger getFrom();

    Messenger getTo();

    MessageType getType();


}
