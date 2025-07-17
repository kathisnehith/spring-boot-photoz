package com.example.project.photoz.service;

import java.util.Collection;
import java.util.HashMap;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.project.photoz.model.Photo;

//@Component
@Service
public class Photozservicemain {
    private HashMap<String, Photo> db = new HashMap<>() {{
        put("01", new Photo("01", "photo1.jpg"));
        put("02", new Photo("02", "photo2.jpg"));
    }};

    public Collection<Photo> get() {
        return db.values();
    }


    public Photo get(String id) {
        return db.get(id);
    }


    public Photo remove(String id) {
            return db.remove(id);
    }


    public Photo save(String filename, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setId(UUID.randomUUID().toString());
        photo.setFilename(null != filename ? filename : "unknown.jpg");
        photo.setContentType(contentType);
        photo.setData(data);
        db.put(photo.getId(), photo);
        return photo;
    }



    
    
}
