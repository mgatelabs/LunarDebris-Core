package com.mgatelabs.lunardebris.support.tags;

import java.util.Date;
import java.util.List;

/**
 * Created by Michael Fuller on 1/18/14.
 */
public class LetterTransport {

    // Some junk

    // The time the letter was generated, in UTC time.  Used for alternative verification with existing exterior value.
    private Date generated;

    // The letter's subject
    private String subject;

    // The letter's unique identity
    private byte [] identifier;

    // Optional reference to another letter
    private byte [] reference;

    // Files sent with this letter
    private List<FileTransport> files;

    // More junk

}
