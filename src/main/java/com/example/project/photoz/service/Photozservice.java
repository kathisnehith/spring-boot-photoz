package com.example.project.photoz.service;

import java.util.Collection;
import java.util.HashMap;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.project.photoz.model.Photo;
import com.example.project.photoz.repository.Photozrepository;

//@Component
@Service
public class Photozservice {
    
    private final Photozrepository photozrepository;

    public Photozservice(Photozrepository photozrepository) {
        this.photozrepository = photozrepository;
    }

    public Iterable<Photo> get() {
        return photozrepository.findAll();
    }


    public Photo get(String id) {
        return photozrepository.findById(id).orElse(null);
    }


    public void remove(String id) {
            photozrepository.deleteById(id);
    }


    public Photo save(String filename, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setId(UUID.randomUUID().toString());
        photo.setFilename(null != filename ? filename : "unknown.jpg");
        photo.setContentType(contentType);
        photo.setData(data);
        photozrepository.save(photo);
        return photo;
    }



    
    
}
