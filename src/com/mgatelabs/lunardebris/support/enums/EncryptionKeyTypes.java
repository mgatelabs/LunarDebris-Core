package com.mgatelabs.lunardebris.support.enums;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public enum EncryptionKeyTypes {
    SYMMETRIC(true),
    PUBLIC_KEY(false),
    PRIVATE_KEY(false);

    private boolean ivRequired;

    EncryptionKeyTypes(boolean ivRequired) {
        this.ivRequired = ivRequired;
    }

    public boolean isIvRequired() {
        return ivRequired;
    }
}
