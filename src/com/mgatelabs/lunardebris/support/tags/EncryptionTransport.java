package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.enums.EncryptionKeyTypes;
import com.mgatelabs.lunardebris.util.LunarSupport;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class EncryptionTransport {
    EncryptionKeyTypes type;
    EncryptionAlgorithms algorithm;
    EncryptionModes mode;
    EncryptionPaddingSchemes padding;
    private byte [] key;
    private String format;
    private int keySize;
    private byte [] iv;

    public EncryptionTransport() {
        this(null, null, 0, null, null, null, null, null);
    }

    public EncryptionTransport(EncryptionKeyTypes type, byte[] key, int keySize, String format, byte[] iv, EncryptionAlgorithms algorithm, EncryptionModes mode, EncryptionPaddingSchemes padding) {
        this.type = type;
        this.key = key;
        this.keySize = keySize;
        this.iv = iv;
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }

    // Instance grabbers

    public static EncryptionTransport from(PrivateKey k, int keySize) {
        return new EncryptionTransport(EncryptionKeyTypes.PRIVATE_KEY, k.getEncoded(), keySize, k.getFormat(), null, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
    }

    public static EncryptionTransport from(PublicKey k, int keySize) {
        return new EncryptionTransport(EncryptionKeyTypes.PUBLIC_KEY, k.getEncoded(), keySize, k.getFormat(), null, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
    }

    public static EncryptionTransport from(Key k, int keySize, byte [] iv) {
        return new EncryptionTransport(EncryptionKeyTypes.SYMMETRIC, k.getEncoded(), keySize, k.getFormat(), iv, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5Padding);
    }

    // Getter / Setters

    public EncryptionKeyTypes getType() {
        return type;
    }

    public void setType(EncryptionKeyTypes type) {
        this.type = type;
    }

    public EncryptionAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(EncryptionAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public EncryptionModes getMode() {
        return mode;
    }

    public void setMode(EncryptionModes mode) {
        this.mode = mode;
    }

    public EncryptionPaddingSchemes getPadding() {
        return padding;
    }

    public void setPadding(EncryptionPaddingSchemes padding) {
        this.padding = padding;
    }

    protected Cipher getCipher() throws Exception {
        StringBuilder instanceName = new StringBuilder();
        instanceName.append(this.algorithm.getAlgorithm());
        if (this.mode != null && this.padding != null) {
            instanceName.append("/").append(this.mode.toString()).append("/").append(this.padding.toString());
        }
        return Cipher.getInstance(instanceName.toString());
    }

    private Key getKeySpec() throws Exception {
        switch (type) {
            case PRIVATE_KEY: {
                return KeyFactory.getInstance(this.algorithm.getAlgorithm()).generatePrivate(new PKCS8EncodedKeySpec(key));
            }
            case PUBLIC_KEY: {
                return KeyFactory.getInstance(this.algorithm.getAlgorithm()).generatePublic(new X509EncodedKeySpec(key));
            }
            case SYMMETRIC:
            default: {
                return new SecretKeySpec(key, this.algorithm.getAlgorithm());
            }
        }
    }

    public IvParameterSpec getIvParmSpec() throws Exception {
        return new IvParameterSpec(iv);
    }

    private Cipher getEncryptCipher() throws Exception {

        Cipher cipher = getCipher();

        switch (algorithm) {
            case RSA: {
                cipher.init(Cipher.ENCRYPT_MODE, getKeySpec());
            } break;
            default: {
                cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(), getIvParmSpec());
            }
        }

        return cipher;
    }

    private Cipher getDecryptCipher() throws Exception {

        Cipher cipher = getCipher();

        switch (algorithm) {
            case RSA: {
                cipher.init(Cipher.DECRYPT_MODE, getKeySpec());
            } break;
            default: {
                cipher.init(Cipher.DECRYPT_MODE, getKeySpec(), getIvParmSpec());
            }
        }

        return cipher;
    }

    public void encrypt(InputStream is, OutputStream os) throws Exception {
        Cipher cipher = getEncryptCipher();
        CipherInputStream cis = new CipherInputStream(is, cipher);
        LunarSupport.copyStream(cis, os);
    }

    public void decrypt(InputStream is, OutputStream os) throws Exception {
        Cipher cipher = getDecryptCipher();
        CipherInputStream cis = new CipherInputStream(is, cipher);
        LunarSupport.copyStream(cis, os);
    }
}
