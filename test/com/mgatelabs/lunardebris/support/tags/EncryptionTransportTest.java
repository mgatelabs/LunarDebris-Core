package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.enums.EncryptionKeyTypes;
import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.util.EncryptionUtils;
import com.mgatelabs.lunardebris.util.LunarSupport;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Created by MiniMegaton on 1/6/14.
 */
public class EncryptionTransportTest extends TestCase {

    public void testBlowfishEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.Blowfish, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding, EncryptionAlgorithms.Blowfish.getMinKeySize());
        tryEncryption(et);
    }

    public void testAESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.AES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding, EncryptionAlgorithms.AES.getMinKeySize());
        tryEncryption(et);
    }

    public void testDESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.DES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding, EncryptionAlgorithms.DES.getMinKeySize());
        tryEncryption(et);
    }

    public void testTripleDESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.TripleDES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding, EncryptionAlgorithms.TripleDES.getMinKeySize());
        tryEncryption(et);
    }

    public void tryEncryption(EncryptionTransport et) throws Exception {
        byte [] sampleData = new byte [256];
        for (int i = 0; i < 256; i++) {
            sampleData[i] = (byte) i;
        }

        ByteArrayInputStream encodeInputStream = new ByteArrayInputStream(sampleData);
        ByteArrayOutputStream encodedOutputStream = new ByteArrayOutputStream(1024);

        et.encrypt(encodeInputStream, encodedOutputStream);

        encodeInputStream = new ByteArrayInputStream(encodedOutputStream.toByteArray());
        encodedOutputStream.reset();

        et.decrypt(encodeInputStream, encodedOutputStream);

        byte [] decodedData = encodedOutputStream.toByteArray();

        assertEquals(sampleData.length, decodedData.length);

        for (int i = 0; i < 256; i++) {
            assertEquals(sampleData[i], decodedData[i]);
        }
    }

    public void tryPKEncryption(EncryptionTransport pri, EncryptionTransport pub) throws Exception {
        byte [] sampleData = new byte [(pri.getKeySize()/8) - 12];
        for (int i = 0; i < sampleData.length; i++) {
            sampleData[i] = (byte) i;
        }

        System.out.println("Testing " + pri.getKeySize() + " bit key with " + sampleData.length + " bytes of data");

        ByteArrayInputStream encodeInputStream = new ByteArrayInputStream(sampleData);
        ByteArrayOutputStream encodedOutputStream = new ByteArrayOutputStream(1024);

        pri.encrypt(encodeInputStream, encodedOutputStream);

        encodeInputStream = new ByteArrayInputStream(encodedOutputStream.toByteArray());
        encodedOutputStream.reset();

        pub.decrypt(encodeInputStream, encodedOutputStream);

        byte [] decodedData = encodedOutputStream.toByteArray();

        assertEquals(sampleData.length, decodedData.length);

        for (int i = 0; i < sampleData.length; i++) {
            assertEquals(sampleData[i], decodedData[i]);
        }
    }

    public void testRSA512() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1Padding, 512);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRIVATE_KEY), keys.get(EncryptionKeyTypes.PUBLIC_KEY));
    }

    public void testRSA1024() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1Padding, 1024);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRIVATE_KEY), keys.get(EncryptionKeyTypes.PUBLIC_KEY));
    }

    public void testRSA2048() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1Padding, 2048);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRIVATE_KEY), keys.get(EncryptionKeyTypes.PUBLIC_KEY));
    }

    public void testRSA4096() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1Padding, 4096);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRIVATE_KEY), keys.get(EncryptionKeyTypes.PUBLIC_KEY));
    }

}
