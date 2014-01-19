# MacTransport

## Usage

This tag is designed to transport HMAC digests.  A HMAC digest is like a standard digest, except the sender and receiver also know a shared secret.  If the shared secret isn't publicly know it would be difficult for a third party to alter the encrypted letter without being noticed.

## Tag Information

- *Identity Number*: 102
- *Includes Noise*: No

## Fields

### hmac (required, byte \[\])

This field contains the bytes used to make up the hmac digest.  This value is only useful if the receiver also knows the shared secret.

### algorithm (required, enum)

The following algorithms are supported.

- HmacMD5
- HmacSHA1
- HmacSHA256
- HmacSHA384
- HmacSHA512
