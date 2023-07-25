package cn.heshiqian.lycoris.core.server;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/25
 */
public enum BasicProtocol implements Protocol{

    HTTP {
        @Override
        public String protocol() {
            return "http";
        }
    },

    TCP {
        @Override
        public String protocol() {
            return "tcp";
        }
    },

    UDP {
        @Override
        public String protocol() {
            return "udp";
        }
    },

    WEBSOCKET {
        @Override
        public String protocol() {
            return "websocket";
        }
    },

}
