# EncryptionTransport

## Usage

This tag is designed to transport encryption details.  This related class can be used to encrypt or decrypt files, streams and bytes.

## Tag Information

*Identity Number*: 100

## Fields

### type (required, enum)

- SYM (Symetric key)
- PUB (Public key)
- PRI (Private key)

### algorithm (required, enum)

- AES
- BLOWFISH
- DES
- DESede (Triple DES)
- RSA

### mode (required, enum)

- CBC
- CFB
- ECB
- PCBC

### padding (required, enum);

- PKCS1Padding
- PKCS5Padding
- OAEPWithSHA-1AndMGF1Padding
- OAEPWithSHA-256AndMGF1Padding

### compression (required, enum)

- NONE
- GZIP

### key (required, byte \[\])

They bytes that make up the key

### format (required, string)

What format were the key byte's transported in?

### keySize (required, int)

The size of the key

### byte[] iv (optional, byte \[\])

Only needed for symetric keys.  If not provided a default IV will be provided which will resemble 0x00, 0x01, 0x02, 0x03...

### Date start (optional, Date)

This is an optional field, but the sender can specify a date when this encryption scheme goes live.  This field could be ignored, but certain systems could find a use for it.

### Date expire (optional, Date)

This is an optional field, but the sender can specify a date when this encryption scheme should expire.  This field could be ignored, but certain systems could find a use for it.
