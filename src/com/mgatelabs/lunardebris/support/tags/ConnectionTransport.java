package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.ConnectionAcceptanceTypes;

/**
 * Created by MiniMegaton on 1/12/14.
 */
public class ConnectionTransport {

    private String host;
    private int port;
    private String path;
    private byte [] hmac;
    private ConnectionAcceptanceTypes acceptance;

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

    public ConnectionAcceptanceTypes getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(ConnectionAcceptanceTypes acceptance) {
        this.acceptance = acceptance;
    }
}
