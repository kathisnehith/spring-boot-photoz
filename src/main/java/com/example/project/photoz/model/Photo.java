package com.example.project.photoz.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "PHOTOZ")
public class Photo {

    @Id
    private String id;

    @NotEmpty
    @Column(name = "file_name")
    private String filename;

    @Lob
    @JsonIgnore
    @Column(name = "data")
    private byte[] data;

    @Column(name = "content_type")
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