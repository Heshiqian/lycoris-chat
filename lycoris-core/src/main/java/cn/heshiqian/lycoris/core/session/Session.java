package cn.heshiqian.lycoris.core.session;

import java.io.Serializable;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface Session extends Serializable {

    String getAddress();

    String getId();

    String getName();





}
