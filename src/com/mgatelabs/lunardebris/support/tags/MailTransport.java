package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.bytemapper.util.FileLink;

import java.util.Date;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public class MailTransport {

    /**
     * The envelope content, gzip + symmetric encrypted (BLOWFISH, AES, DES, DESede...)
     */
    FileLink envelope;

    /**
     * The security details, which have been gzip + RSA Encrypted
     */
    FileLink key;

    /**
     * The required date that it was generated on in UTC
     */
    Date generated;

    /**
     * Optional hash of the envelope
     */
    DigestTransport hash;

    /**
     * Optional HMAC of the envelope
     */
    MacTransport hmac;

    public MailTransport() {

    }

    public FileLink getEnvelope() {
        return envelope;
    }

    public void setEnvelope(FileLink envelope) {
        this.envelope = envelope;
    }

    public DigestTransport getHash() {
        return hash;
    }

    public void setHash(DigestTransport hash) {
        this.hash = hash;
    }

    public FileLink getSecurity() {
        return key;
    }

    public void setSecurity(FileLink security) {
        this.key = key;
    }
}
