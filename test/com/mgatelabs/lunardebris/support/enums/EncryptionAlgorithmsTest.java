package com.mgatelabs.lunardebris.support.enums;

import junit.framework.TestCase;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class EncryptionAlgorithmsTest extends TestCase {
    public void testIsKeySizeValid() throws Exception {
        assertFalse(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(7));
        assertFalse(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(70));
        assertTrue(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(EncryptionAlgorithms.BLOWFISH.getMinKeySize()));
        assertTrue(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(EncryptionAlgorithms.BLOWFISH.getMaxKeySize()));
        assertFalse(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(EncryptionAlgorithms.BLOWFISH.getMinKeySize() - 1));
        assertFalse(EncryptionAlgorithms.BLOWFISH.isKeySizeValid(EncryptionAlgorithms.BLOWFISH.getMaxKeySize() + 1));

        assertTrue(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMinKeySize()));
        assertTrue(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMaxKeySize()));
        assertFalse(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMinKeySize() - 1));
        assertFalse(EncryptionAlgorithms.DES.isKeySizeValid(EncryptionAlgorithms.DES.getMaxKeySize() + 1));
    }
}
