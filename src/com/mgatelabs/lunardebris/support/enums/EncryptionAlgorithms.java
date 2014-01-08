package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public enum EncryptionAlgorithms {
    AES("AES", 128, 256, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, false),
    BLOWFISH("Blowfish", 32, 448, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, false),
    DES("DES", 56, 56, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, false),
    DESede("DESede", 112, 168, EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5, false),
    RSA("RSA", 512, 16384, EncryptionModes.ECB, EncryptionPaddingSchemes.PKCS1, true);

    private String algorithm;
    private int minKeySize;
    private int maxKeySize;
    EncryptionModes mode;
    EncryptionPaddingSchemes padding;
    private boolean doFinal;

    EncryptionAlgorithms(String algorithm, int minKeySize, int maxKeySize, EncryptionModes mode, EncryptionPaddingSchemes padding, boolean doFinal) {
        this.algorithm = algorithm;
        this.minKeySize = minKeySize;
        this.maxKeySize = maxKeySize;
        this.mode = mode;
        this.padding = padding;
        this.doFinal = doFinal;
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

    public EncryptionModes getMode() {
        return mode;
    }

    public EncryptionPaddingSchemes getPadding() {
        return padding;
    }

    public boolean isDoFinal() {
        return doFinal;
    }

    public boolean isKeySizeValid(int size) {
        if (size < minKeySize || size > maxKeySize) {
            return false;
        } else if (this == AES) {
            return size == 128 || size == 192 || size == 256;
        } else if (this == BLOWFISH) {
            return size % 8 == 0;
        } else if (this == DESede) {
            return size == 112 || size == 168;
        }
        return true;
    }

    public int getIV(int keySize) {
        switch (this) {
            case DESede:
            case DES:
            case BLOWFISH: {
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
