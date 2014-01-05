package com.mgatelabs.lunardebris.support.enums;

import junit.framework.TestCase;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class EncryptionAlgorithmsTest extends TestCase {
    public void testIsKeySizeValid() throws Exception {
        assertFalse(EncryptionAlgorithms.Blowfish.isKeySizeValid(7));
        assertFalse(EncryptionAlgorithms.Blowfish.isKeySizeValid(70));
        assertTrue(EncryptionAlgorithms.Blowfish.isKeySizeValid(EncryptionAlgorithms.Blowfish.getMinKeySize()));
        assertTrue(EncryptionAlgorithms.Blowfish.isKeySizeValid(EncryptionAlgorithms.Blowfish.getMaxKeySize()));
        assertFalse(EncryptionAlgorithms.Blowfish.isKeySizeValid(EncryptionAlgorithms.Blowfish.getMinKeySize() - 1));
        assertFalse(EncryptionAlgorithms.Blowfish.isKeySizeValid(EncryptionAlgorithms.Blowfish.getMaxKeySize() + 1));

        assertTrue(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMinKeySize()));
        assertTrue(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMaxKeySize()));
        assertFalse(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMinKeySize() - 1));
        assertFalse(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMaxKeySize() + 1));
    }
}
