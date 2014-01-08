package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.bytemapper.util.FileLink;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public class MailTransport {

    /**
     * The message content, gzip + symmetric encrypted (BLOWFISH, AES, DES, DESede...)
     */
    FileLink message;

    /**
     * Optional hash of the message
     */
    byte [] hash;

    /**
     * The security details, which have been gzip + RSA Encrypted
     */
    FileLink security;

    public FileLink getMessage() {
        return message;
    }

    public void setMessage(FileLink message) {
        this.message = message;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public FileLink getSecurity() {
        return security;
    }

    public void setSecurity(FileLink security) {
        this.security = security;
    }
}
