package com.intranet.project.repository.user;

import java.sql.Blob;

public class Image {
    private Blob blob;
    private byte[] image;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
