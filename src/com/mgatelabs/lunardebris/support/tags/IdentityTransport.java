package com.mgatelabs.lunardebris.support.tags;

/**
 * Created by Michael Fuller on 1/14/14.
 */
public class IdentityTransport {

    // The client's identity number
    private byte[] identity;

    // The client's public key (I would never send this, unless to an public mailbox)
    private EncryptionTransport key;

    // How to reach the client
    private ConnectionTransport address;

    public IdentityTransport() {
    }

    public byte[] getIdentity() {
        return identity;
    }

    public void setIdentity(byte[] identity) {
        this.identity = identity;
    }

    public EncryptionTransport getKey() {
        return key;
    }

    public void setKey(EncryptionTransport key) {
        this.key = key;
    }

    public ConnectionTransport getAddress() {
        return address;
    }

    public void setAddress(ConnectionTransport address) {
        this.address = address;
    }
}
