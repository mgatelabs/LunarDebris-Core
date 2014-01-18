package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.SignatureAlgorithms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

/**
 * Created by MiniMegaton on 1/9/14.
 */
public class SignatureTransport {

    private byte [] signature;
    private SignatureAlgorithms algorithm;

    public SignatureTransport() {
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public SignatureAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SignatureAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public void sign(SecureRandom random, PrivateKey key, byte [] ... bytes) throws Exception {
        InputStream [] streams = new InputStream[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            streams[i] = new ByteArrayInputStream(bytes[i]);
        }

        sign(random, key, streams);
    }

    /**
     * This is used to create a signature for a user with their private key
     * @param random
     * @param key
     * @param streams
     * @throws Exception
     */
    public void sign(SecureRandom random, PrivateKey key, InputStream ... streams) throws Exception{
        Signature signature = Signature.getInstance(algorithm.getAlgorithm());
        signature.initSign(key, random);
        for (InputStream is: streams) {
            byte[] dataBytes = new byte[1024];
            int count;
            while ((count = is.read(dataBytes)) != -1) {
                signature.update(dataBytes, 0, count);
            };
        }
        this.signature = signature.sign();
    }

    public boolean verify(PublicKey key, byte [] ... bytes) throws Exception {
        InputStream [] streams = new InputStream[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            streams[i] = new ByteArrayInputStream(bytes[i]);
        }

        return verify(key, streams);
    }

    public boolean verify(PublicKey key, InputStream ... streams) throws Exception{
        Signature signature = Signature.getInstance(algorithm.getAlgorithm());
        signature.initVerify(key);
        for (InputStream is: streams) {
            byte[] dataBytes = new byte[1024];
            int count;
            while ((count = is.read(dataBytes)) != -1) {
                signature.update(dataBytes, 0, count);
            };
        }
        return signature.verify(this.signature);
    }
}
