package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/8/14.
 */
public enum MacAlgorithms {
    HMD5("HmacMD5"),
    HSHA1("HmacSHA1"),
    HSHA256("HmacSHA256"),
    HSHA384("HmacSHA384"),
    HSHA512("HmacSHA512");

    private String algorithm;

    MacAlgorithms(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
