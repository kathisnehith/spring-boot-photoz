package com.example.project.photoz;

import jakarta.validation.constraints.NotEmpty;

public class Photo {

    
    private String id;
    @NotEmpty
    private String filename;

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

    

    
}