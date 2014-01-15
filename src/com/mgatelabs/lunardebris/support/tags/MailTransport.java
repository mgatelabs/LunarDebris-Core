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
     * The sender's open identity
     */
    IdentityTransport openIdentity;

    /**
     * The sender's encrypted identity, gzip + encrypted with the key
     */
    FileLink encryptedIdentity;

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

    public IdentityTransport getOpenIdentity() {
        return openIdentity;
    }

    public void setOpenIdentity(IdentityTransport openIdentity) {
        this.openIdentity = openIdentity;
    }

    public FileLink getEncryptedIdentity() {
        return encryptedIdentity;
    }

    public void setEncryptedIdentity(FileLink encryptedIdentity) {
        this.encryptedIdentity = encryptedIdentity;
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
