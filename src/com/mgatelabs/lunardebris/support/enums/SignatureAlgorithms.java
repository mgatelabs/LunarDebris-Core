package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/9/14.
 */
public enum SignatureAlgorithms {
    MD2wRSA("MD2withRSA", EncryptionAlgorithms.RSA),
    MD5wRSA("MD5withRSA", EncryptionAlgorithms.RSA),
    SHA1wRSA("SHA1withRSA", EncryptionAlgorithms.RSA),
    SHA256wRSA("SHA256withRSA", EncryptionAlgorithms.RSA),
    SHA384wRSA("SHA384withRSA", EncryptionAlgorithms.RSA),
    SHA512wRSA("SHA512withRSA", EncryptionAlgorithms.RSA);

    private String algorithm;
    private EncryptionAlgorithms encryption;

    SignatureAlgorithms(String algorithm, EncryptionAlgorithms encryption) {
        this.algorithm = algorithm;
        this.encryption = encryption;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public EncryptionAlgorithms getEncryption() {
        return encryption;
    }
}
