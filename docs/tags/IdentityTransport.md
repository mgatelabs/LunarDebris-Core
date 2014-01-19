# IdentityTransport

## Usage

This tag is designed to transport a sender's identity.

## Tag Information

*Identity Number*: 106
*Includes Noise*: Yes

## Fields

### identity (required, byte \[\])

These bytes make up the sender's 256 bit identity.

### key (optional, EncryptionTransport)

This tag would include this identities public key.

### address (optional, ConnectionTransport)

This tag would include details on how to send messages to this identity.
