package cn.heshiqian.lycoris;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/24
 */
public interface LycorisVersion {

    int MAIN_VERSION = 1;

    int SUB_VERSION = 0;

    int RELEASE_TIMES = 0;

    String VER = MAIN_VERSION + "." + SUB_VERSION + "." + RELEASE_TIMES;

    String BANNER = "           ^               ^\n" +
            "           :        *      :\n" +
            "       ^   ::   **  * *    :  ^\n" +
            "       ::   ::  ** *  *  ::   :\n" +
            "         ::::::::***** :::  ::\n" +
            "Lycoris Chat :::::**::::::: " + "Ver." + VER;

}
