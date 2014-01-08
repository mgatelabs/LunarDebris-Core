package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.bytemapper.support.io.FormatIO;
import com.mgatelabs.bytemapper.util.BMResult;
import com.mgatelabs.bytemapper.util.loaders.SimpleFormatLoader;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.enums.EncryptionKeyTypes;
import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.util.EncryptionUtils;
import com.mgatelabs.lunardebris.util.LunarSupport;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by MiniMegaton on 1/6/14.
 */
public class EncryptionTransportTest extends TestCase {

    public void testBlowfishEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.BLOWFISH, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, EncryptionAlgorithms.BLOWFISH.getMinKeySize());
        tryEncryption(et);
    }

    public void testAESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.AES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, EncryptionAlgorithms.AES.getMinKeySize());
        tryEncryption(et);
    }

    public void testDESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.DES, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, EncryptionAlgorithms.DES.getMinKeySize());
        tryEncryption(et);
    }

    public void testTripleDESEncryption() throws Exception {
        EncryptionTransport et = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.DESede, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, EncryptionAlgorithms.DESede.getMinKeySize());
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
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1, 512);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRI), keys.get(EncryptionKeyTypes.PUB));
    }

    public void testRSA1024() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1, 1024);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRI), keys.get(EncryptionKeyTypes.PUB));
    }

    public void testRSA2048() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1, 2048);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRI), keys.get(EncryptionKeyTypes.PUB));
    }

    /*
    public void testRSA4096() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1, 4096);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRI), keys.get(EncryptionKeyTypes.PUB));
    }
    */

    public void testRSA512NS() throws Exception {
        Map<EncryptionKeyTypes, EncryptionTransport> keys = EncryptionUtils.generateRSAKeyPair(LunarSupport.getSecureRandom(), EncryptionModes.NS, EncryptionPaddingSchemes.NS, 512);
        tryPKEncryption(keys.get(EncryptionKeyTypes.PRI), keys.get(EncryptionKeyTypes.PUB));
    }

    /**
     * Test if the ByteMapper links work
     * @throws Exception
     */
    public void testByteMapping() throws Exception {

        EncryptionTransport orig = EncryptionUtils.generateSymmetricEncryption(LunarSupport.getSecureRandom(), EncryptionAlgorithms.BLOWFISH, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, EncryptionAlgorithms.BLOWFISH.getMinKeySize());
        ByteArrayOutputStream savedStream = new ByteArrayOutputStream(2048);

        byte [] sampleData = new byte [256];
        for (int i = 0; i < 256; i++) {
            sampleData[i] = (byte) i;
        }

        ByteArrayInputStream encodeInputStream = new ByteArrayInputStream(sampleData);
        ByteArrayOutputStream encodedOutputStream = new ByteArrayOutputStream(1024);

        orig.encrypt(encodeInputStream, encodedOutputStream);

        FormatIO fio = new FormatIO(new SimpleFormatLoader().addKnownClass(EncryptionTransport.class), null);
        fio.init(getClass().getResourceAsStream("/com/mgatelabs/lunardebris/support/tags/tags.js"));

        fio.save(savedStream, orig, 1);
        /*
        FileOutputStream fos = new FileOutputStream(new File("sample.out"));
        fos.write(savedStream.toByteArray());
        fos.close();
        */
        ByteArrayInputStream readStream = new ByteArrayInputStream(savedStream.toByteArray());

        BMResult result = fio.load(null, readStream, 0, savedStream.size());

        assertEquals(100, result.getObjectIdentity());
        assertNotNull(result.getObjectInstance());

        EncryptionTransport readFish = (EncryptionTransport) result.getObjectInstance();

        assertEquals(orig.getKey().length, readFish.getKey().length);
        assertEquals(orig.getIv().length, readFish.getIv().length);

        assertEquals(orig.getAlgorithm(), readFish.getAlgorithm());
        assertEquals(orig.getMode(), readFish.getMode());
        assertEquals(orig.getPadding(), readFish.getPadding());
        assertEquals(orig.getType(), readFish.getType());
        assertEquals(orig.getFormat(), readFish.getFormat());

        for (int i = 0; i < orig.getKey().length; i++) {
            assertEquals(orig.getKey()[i], readFish.getKey()[i]);
        }

        for (int i = 0; i < orig.getIv().length; i++) {
            assertEquals(orig.getIv()[i], readFish.getIv()[i]);
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
