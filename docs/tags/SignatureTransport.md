# SignatureTransport

## Usage

This tag is designed to transport an RSA based signature.  Signatures are similar to HMACs, since they both verify that content hasn't been altered, but provides more protection from tampering, since it also verifies the identity of the sender.

## Fields

### signature (required, byte \[\])

This field contains the bytes used to make up the signature.  This value is only useful if the receiver also has the sender's public key.

### algorithm (required, enum)

The following algorithms are supported.

- MD2withRSA
- MD5withRSA
- SHA1withRSA
- SHA256withRSA
- SHA384withRSA
- SHA512withRSA
