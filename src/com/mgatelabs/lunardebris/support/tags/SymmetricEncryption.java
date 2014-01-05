package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithmModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithmPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.interfaces.MessageKeyInterface;
import com.mgatelabs.lunardebris.util.LunarSupport;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public class SymmetricEncryption implements MessageKeyInterface {

    private EncryptionAlgorithms algorithm;
    private EncryptionAlgorithmModes mode;
    private EncryptionAlgorithmPaddingSchemes padding;
    private byte[] encodedKey;
    private byte[] initializationVector;
    private int keySize;

    public SymmetricEncryption() {
        this.algorithm = null;
        this.mode = null;
        this.padding = null;
        this.keySize = 0;
    }

    public SymmetricEncryption(EncryptionAlgorithms algorithm, EncryptionAlgorithmModes mode, EncryptionAlgorithmPaddingSchemes padding) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
        this.keySize = algorithm.getMinKeySize();
    }

    protected Cipher getCipher() throws Exception {
        StringBuilder instanceName = new StringBuilder();
        instanceName.append(this.algorithm.getAlgorithm());
        if (this.mode != null && this.padding != null) {
            instanceName.append("/").append(this.mode.toString()).append("/").append(this.padding.toString());
        }
        return Cipher.getInstance(instanceName.toString());
    }

    public void initEncryptCipher(Cipher cipher) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getEncodedKey(), this.algorithm.getAlgorithm()), getIvParmSpec());
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) throws Exception {
        Cipher cipher = getCipher();
        initEncryptCipher(cipher);
        CipherInputStream cis = new CipherInputStream(in, cipher);
        LunarSupport.copyStream(cis, out);
    }

    public void initDecryptCipher(Cipher cipher) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getEncodedKey(), this.algorithm.getAlgorithm()), getIvParmSpec());
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) throws Exception {
        Cipher cipher = getCipher();
        initDecryptCipher(cipher);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        LunarSupport.copyStream(in, cos);
    }

    public IvParameterSpec getIvParmSpec() throws Exception {
        if (initializationVector == null) {
            generateInitializationVector(LunarSupport.getSecureRandom(), keySize);
            return new IvParameterSpec(initializationVector);
        } else {
            return new IvParameterSpec(initializationVector);
        }
    }

    public void generateKey() throws Exception {
        generateKey(LunarSupport.getSecureRandom(), this.algorithm.getMinKeySize());
    }

    public void generateKey(SecureRandom random, int keySize) throws Exception {

        if (!algorithm.isKeySizeValid(keySize)) {
            throw new Exception("Invalid key length");
        }

        this.keySize = keySize;
        KeyGenerator keyGenerator = KeyGenerator.getInstance(getAlgorithm().getAlgorithm());
        // Setup the random class and associate with key generator.
        keyGenerator.init(keySize, random);
        SecretKey secKey = keyGenerator.generateKey();
        this.setEncodedKey(secKey.getEncoded());
    }

    public void generateInitializationVector(SecureRandom random, int size) {
        this.setInitializationVector(new byte[this.algorithm.getInitializationVector(size)]);
        random.nextBytes(this.getInitializationVector());
    }

    /**
     * Getter / Setters
     */

    @Override
    public EncryptionAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(EncryptionAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public EncryptionAlgorithmModes getMode() {
        return mode;
    }

    public void setMode(EncryptionAlgorithmModes mode) {
        this.mode = mode;
    }

    public EncryptionAlgorithmPaddingSchemes getPadding() {
        return padding;
    }

    public void setPadding(EncryptionAlgorithmPaddingSchemes padding) {
        this.padding = padding;
    }

    public byte[] getEncodedKey() {
        return encodedKey;
    }

    public void setEncodedKey(byte[] encodedKey) {
        this.encodedKey = encodedKey;
    }

    public byte[] getInitializationVector() {
        return initializationVector;
    }

    public void setInitializationVector(byte[] initializationVector) {
        this.initializationVector = initializationVector;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
}
