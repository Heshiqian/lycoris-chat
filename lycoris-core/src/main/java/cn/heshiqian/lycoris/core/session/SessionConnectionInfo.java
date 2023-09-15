package cn.heshiqian.lycoris.core.session;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/9/15
 */
@Getter
@Setter
public class SessionConnectionInfo {

    private String address;
    private byte[] ipAddress;

}
