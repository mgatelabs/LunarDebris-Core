package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.MacAlgorithms;
import junit.framework.TestCase;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public class MacTransportTest extends TestCase {

    public void testMD5() throws Exception{
        byte [] key = "key".getBytes("UTF-8");
        byte [] sample = new byte [] {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07};

        MacTransport mc = new MacTransport();
        mc.setAlgorithm(MacAlgorithms.HMD5);
        mc.setHmac(mc.digest(sample, key));

        assertTrue(mc.verify(sample, key));
    }

    public void testSHA256() throws Exception{
        byte [] key = "key".getBytes("UTF-8");
        byte [] sample = "The quick brown fox jumps over the lazy dog".getBytes("UTF-8");
        byte [] correct = new byte [] {(byte)0xf7,(byte)0xbc,(byte)0x83,(byte)0xf4,0x30,0x53,(byte)0x84,0x24,(byte)0xb1,0x32,(byte)0x98,(byte)0xe6,(byte)0xaa,0x6f,(byte)0xb1,0x43,(byte)0xef,0x4d,0x59,(byte)0xa1,0x49,0x46,0x17,0x59,(byte)0x97,0x47,(byte)0x9d,(byte)0xbc,0x2d,0x1a,0x3c,(byte)0xd8};

        MacTransport mc = new MacTransport();
        mc.setAlgorithm(MacAlgorithms.HSHA256);
        mc.setHmac(mc.digest(sample, key));

        assertTrue(mc.verify(sample, key));
        assertFalse(mc.verify(sample, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00}));
        assertEquals(correct.length, mc.getHmac().length);

        for (int i = 0; i < correct.length; i++) {
            assertEquals(correct[i], mc.getHmac()[i]);
        }
    }
}
