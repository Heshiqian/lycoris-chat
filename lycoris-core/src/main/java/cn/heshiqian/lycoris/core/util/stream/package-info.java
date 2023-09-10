/**
 * This package provide a type of InputStream/OutputStream,
 * and is not do anything when {@link java.io.Closeable#close()} method called.
 */
package cn.heshiqian.lycoris.core.util.stream;

import java.io.Closeable;

/**
 * When {@link Closeable#close()} method is called, control program how to do.
 */
class CloseMethodPolicy {
    /**
     * Thrown exception
     */
    private static final boolean thrown = false;

    static void handleCloseCall() {
        if (thrown) {
            throw new RuntimeException("This stream NOT allow close.");
        }
    }
}