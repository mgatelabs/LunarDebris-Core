package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.ConnectionIdentityTypes;
import com.mgatelabs.lunardebris.support.enums.ConnectionVerificationTypes;

/**
 * Created by MiniMegaton on 1/12/14.
 */
public class ConnectionTransport {

    private String host;
    private int port;
    private String path;
    private byte[] hmac;
    private ConnectionVerificationTypes verification;
    private ConnectionIdentityTypes identity;

    public ConnectionTransport() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getHmac() {
        return hmac;
    }

    public void setHmac(byte[] hmac) {
        this.hmac = hmac;
    }

    public ConnectionVerificationTypes getVerification() {
        return verification;
    }

    public void setVerification(ConnectionVerificationTypes verification) {
        this.verification = verification;
    }

    public ConnectionIdentityTypes getIdentity() {
        return identity;
    }

    public void setIdentity(ConnectionIdentityTypes identity) {
        this.identity = identity;
    }
}
