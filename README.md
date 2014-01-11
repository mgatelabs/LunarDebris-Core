LunarDebris-Core
================

Lunar Debris is a library of classes, which will be used to securly transport messages over the internet.  The main goal of this project is to remove 3rd parties which are currently infused into the standard mail lifecycle.  This project will not attempt to interface with the existing mail systems. 

Goals
------------

- Messages are only readable by the intended recipetent
- Control identity, and verification
- Low file format overhead with ByteMapper

Identity Control
------------

Unlike standard mail systems you don't need a 3rd party "server" to send mail.  Instead to send a message, you will need the recipients public key and connection properties.  And in the message you can opt to reveal your identity in a number of ways...

- NONE: Don't reveal your identity (Special cases)
- Envelope ID: A 256 bit number which be sent on the outside of their Envelope (Identity is in the open)
- Message ID: A 256 bit number, which will be sent inside your encrypted Envelope.  (The envelope must be opened in order to learn their identity)

The mailbox owner can specify what identiy level is required for messages to be received.

*The 256 bit identity number is not controlled by a central source.*

How would clients send you mail?
------------

There are varius ways people could send you messages with different security concerns.

**Open mailbox:**
You would post details, such as a QR code with your connection details, display name and public key.  This would be prefered for mail messages that are not sensative, but you still want a layer of protection.  People who send messages can also post their return details for two way communication to start.

- Spam messages, as with current e-mail
- General communication

**Private mailbox:**
This would entail you setting up a mail box with a different public/private key from the **open** connection.  The connection details would not be posted to the public.  At this point you would need to physically transfer connection details to people you wish to send and receive messages from.

- Business partners
- Family members

How are messages handled
------------

Messages are a collection of varius items, such as files, connection properties, message details, identity markers.

Unlike current mail systems where thread views have been superimposed, each letter will have it's own unique identity number.  When you reply to a letter the source letter's identity will be referenced, so proper message threading can occur.

This is still in early development, so watch out!
