package com.mgatelabs.lunardebris.support.tags;

import com.mgatelabs.bytemapper.util.FileLink;

/**
 * Created by Michael Fuller on 1/18/14.
 */
public class FileTransport {

    private String filename;
    private String mimetype;
    private FileLink content;

    public FileTransport() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public FileLink getContent() {
        return content;
    }

    public void setContent(FileLink content) {
        this.content = content;
    }
}
