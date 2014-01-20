LunarDebris-Core
================

Lunar Debris is a library of classes, which will be used to securely transport messages over the internet.  The main goal of this project is to remove 3rd parties which are currently infused into the standard mail life cycle.  This project will not attempt to interface with the existing mail systems. 

## Goals

- Messages are only readable by the intended recipient
  - Use strong encryption
  - Limit leaks
- Sender Identity & Verification are optional
  - For sending anonymous and possibly non-verifiable messages
- Low file format overhead
  - This system uses ByteMapper to create tiny binary files.

## Philosophy

### Extremely limited 3rd party involvement

- The clients of the system have fully control over their security and identity.
  - When sending messages a client can determine the level of trust they would like to establish with their recipient.  They can exclude or include certain features used to verify their identity or message authenticity.
- Client's can create and publish their own public/private keys without requiring a 3rd party.
  - The client has the responsibility to create their own public and private keys.  The software will provide options to quickly generate a key, but we will nto force them to use it.
- There is no central authority to guarantee a client's identity number has been taken.
  - Client's can choose their own random number based upon a digest, manual entry, or random noise.
- Multiple mailboxes
  - Unlike current mail systems, one person may have multiple and distinct identities.  Each mailbox can have it's own identity number and public/private keys.  They way clients can create mailboxes for special purposes and filter their messages.  For example client's could have a public identity which has been posted to a website, and separate private identities for work and family.
- There is no centralized way to search for communication details.
  - You simply can't try to spam mail boxes by trying to send to "a, aa, aaa, aaa,..." since their communication details will be very complex and different.  Sending messages will require knowing the correct path, port and host to transmit to, and possibly solving a simple hash.
- Limiting spam
  - Spammers are going to have a fun time trying to send messages, since server's could setup a proof of work sequence for posting messages.  This method will require clients to solve a hashing problem, similar to BitCoin in order to send messages.
- The clients are responsible for posting how to communicate with them.
  - For stealth needs clients can directly communicate details in person.
  - For public needs, such as a website contact form, a QR code or file can be posted online with connection details.

## Flow

## Java Target

The code has been optomized for Java 7 and will not work without further modification under Java 6.  We could have targetted Java 6 and still gotten the same result, but using an unsupported Java version would be a security risk.

## Identity Control

Messages will have various ways to disclose the sender's identity.  Identity is based upon a 256 but number (32 bytes), with the possability of no other information being disclosed.  A client's identity will be generated when setting up a mailbox, and no central authority will be used to stop collisions from occuring.  Clients may have multiple identities and mailboxes at the same time.

**Identity options**
- Anonymous: Identity is not disclosed (Special cases)
- Envelope ID: A 256 bit number which be sent on the outside of their Envelope (Identity is in the clear)
- Message ID: A 256 bit number, which will be sent inside your encrypted Envelope.  (The envelope must be opened in order to learn their identity)

The mailbox owner can specify what identity level is required for messages to be received.

## Verification Control

Messages sent with this system have various ways to verify their integrity and identity.

- NONE: No integrity or identity verification
- HMAC: A special hash, which requires a known shared secret. (Weak identity/validation check)
- SIGNATURE: Public/Private keys are used with a hash (Proves identity and validation)

Verification uses a cascade system to improve overal reliability.  A client can include an HMAC and Signature with their message.  The HMAC would be calculated by hashing the encrypted content/key and the generated time(If available).  The Singature would also be created by hashing the encrypted content/key, the generated time(If available) and HMAC value.

## Envelope Encryption

The encryption used on the envelope will be either AES, DES, TripleDES or Blowfish.  The symmetric key plus other fields will be encrypted with the recipient's public key.

**Notes**:
- The symmetric encryption key will be unique for each message.
- The decrypted key will provide additional information, such as the encryption scheme used, and when it expires.

## How would clients send mail?

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

Unlike current mail systems where threaded views have been a late addition, each message in our system will have it's own unique identity number.  When a client receives a message, it can check if its linked to another message and properly thread the mails.  Also with this system, previous messages don't need to be included with reply's.

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
