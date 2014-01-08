package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.lunardebris.support.enums.EncryptionModes;
import com.mgatelabs.lunardebris.support.enums.EncryptionPaddingSchemes;
import com.mgatelabs.lunardebris.support.enums.EncryptionAlgorithms;
import com.mgatelabs.lunardebris.support.enums.EncryptionKeyTypes;
import com.mgatelabs.lunardebris.util.LunarSupport;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * Created by MiniMegaton on 1/5/14.
 */
public class EncryptionTransport {
    EncryptionKeyTypes type;
    EncryptionAlgorithms algorithm;
    EncryptionModes mode;
    EncryptionPaddingSchemes padding;
    private byte [] key;
    private String format;
    private int keySize;
    private byte [] iv;
    private Date start;
    private Date expire;

    public EncryptionTransport() {
        this(null, null, 0, null, null, null, null, null);
    }

    public EncryptionTransport(EncryptionKeyTypes type, byte[] key, int keySize, String format, byte[] iv, EncryptionAlgorithms algorithm, EncryptionModes mode, EncryptionPaddingSchemes padding) {
        this.type = type;
        this.key = key;
        this.keySize = keySize;
        this.format = format;
        this.iv = iv;
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }

    // Instance grabbers

    public static EncryptionTransport from(PrivateKey k, int keySize) {
        return new EncryptionTransport(EncryptionKeyTypes.PRI, k.getEncoded(), keySize, k.getFormat(), null, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5);
    }

    public static EncryptionTransport from(PublicKey k, int keySize) {
        return new EncryptionTransport(EncryptionKeyTypes.PUB, k.getEncoded(), keySize, k.getFormat(), null, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5);
    }

    public static EncryptionTransport from(Key k, int keySize, byte [] iv) {
        return new EncryptionTransport(EncryptionKeyTypes.SYM, k.getEncoded(), keySize, k.getFormat(), iv, EncryptionAlgorithms.valueOf(k.getAlgorithm()), EncryptionModes.CBC, EncryptionPaddingSchemes.PKCS5);
    }

    // Getter / Setters

    public EncryptionKeyTypes getType() {
        return type;
    }

    public void setType(EncryptionKeyTypes type) {
        this.type = type;
    }

    public EncryptionAlgorithms getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(EncryptionAlgorithms algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public EncryptionModes getMode() {
        return mode;
    }

    public void setMode(EncryptionModes mode) {
        this.mode = mode;
    }

    public EncryptionPaddingSchemes getPadding() {
        return padding;
    }

    public void setPadding(EncryptionPaddingSchemes padding) {
        this.padding = padding;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    protected Cipher getCipher() throws Exception {
        StringBuilder instanceName = new StringBuilder();
        instanceName.append(this.algorithm.getAlgorithm());

        EncryptionModes md = algorithm.getMode();
        EncryptionPaddingSchemes pd = algorithm.getPadding();

        if (this.mode != EncryptionModes.NS) {
            md = this.mode;
        }

        if (this.padding != EncryptionPaddingSchemes.NS) {
            pd = this.padding;
        }

        if (md != null && pd != null) {
            instanceName.append("/").append(md.toString()).append("/").append(pd.getPadding());
        }
        return Cipher.getInstance(instanceName.toString());
    }

    private Key getKeySpec() throws Exception {
        switch (type) {
            case PRI: {
                return KeyFactory.getInstance(this.algorithm.getAlgorithm()).generatePrivate(new PKCS8EncodedKeySpec(key));
            }
            case PUB: {
                return KeyFactory.getInstance(this.algorithm.getAlgorithm()).generatePublic(new X509EncodedKeySpec(key));
            }
            case SYM:
            default: {
                return new SecretKeySpec(key, this.algorithm.getAlgorithm());
            }
        }
    }

    public IvParameterSpec getIvParmSpec() throws Exception {
        if (iv == null) {
            int size =algorithm.getIV(keySize);
            byte [] bytes = new byte [size];
            for (int i = 0; i < size; i++) {
                bytes[i] = (byte)i;
            }
            return new IvParameterSpec(bytes);
        } else {
            return new IvParameterSpec(iv);
        }
    }

    private Cipher getEncryptCipher() throws Exception {
        Cipher cipher = getCipher();
        switch (algorithm) {
            case RSA: {
                cipher.init(Cipher.ENCRYPT_MODE, getKeySpec());
            } break;
            default: {
                cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(), getIvParmSpec());
            }
        }
        return cipher;
    }

    private Cipher getDecryptCipher() throws Exception {
        Cipher cipher = getCipher();
        switch (algorithm) {
            case RSA: {
                cipher.init(Cipher.DECRYPT_MODE, getKeySpec());
            } break;
            default: {
                cipher.init(Cipher.DECRYPT_MODE, getKeySpec(), getIvParmSpec());
            }
        }
        return cipher;
    }

    public void encrypt(InputStream is, OutputStream os) throws Exception {
        crypt(getEncryptCipher(), is, os);
    }

    public void decrypt(InputStream is, OutputStream os) throws Exception {
        crypt(getDecryptCipher(), is, os);
    }

    private void crypt(Cipher cipher, InputStream is, OutputStream os) throws Exception {
        // RSA does not require this step, but it will show an error
        if (algorithm.isDoFinal()) {
            ByteArrayOutputStream toBytes = new ByteArrayOutputStream(1024);
            LunarSupport.copyStream(is, toBytes);
            byte [] result = cipher.doFinal(toBytes.toByteArray());
            os.write(result);
        } else {
            CipherInputStream cis = new CipherInputStream(is, cipher);
            LunarSupport.copyStream(cis, os);
        }
    }
}
