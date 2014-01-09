package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public enum MessageDigestAlgorithms {
    MD2("MD2"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    private String algorithm;

    MessageDigestAlgorithms(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
