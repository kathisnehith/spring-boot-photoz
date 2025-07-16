package com.example.project.photoz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

public class Photo {

    
    private String id;
    @NotEmpty
    private String filename;
    @JsonIgnore
    private byte[] data;

    private String contentType;
    // Default constructor
    public Photo() {
        
    }

    // Parameterized constructor
    public Photo(String id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    // getters, and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    

    

    
}