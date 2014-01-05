package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public enum EncryptionAlgorithms {
    AES("AES", 128, 256),
    Blowfish("Blowfish", 32, 448),
    DES("DES", 56, 56),
    TripleDES("DESede", 112, 168),
    RSA("RSA", 512, 16384);

    private String algorithm;
    private int minKeySize, maxKeySize;

    EncryptionAlgorithms(String algorithm, int minKeySize, int maxKeySize) {
        this.algorithm = algorithm;
        this.minKeySize = minKeySize;
        this.maxKeySize = maxKeySize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getMinKeySize() {
        return minKeySize;
    }

    public int getMaxKeySize() {
        return maxKeySize;
    }

    public boolean isKeySizeValid(int size) {
        if (size < minKeySize || size > maxKeySize) {
            return false;
        } else if (this == AES) {
            return size == 128 || size == 192 || size == 256;
        } else if (this == Blowfish) {
            return size % 8 == 0;
        } else if (this == TripleDES) {
            return size == 112 || size == 168;
        }
        return true;
    }

    public int getInitializationVector(int keySize) {
        switch (this) {
            case TripleDES:
            case DES:
            case Blowfish: {
                return 8;
            }
            case AES:
                return 16;
            default: {
                return keySize;
            }
        }
    }
}
