package com.Shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import java.util.UUID;

@Entity
public class Attachment {

    @Id
    private String id; // String type for the ID

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public Attachment() {
        super();
    }

    public Attachment(String fileName, String fileType, byte[] data) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @PrePersist
    private void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
