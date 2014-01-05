package com.mgatelabs.lunardebris.support.interfaces;

import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithmModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithmPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MiniMegaton on 1/4/14.
 */
public interface MessageKeyInterface {
    public EncryptionAlgorithms getAlgorithm();
    public EncryptionAlgorithmModes getMode();
    public EncryptionAlgorithmPaddingSchemes getPadding();
    public void encrypt(InputStream in, OutputStream out) throws Exception;
    public void decrypt(InputStream in, OutputStream out) throws Exception;
}
