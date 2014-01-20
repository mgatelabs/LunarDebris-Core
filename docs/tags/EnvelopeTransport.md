# EnvelopeTransport

## Usage

This tag is designed to transport an encrypted letter.

## Tag Information

- *Identity Number*: 1
- *Includes Noise*: No

## Fields

### envelope (required, FileLink)

This field contains an encrypted LetterTransport tag.  This field can be decrypted with the key field.

### identity (optional, FileLink)

This field contains an encrypted IdentityTransport tag.  This field can be decrypted with the key field.

### key (required, FileLink)

This field contains an encrypted EncryptionTransport tag.  This field can be decrypted with the client's private key.

### generated (optional, Date)

This field would store the date and time the envelope was generated in UTC time.  This can be used by the server to discard old messages and increase the security surrounding the hash, hmac and signature.

### hash (optional, DigestTransport)

This is a simple digest used to verify a file's integrity, but it cannot stop tampering.

*Fields to digest*

- envelope (the encrypted FileLink bytes)
- identity (the encrypted FileLink bytes)
- key (the encrypted FileLink bytes)
- generated (the long bytes)

### hmac (optional, MacTransport)

This is a more advanced digest which requires a shared secret key.  This provides more strength than a standard digest field.

*Fields to digest*

- envelope (the encrypted FileLink bytes)
- identity (the encrypted FileLink bytes)
- key (the encrypted FileLink bytes)
- generated (the long bytes)

###  signature (optional, SignatureTransport)

This is similar to the hmac field, but the receiver will need to use the sender's public key to verify that it came from them and the file hasn't been altered.

*Fields to sign*

- envelope (the encrypted FileLink bytes)
- identity (the encrypted FileLink bytes)
- key (the encrypted FileLink bytes)
- generated (time as long bytes)
- hmac (the hmac's digest bytes)
