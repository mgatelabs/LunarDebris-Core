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
                    "name": "message",
                    "description":"Holds the symmetric encrypted message, using symmetric encryption",
                    "type": "filelink",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "security",
                    "description":"Holds the details to unlock the message, using RSA encryption",
                    "type": "filelink",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                     "name": "hash",
                     "description":"The optional hash of the message and security values",
                     "type": "blob",
                     "min": "1",
                     "max": "0",
                     "required":"false",
                     "properties": {}
                }
            ]
        },

        {
            "name": "SymmetricEncryption",
            "description": "Used to perform blowfish encryption",
            "identity": "100",
            "min": "1",
            "max": "0",
            "fields": [
                {
                    "name": "encodedKey",
                    "description":"Holds the symmetric key",
                    "type": "blob",
                    "min": "1",
                    "max": "0",
                    "required":"true",
                    "properties": {}
                },
                {
                    "name": "initializationVector",
                    "description":"Holds the symmetric initialization vector",
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
                }
            ]
        }

    ]
}