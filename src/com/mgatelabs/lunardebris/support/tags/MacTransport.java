package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.MacAlgorithms;
import com.mgatelabs.lunardebris.support.enums.MessageDigestAlgorithms;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public class MacTransport {

    private byte [] hmac;
    private MacAlgorithms algorithm;

    public MacTransport() {
    }

    public byte[] getHmac() {
        return hmac;
    }

    public void setHmac(byte[] hmac) {
        this.hmac = hmac;
    }

    public MacAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(MacAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    private Mac getInstance(byte [] key) throws Exception {
        Mac instance = Mac.getInstance(algorithm.getAlgorithm());
        SecretKeySpec secret_key = new SecretKeySpec(key, algorithm.getAlgorithm());
        instance.init(secret_key);
        return instance;
    }

    public byte [] digest(byte [] input, byte [] key) throws Exception {
        Mac md = getInstance(key);
        md.update(input);
        return md.doFinal();
    }

    public byte [] digest(File f, byte [] key) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            return digest(fis, key);
        } finally {
            fis.close();
        }
    }

    public byte [] digest(InputStream is, byte [] key) throws Exception {
        Mac md = getInstance(key);

        byte[] dataBytes = new byte[1024];

        int count = 0;
        while ((count = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, count);
        };

        return md.doFinal();
    }

    public boolean verify(byte [] input, byte [] key) throws Exception {
        final byte [] other = digest(input, key);
        return Arrays.equals(this.hmac, other);
    }

    public boolean verify(File f, byte [] key) throws Exception {
        final byte [] other = digest(f, key);
        return Arrays.equals(this.hmac, other);
    }

    public boolean verify(InputStream is, byte [] key) throws Exception {
        final byte [] other = digest(is, key);
        return Arrays.equals(this.hmac, other);
    }

}
