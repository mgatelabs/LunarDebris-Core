LunarDebris-Core
================

Lunar Debris is a library of classes, which will be used to securly transport messages over the internet.  The main goal of this project is to remove 3rd parties which are currently infused into the standard mail lifecycle.  This project will not attempt to interface with the existing mail systems. 

Goals
------------

- Messages are only readable by the intended recipetent
- Sender Identity & Verification are optional (For anonymous, non-verifiable messagaing)
- Low file format overhead

Flow
------------


Identity Control
------------

Messages will have various ways to disclose the sender's identity.  Identity is based upon a 256 but number (32 bytes), with no other information being disclosed.  A clients identity will be generated when setting up a mailbox, and no central authority will be used to stop collisions.  One client may have multiple identities for diffferent mailboxes.

**Identity options**
- Anonymous: Don't reveal your identity (Special cases)
- Envelope ID: A 256 bit number which be sent on the outside of their Envelope (Identity is in the clear)
- Message ID: A 256 bit number, which will be sent inside your encrypted Envelope.  (The envelope must be opened in order to learn their identity)

The mailbox owner can specify what identiy level is required for messages to be received.

*The 256 bit identity number is not controlled by a central source.*

Verification Control
------------

There will be multiple ways in order to ensure a message came from the right source and hasn't been altered.

- NONE:
- HASH: A simple hash of the content (provides no verification of where it came from)
- HMAC: The sender and receiver will need to know a shared secret (Provides weak verification)
- SIGNATURE: Public/Private keys are used with a hash (Provides strong verification)

Envelope Encryption
------------

The encryption used on the envelope will be either AES, DES, TripleDES or Blowfish.  The symmetric encryption key will be generated for each message and should not be recycles.  The symmetric key will be encrypted with the recipetents public key.  The decrypted key will provide additional information, such as the encryption scheme used, and when the encryption key expires.

How would clients send mail?
------------

There are varius ways clients could send messages.  Each option handles different security concerns.

**Open mailbox:**
You would post details, such as a QR code with your connection details, display name and public key.  This would be prefered for mail messages that are not sensative, but you still want a layer of protection.  Clients who send messages can also disclose their return details for two way communication to start.

- Spam messages, as with current e-mail
- General communication

**Private mailbox:**
This would entail you setting up a mail box with a different public/private key from the **open** connection.  The connection details would not be posted to the public.  At this point you would need to physically transfer connection details to clients you wish to send and receive messages from.

- Business partners
- Family members

Specifications
================

Envelope Format
------------

- Contents: The encrypted message contents (Required)
- Key: The symmetric key used to decrypt the message.  This has been encrypted with the recipients public key. (Required)
- Sender Identity: The sender's 256 bit number (Optional)
- Generated: The date/time the message was generated (Optional, in UTC)
- HASH: Simple hash to verify content has not been altered (Optional)
- HMAC: Advanced hash to verify content has not been altered, requires shared secret (Optional)
- Signature: Secure hash used to verify sender's identity (Optional)

Message Format
------------

- Message identity: 256 bit number (Required)
- Related identity: 256 bit number (Optional)
- Generated: The date/time the message was generated (Optional, in UTC)
- Subject: The tagline for the message (Optional, UTF-8)
- Sender Identity: The sender's 256 bit number (Optional)
- Files: A list of files (Required)

File Format
------------

- content: The file's bytes (Required)
- name: The file's name (Required)
- mime: The file's mime type (Required)

Appendix
================

Unlike current mail systems where thread views have been superimposed, each letter will have it's own unique identity number.  When you reply to a letter the source letter's identity will be referenced, so proper message threading can occur.

This is still in early development, so watch out!
