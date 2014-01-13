LunarDebris-Core
================

Lunar Debris is a library of classes, which will be used to securly transport messages over the internet.  The main goal of this project is to remove 3rd parties which are currently infused into the standard mail lifecycle.  This project will not attempt to interface with the existing mail systems. 

Goals
------------

- Messages are only readable by the intended recipetent
- Sender Identity & Verification are optional (For anonymous, non-verifiable messaging)
- Low file format overhead

Flow
------------

Identity Control
------------

Messages will have various ways to disclose the sender's identity.  Identity is based upon a 256 but number (32 bytes), with the possability of no other information being disclosed.  A client's identity will be generated when setting up a mailbox, and no central authority will be used to stop collisions from occuring.  Clients may have multiple identities and mailboxes at the same time.

**Identity options**
- Anonymous: Identity is not disclosed (Special cases)
- Envelope ID: A 256 bit number which be sent on the outside of their Envelope (Identity is in the clear)
- Message ID: A 256 bit number, which will be sent inside your encrypted Envelope.  (The envelope must be opened in order to learn their identity)

The mailbox owner can specify what identity level is required for messages to be received.

Verification Control
------------

Messages sent with this system have various ways to verify their integrity and identity.

- NONE: No integrity or identity verification
- HMAC: A special hash, which requires a known shared secret. (Weak identity/validation check)
- SIGNATURE: Public/Private keys are used with a hash (Proves identity and validation)

Verification uses a cascade system to improve overal reliability.  A client can include an HMAC and Signature with their message.  The HMAC would be calculated by hashing the encrypted content/key and the generated time(If available).  The Singature would also be created by hashing the encrypted content/key, the generated time(If available) and HMAC value.

Envelope Encryption
------------

The encryption used on the envelope will be either AES, DES, TripleDES or Blowfish.  The symmetric key plus other fields will be encrypted with the recipient's public key.

**Notes**:
- The symmetric encryption key will be unique for each message.
- The decrypted key will provide additional information, such as the encryption scheme used, and when it expires.

How would clients send mail?
------------

Sending messages will not be as simple as the existing e-mail systems.

For example, say you want to send a message to another user you've never communicated with before.  You would either need to meet them in person to exchange private details or find their publicly disclosed details.  Since this contact posted a QR/file code on a webpage, you are able to import their details into your local mail client.  Writing them a message will not be very different, but before sending, you need to specify the identity level you wish to disclose.  Because you want two way communication with them, you will provide the HMAC verification, disclose your identity/connection/public key in the encrypted message.  Once the client confirms sending the message, its content will be package and made ready for sending.  The envelope file will be posted to the remote server which will place it in the correct box.

- The message system is being designed to work in different modes that offer various levels of security.

**Open mailbox:**
You would post online/print a QR code with your connection details, display name and public key.  This would be preferred for mail messages that are not sensitive, but you still want a layer of protection.  Clients who send messages can also disclose their return details for two way communication to start.

- Spam messages, as with current e-mail
- General/Public communication

**Private mailbox:**
This would entail you setting up a mail box with different public/private keys from any other connection.  The connection details would not be posted to the public and you would need to transfer them securly.

- Business partners
- Family members

Format Specifications
================

Envelope Format
------------

- Contents: The encrypted message contents (Required, GZIP &gt; Encrypted)
- Key: The symmetric key used to decrypt the message.  This has been encrypted with the recipients public key. (Required, Key Format &gt; Encrypted)
- Generated: The date/time the message was generated in UTC (Optional, Date)
- HMAC: Hash to verify content has not been altered, requires a large shared secret (Optional, HMAC Format)
- Signature: Secure hash used to verify sender's identity and integrity (Optional, Signature Format)

HMAC Format
------------

- hmac: The bytes to verify against (Required, bytes)
- algorithm: The algorithm used (Required, UTF-8 String)

Signature Format
------------

- identity: The sender's 256 bit number, if not sent, the envelope will need to be opened first to find out their identity (Optional, bytes)
- signature: The bytes to verify against (Required, bytes)
- algorithm: The algorithm used (Required, UTF-8 String)

Message Format
------------

- Pre Junk: Random bytes posted on the front to alter a the message's length (Optional, bytes)
- Message identity: 256 bit number unique to this message (Required, bytes)
- Related identity: 256 bit number of another message, used for threading (Optional, bytes)
- Generated: The date/time the message was generated in UTC (Optional, Date)
- Subject: The tagline for the message (Optional, UTF-8 String)
- Identity: Identity information (Optional, Identity Format)
- Files: A list of files (Required, List of File Format)
- Post Junk: Random bytes posted on the front to alter the message's length (Optional, bytes)

Identity Format
------------

- id: 256 bit number (Required, bytes)
- key: The public key (Optional, Key Format)
- connection: How to reach them (Optional, Connection Format)

Key Format
------------

- type: The key type (Required, ENUM)
- algorithm: The algorithms name (Required, ENUM)
- mode: The algorithms mode (Required, ENUM)
- padding: The algorithms padding scheme (Required, ENUM)
- key: The key's bytes (Required, bytes)
- format: How is the key formatted? (Required, UTF-8 String)
- keySize: The size in bits for the key (Required, Int)
- iv: The initialization vector (Optional, bytes)
- start: When does this key start being valid in UTC (Optional, Date)
- expire: When does this key expire in UTC (Optional, Date)

File Format
------------

- content: The file's bytes (Required, bytes)
- name: The file's name (Required, UTF-8 String)
- mime: The file's mime type (Required, UTF-8 String)

Connection Format
------------

- host: The server's host name (Required, UTF-8 String)
- port: The server's port for communication (Required, Int)
- path: The server's path for web server integration (Optional, , UTF-8 String)
- hmac: Shared secret for HMAC (Optional, bytes)
- acceptance: What minimal level of identity/integrity is required for it to be accepted? (Required, ENUM)

The acceptance levels will specify what the server requires in order to accept messages.  For example if the level is set to HMAC, than a Signature can be substituted since it provides a higher level of integrity and identity verification.  For secure/private communications, Signature Open/Hidden can be specified, which requires a signature tag to be present, but the sender's identity can be hidden inside the message if available.  Since you can hide a sender's identity, a message would need to be opened first before confirming its validity, so a HMAC can also be included to add additional levels of protection from tampering.  Also signature's/HMAC's would be built from multiple sources, such as the encrypted envelope/key bytes, time it was generated, and HMAC if possible.  Having the Signature/HMAC built from multiple sources adds an extra layer of protection, since if a piece of the chain was altered or removed the resulting hashes would fail.

Appendix
================

Spam Control
------------

In order to reduce spam servers can deploy a proof of work requirement on clients sending e-mail.  The proof of work will require clients to hash a set of bytes with a specified algorithm and meet a list of requirements.  The set of bytes will need be different for each client connection.  The requirements will specify what rules must be met for the message to be accepted.

**Possible Rules**
- Starts with bytes & mask
- Ends with bytes & mask
- Contains bytes & mask

*This system is similar to how Bitcoin generates coins.*

Threads
------------

Unlike current mail systems where thread views have been a late addition, each message in our system will have it's own unique identity number.  When a client receives a message, it can check if its linked to another message and properly thread the mails.  Also with this system, previous messages don't need to be included with reply's.

Key Exchanges
------------

This format is being designed for individuals to maintain a high level of security and privacy from 3rd party involvement.  So the level of security will be determined by how connection and identity information is exchanged between clients.

For general or public communication, a clients details can be posted online for other to see and use.  This would work similar to how e-mail is distributed now by telling someone your address, but it will be more complicated.  These connections will leak critical information and should not be trusted for secure conversations.

- Public key is in the open
- Low barrier to send and receive mail
- High chance of being compromised
- Little trust

For a high security example imagine that you were a whistle blower wanting to disclose information to an journalist.  You would need to meet face to face and exchange communication details in person without involving the internet or networking.  Meeting in person lowers the possibility for 3rd party spying and information leakage.  When the two parties communicate nothing is disclosed about who sent the message since identities will be striped and a simple HMAC check will be used to stop tampering.  The server will also require a basic proof of work and messages must start transferring within 5 minutes of their generation.  *If a 3rd party tried to resend a message with an altered generated time-stamp it would make it through the proof of work filter, but a later HMAC check would disqualify it from being fully accepted*

- Public keys are only known by end points
- High barrier to send and receive mail
- Low chance of being compromised
- Higher trust
- More restrictions on mail flow by requiring clients to quickly send messages and perform a basic proof of work.



This is still in early development, so watch out!
