package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public enum EncryptionPaddingSchemes {
    NS("NOT SET"),
    PKCS1("PKCS1Padding"),
    PKCS5("PKCS5Padding"),
    OSH1M("OAEPWithSHA-1AndMGF1Padding"),
    OSH256M("OAEPWithSHA-256AndMGF1Padding");

    private String padding;

    EncryptionPaddingSchemes(String padding) {
        this.padding = padding;
    }

    public String getPadding() {
        return padding;
    }
}
