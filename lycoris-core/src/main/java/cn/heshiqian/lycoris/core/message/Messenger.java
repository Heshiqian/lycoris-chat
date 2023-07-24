package cn.heshiqian.lycoris.core.message;

import lombok.Data;
import lombok.ToString;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
@Data
@ToString
public class Messenger {

    private String address;
    private String name;
    private String location;

    public static Messenger of(String name, String address) {
        Messenger messenger = new Messenger();
        messenger.setName(name);
        messenger.setAddress(address);
        messenger.setLocation(name + "@" + address);
        return messenger;
    }

}
