package com.mgatelabs.lunardebris.util;

import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.enums.EncryptionKeyTypes;
import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.support.tags.EncryptionTransport;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class EncryptionUtils {

    public static Map<EncryptionKeyTypes, EncryptionTransport> generateRSAKeyPair(SecureRandom random, EncryptionModes mode, EncryptionPaddingSchemes padding, int keySize) throws Exception{
        Map<EncryptionKeyTypes, EncryptionTransport> results = new HashMap<>(2);

        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(EncryptionAlgorithms.RSA.getAlgorithm());
        keyGen.initialize(keySize, random);

        final KeyPair keyPair = keyGen.generateKeyPair();

        results.put(EncryptionKeyTypes.PRIVATE_KEY, new EncryptionTransport(EncryptionKeyTypes.PRIVATE_KEY, keyPair.getPrivate().getEncoded(), keySize, keyPair.getPrivate().getFormat(), null, EncryptionAlgorithms.RSA, mode, padding));
        results.put(EncryptionKeyTypes.PUBLIC_KEY, new EncryptionTransport(EncryptionKeyTypes.PUBLIC_KEY, keyPair.getPublic().getEncoded(), keySize, keyPair.getPublic().getFormat(), null, EncryptionAlgorithms.RSA, mode, padding));

        return results;
    }

    public static EncryptionTransport generateSymmetricEncryption(SecureRandom random, EncryptionAlgorithms algorithm, EncryptionModes mode, EncryptionPaddingSchemes padding, int keySize) throws Exception {
        if (EncryptionAlgorithms.RSA == algorithm) {
            throw new Exception("Cannot generate encryption for transport for RSA keys");
        }

        if (!algorithm.isKeySizeValid(keySize)) {
            throw new Exception("Invalid key length");
        }

        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getAlgorithm());
        // Setup the random class and associate with key generator.
        keyGenerator.init(keySize, random);
        SecretKey secKey = keyGenerator.generateKey();
        secKey.getEncoded();

        byte [] iv = (new byte[algorithm.getInitializationVector(keySize)]);
        random.nextBytes(iv);

        return new EncryptionTransport(EncryptionKeyTypes.SYMMETRIC, secKey.getEncoded(), keySize, secKey.getFormat(), iv, algorithm, mode, padding);
    }

}
