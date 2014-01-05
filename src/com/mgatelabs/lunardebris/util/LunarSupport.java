package com.mgatelabs.lunardebris.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class LunarSupport {

    /**
     * Stream Helpers
     */

    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        try {
            byte[] bytes = new byte[4096];
            int numBytes;
            while ((numBytes = is.read(bytes)) != -1) {
                os.write(bytes, 0, numBytes);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    /**
     * Security Helpers
     */

    public static SecureRandom getSecureRandom() throws Exception{
        // Make sure we get the right instance
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        // Make sure its been seeded
        random.nextBytes(new byte[4]);
        // Return the instance
        return random;
    }

}
