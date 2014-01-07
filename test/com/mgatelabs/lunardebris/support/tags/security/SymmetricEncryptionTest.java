package com.mgatelabs.lunardebris.support.tags.security;

import com.mgatelabs.bytemapper.support.io.FormatIO;
import com.mgatelabs.bytemapper.util.BMResult;
import com.mgatelabs.bytemapper.util.loaders.SimpleFormatLoader;
import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.tags.SymmetricEncryption;
import com.mgatelabs.lunardebris.util.LunarSupport;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public class SymmetricEncryptionTest extends TestCase {

    public void testBlowfishMinKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.Blowfish, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey();
        tryEncryption(en);
    }

    public void testAESMinKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.AES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey();
        tryEncryption(en);
    }

    public void testDESMinKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.DES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey();
        tryEncryption(en);
    }

    public void testTripleDESMinKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.TripleDES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey();
        tryEncryption(en);
    }

    public void testBlowfishMaxKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.Blowfish, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey(LunarSupport.getSecureRandom(), en.getAlgorithm().getMaxKeySize());
        tryEncryption(en);
    }

    public void testAESMaxKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.AES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey(LunarSupport.getSecureRandom(), en.getAlgorithm().getMaxKeySize());
        tryEncryption(en);
    }

    public void testDESMaxKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.DES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey(LunarSupport.getSecureRandom(), en.getAlgorithm().getMaxKeySize());
        tryEncryption(en);
    }

    public void testTripleDESMaxKeyLength() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.TripleDES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey(LunarSupport.getSecureRandom(), en.getAlgorithm().getMaxKeySize());
        tryEncryption(en);
    }

    public void tryEncryption(SymmetricEncryption en) throws Exception {

        byte [] sampleData = new byte [256];
        for (int i = 0; i < 256; i++) {
            sampleData[i] = (byte) i;
        }

        ByteArrayInputStream encodeInputStream = new ByteArrayInputStream(sampleData);
        ByteArrayOutputStream encodedOutputStream = new ByteArrayOutputStream(1024);

        en.encrypt(encodeInputStream, encodedOutputStream);

        encodeInputStream = new ByteArrayInputStream(encodedOutputStream.toByteArray());
        encodedOutputStream.reset();

        en.decrypt(encodeInputStream, encodedOutputStream);

        byte [] decodedData = encodedOutputStream.toByteArray();

        assertEquals(sampleData.length, decodedData.length);

        for (int i = 0; i < 256; i++) {
            assertEquals(sampleData[i], decodedData[i]);
        }
    }

    /**
     * If this method fails, you need to install the "JCE Unlimited Strength Jurisdiction Policy Files"
     * @throws Exception
     */
    public void testUnlimitedStrengthJurisdictionPolicyFilesInstalled() throws Exception {
        SymmetricEncryption en = new SymmetricEncryption(EncryptionAlgorithms.Blowfish, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        en.generateKey(LunarSupport.getSecureRandom(), en.getAlgorithm().getMaxKeySize());
        tryEncryption(en);
    }

    /**
     * Test if the ByteMapper links work
     * @throws Exception
     */
    public void testByteMapping() throws Exception {

        SymmetricEncryption orig = new SymmetricEncryption(EncryptionAlgorithms.Blowfish, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
        orig.generateKey();

        ByteArrayOutputStream savedStream = new ByteArrayOutputStream(2048);

        byte [] sampleData = new byte [256];
        for (int i = 0; i < 256; i++) {
            sampleData[i] = (byte) i;
        }

        ByteArrayInputStream encodeInputStream = new ByteArrayInputStream(sampleData);
        ByteArrayOutputStream encodedOutputStream = new ByteArrayOutputStream(1024);

        orig.encrypt(encodeInputStream, encodedOutputStream);

        FormatIO fio = new FormatIO(new SimpleFormatLoader().addKnownClass(SymmetricEncryption.class), null);
        fio.init(getClass().getResourceAsStream("/com/mgatelabs/lunardebris/support/tags/tags.js"));

        fio.save(savedStream, orig, 1);

        ByteArrayInputStream readStream = new ByteArrayInputStream(savedStream.toByteArray());

        BMResult result = fio.load(null, readStream, 0, savedStream.size());

        assertEquals(100, result.getObjectIdentity());
        assertNotNull(result.getObjectInstance());

        SymmetricEncryption readFish = (SymmetricEncryption) result.getObjectInstance();

        assertEquals(orig.getEncodedKey().length, readFish.getEncodedKey().length);
        assertEquals(orig.getInitializationVector().length, readFish.getInitializationVector().length);

        for (int i = 0; i < orig.getEncodedKey().length; i++) {
            assertEquals(orig.getEncodedKey()[i], readFish.getEncodedKey()[i]);
        }

        for (int i = 0; i < orig.getInitializationVector().length; i++) {
            assertEquals(orig.getInitializationVector()[i], readFish.getInitializationVector()[i]);
        }

        encodeInputStream = new ByteArrayInputStream(encodedOutputStream.toByteArray());
        encodedOutputStream.reset();

        readFish.decrypt(encodeInputStream, encodedOutputStream);

        byte [] decodedData = encodedOutputStream.toByteArray();

        assertEquals(sampleData.length, decodedData.length);

        for (int i = 0; i < 256; i++) {
            assertEquals(sampleData[i], decodedData[i]);
        }
    }
}
