package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.MessageDigestAlgorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public class DigestTransport {

    private byte [] digest;
    private MessageDigestAlgorithms algorithm;

    public DigestTransport() {
    }

    public byte[] getDigest() {
        return digest;
    }

    public void setDigest(byte[] digest) {
        this.digest = digest;
    }

    public MessageDigestAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(MessageDigestAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    private MessageDigest getDigestInstance() throws Exception {
        return MessageDigest.getInstance(algorithm.getAlgorithm());
    }

    public byte [] digest(byte [] input) throws Exception {
        MessageDigest md = getDigestInstance();
        md.update(input);
        return md.digest();
    }

    public byte [] digest(File f) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            return digest(fis);
        } finally {
            fis.close();
        }
    }

    public byte [] digest(InputStream is) throws Exception {
        MessageDigest md = getDigestInstance();

        byte[] dataBytes = new byte[1024];

        int count = 0;
        while ((count = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, count);
        };

        return md.digest();
    }

    public boolean verify(byte [] input) throws Exception {
        final byte [] other = digest(input);
        return Arrays.equals(this.digest, other);
    }

    public boolean verify(File f) throws Exception {
        final byte [] other = digest(f);
        return Arrays.equals(this.digest, other);
    }

    public boolean verify(InputStream is) throws Exception {
        final byte [] other = digest(is);
        return Arrays.equals(this.digest, other);
    }
}
