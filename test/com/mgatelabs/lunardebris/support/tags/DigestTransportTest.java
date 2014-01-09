package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.MessageDigestAlgorithms;
import junit.framework.TestCase;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public class DigestTransportTest extends TestCase {
    public void testMD5() throws Exception {
        byte [] sample = new byte [] {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07};
        DigestTransport dt = new DigestTransport();
        dt.setAlgorithm(MessageDigestAlgorithms.MD5);
        dt.setDigest(dt.digest(sample));
        assertTrue(dt.verify(sample));
    }

    public void testSHA1() throws Exception {
        byte [] sample = new byte [] {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07};
        DigestTransport dt = new DigestTransport();
        dt.setAlgorithm(MessageDigestAlgorithms.SHA1);
        dt.setDigest(dt.digest(sample));
        assertTrue(dt.verify(sample));
    }

    public void testSHA256() throws Exception {
        byte [] sample = new byte [] {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07};
        DigestTransport dt = new DigestTransport();
        dt.setAlgorithm(MessageDigestAlgorithms.SHA256);
        dt.setDigest(dt.digest(sample));
        assertTrue(dt.verify(sample));
    }
}
