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
    private FileLink envelope;

    /**
     * The sender's open identity
     */
    private FileLink identity;

    /**
     * The security details, which have been gzip + RSA Encrypted
     */
    private FileLink key;

    /**
     * The required date that it was generated on in UTC
     */
    private Date generated;

    /**
     * Optional hash of the envelope
     */
    private DigestTransport hash;

    /**
     * Optional HMAC of the envelope
     */
    MacTransport hmac;

    /**
     * Optional Signature of the envelope
     */
    SignatureTransport signature;

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

    public FileLink getKey() {
        return key;
    }

    public void setKey(FileLink key) {
        this.key = key;
    }

    public FileLink getIdentity() {
        return identity;
    }

    public void setIdentity(FileLink identity) {
        this.identity = identity;
    }

    public Date getGenerated() {
        return generated;
    }

    public void setGenerated(Date generated) {
        this.generated = generated;
    }

    public MacTransport getHmac() {
        return hmac;
    }

    public void setHmac(MacTransport hmac) {
        this.hmac = hmac;
    }

    public SignatureTransport getSignature() {
        return signature;
    }

    public void setSignature(SignatureTransport signature) {
        this.signature = signature;
    }
}
