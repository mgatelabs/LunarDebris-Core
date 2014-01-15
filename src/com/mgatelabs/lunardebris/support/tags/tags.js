{
    "name":"Lunar Debris Mail Transport Specification",
    "description": "The tags required to send and receive secret mail messages",
    "min": "1",
    "max": "1",
    "tags": [

        {
            "name": "MailTransport",
            "description": "Used to send messages to and from servers",
            "identity": "1",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "envelope",
                    "description":"Holds the symmetric encrypted message, using symmetric encryption",
                    "type": "filelink",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "key",
                    "description":"Holds an encryption transport instance, encrypted with RSA",
                    "type": "filelink",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "openIdentity",
                    "description":"Holds an open identity transport instance, encrypted with key",
                    "type": "tag/IdentityTransport",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "encryptedIdentity",
                    "description":"Holds an encrypted identity transport instance, encrypted with key",
                    "type": "filelink",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "generated",
                    "description":"The date/time the letter was generated, in UTC",
                    "type": "date",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "hash",
                     "description":"The optional hash of the (message + key + date) and security values",
                     "type": "tag/DigestTransport",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                    "name": "hmac",
                    "description":"The optional HMAC of the (message + key + date) or hash",
                    "type": "tag/MacTransport",
                    "min": "1",
                    "max": "0",
                    "required":"false",
                    "properties": {}
                },
                {
                 "name": "signature",
                 "description":"The optional Signature of the (message + key + date) or hash",
                 "type": "tag/SignatureTransport",
                 "min": "1",
                 "max": "0",
                 "required":"false",
                 "properties": {}
                }
            ]
        },

        {
            "name": "EncryptionTransport",
            "description": "Used to transport encryption details",
            "identity": "100",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "key",
                    "description":"Holds the key",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "keySize",
                    "description":"Holds the symmetric key bit length",
                    "type": "int",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "format",
                     "description":"Format key is saved as",
                     "type": "String",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                    "name": "iv",
                    "description":"Holds the initialization vector",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "type",
                     "description":"The encryption type",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                     "name": "algorithm",
                     "description":"The encryption algorithm",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                     "name": "mode",
                     "description":"The encryption mode",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                    "name": "padding",
                    "description":"The encryption padding scheme",
                    "type": "enum",
                    "min": "1",
                    "max": "0",
                    "required":"false",
                    "properties": {}
                },
                {
                 "name": "start",
                 "description":"The starting valid datetime in UTC",
                 "type": "date",
                 "min": "1",
                 "max": "0",
                 "required":"false",
                 "properties": {}
                },
                {
                  "name": "expire",
                  "description":"The expire datetime in UTC",
                  "type": "date",
                  "min": "1",
                  "max": "0",
                  "required":"false",
                  "properties": {}
                }
            ]
        },

        {
            "name": "DigestTransport",
            "description": "Used to transfer digest details",
            "identity": "101",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "digest",
                    "description":"Holds the key",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "algorithm",
                     "description":"The encryption type",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                }
            ]
        },

        {
            "name": "MacTransport",
            "description": "Used to transfer HMAC details",
            "identity": "102",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "hmac",
                    "description":"Holds the HMAC",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "algorithm",
                     "description":"The encryption type",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                }
            ]
        },

        {
            "name": "SignatureTransport",
            "description": "Used to transfer Signature details",
            "identity": "103",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "identity",
                    "description":"Holds the sender\'s identity",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "signature",
                    "description":"Holds the signature's bytes",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "algorithm",
                     "description":"The algorithm to used",
                     "type": "enum",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                }
            ]
        },

        {
            "name": "ConnectionTransport",
            "description": "Used to transfer connection details",
            "identity": "104",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "host",
                    "description":"The hosts identity",
                    "type": "string",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "port",
                    "description":"The port number",
                    "type": "int",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "path",
                     "description":"The optional path",
                     "type": "string",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                  "name": "hmac",
                  "description":"The hmac shared secret",
                  "type": "blob",
                  "min": "1",
                  "max": "0",
                  "required":"false",
                  "properties": {}
                },
                {
                    "name": "verification",
                    "description":"The minimal verification required",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "identity",
                     "description":"The minimal identification required",
                     "type": "blob",
                     "min": "1",
                     "max": "0",
                     "required":"true",
                     "properties": {}
                }
            ]
        },

        {
            "name": "IdentityTransport",
            "description": "Used to transfer identity details",
            "identity": "105",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "identity",
                    "description":"Holds the sender\'s identity",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "key",
                    "description":"Holds the sender\'s public key",
                    "type": "tag/EncryptionTransport",
                    "min": "1",
                    "max": "0",
                    "required":"false",
                    "properties": {}
                },
                {
                     "name": "address",
                     "description":"How to reach the sender",
                     "type": "tag/ConnectionTransport",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                }
            ]
        },

        {
            "name": "EncryptedIdentityTransport",
            "description": "Used to transfer identity details with extra noise to change how it looks every time",
            "identity": "106",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "-",
                    "description":"Noise 1",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {
                        "min":"5",
                        "max":"10",
                    }
                },
                {
                    "name": "identity",
                    "description":"Holds the sender\'s identity",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "-",
                    "description":"Noise 1",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {
                        "min":"5",
                        "max":"15",
                    }
                },
                {
                    "name": "key",
                    "description":"Holds the sender\'s public key",
                    "type": "tag/EncryptionTransport",
                    "min": "1",
                    "max": "0",
                    "required":"false",
                    "properties": {}
                },
                {
                    "name": "-",
                    "description":"Noise 1",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {
                        "min":"5",
                        "max":"15",
                    }
                },
                {
                     "name": "address",
                     "description":"How to reach the sender",
                     "type": "tag/ConnectionTransport",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                },
                {
                    "name": "-",
                    "description":"Noise 1",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {
                        "min":"5",
                        "max":"10",
                    }
                }
            ]
        },

    ]
}