package com.mgatelabs.lunardebris.support.interfaces;

import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public interface MessageKeyInterface {
    public EncryptionAlgorithms getAlgorithm();
    public EncryptionModes getMode();
    public EncryptionPaddingSchemes getPadding();
    public void encrypt(InputStream in, OutputStream out) throws Exception;
    public void decrypt(InputStream in, OutputStream out) throws Exception;
}
