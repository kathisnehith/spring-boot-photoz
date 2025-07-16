package com.example.project.photoz.web;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.photoz.model.Photo;
import com.example.project.photoz.service.Photozservice;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Photozcontroller {

    private Photozservice photozservice;

    public Photozcontroller(Photozservice photozservice) {
        this.photozservice = photozservice;
    }

    private HashMap<String, String> db = new HashMap<String, String>() {{
        put("01", "photo1.jpg");
        put("02", "photo2.jpg");
    }};

    private List<Photo> db_sample= List.of(
        new Photo("1", "photo1.jpg")
    );

    

    @GetMapping("/")
    public String hello(){
        return "Hello, Photoz!!";
    }

    //get data from photozservice
    @GetMapping("/photoz")
    public Collection<Photo> getAllPhotosFromService() {
        return photozservice.get();
    }

    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable String id){
        System.out.println("photos from db(hashmap)");
        String filename = db.get(id);
        if (filename == null) throw new RuntimeException("Photo not found with id: " + id);
        return new Photo(id, filename);
    }

    @DeleteMapping("/photoz/{id}")
    public Photo delete(@PathVariable String id){
        System.out.println("photos from db(hashmap)");
        Photo photo = photozservice.get(id);
        if (photo == null) throw new RuntimeException("Delete failed, photo not found: " + id);
        photozservice.remove(id);
        return photo;
    }


    @PostMapping("/photoz")
    public ResponseEntity<Photo> create(@RequestPart("data") MultipartFile file) throws IOException {
    Photo photo = photozservice.save(file.getOriginalFilename(), file.getContentType(),file.getBytes());
    return ResponseEntity.ok(photo);
    }


// // // // // // // // // // // 

    //get the data of phots from db or something else
    @GetMapping("/getphotosample")
    public List<Photo> getphoto(){
        System.out.println("photos are:");
        return db_sample;
    }

    //get the data of phots from db or something else
    @GetMapping("/getphoto")
    public List<Photo> getAllPhotos() {
        return db.entrySet().stream()
            .map(entry -> new Photo(entry.getKey(), entry.getValue()))
            .toList();
    }

    @GetMapping("/getphoto/{id}")
    public Photo getphoto(@PathVariable String id){
        System.out.println("photos from db(hashmap)");
        String filename = db.get(id);
        if (filename == null) throw new RuntimeException("Photo not found with id: " + id);
        return new Photo(id, filename);
    }

    @DeleteMapping("/getphoto/{id}")
    public Photo deletephoto(@PathVariable String id){
        System.out.println("photos from db(hashmap)");
        String filename = db.get(id);
        if (filename == null) throw new RuntimeException("Delete failed, photo not found: " + id);
        db.remove(id);
        return new Photo(id, filename);
    }

    @PostMapping("/getphoto")
    public ResponseEntity<Photo> createPhoto(@RequestBody @Valid Photo photo) {
    System.out.println("Adding photo to db (hashmap)");
    if (photo.getId() == null || photo.getFilename() == null) {
        return ResponseEntity.badRequest().build();
    }
    if (db.containsKey(photo.getId())) {
        return ResponseEntity.status(409).build(); // Conflict
    }
    db.put(photo.getId(), photo.getFilename());
    return ResponseEntity.ok(new Photo(photo.getId(), photo.getFilename()));
    }

    @PostMapping("/uploadphoto")
    public ResponseEntity<Photo> createPhoto(@RequestPart("data") MultipartFile fileupload) throws IOException {
    System.out.println("uploading photo to db (hashmap)");
    if (fileupload == null || fileupload.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    if (fileupload.getSize() > 1048576) {
        throw new RuntimeException("File size exceeds 1MB limit. Current size: " + fileupload.getSize() + " bytes");
    }
    Photo photo = new Photo();
    photo.setId(UUID.randomUUID().toString());
    photo.setFilename(null != fileupload ? fileupload.getOriginalFilename() : "unknown.jpg");
    photo.setData(fileupload.getBytes());
    db.put(photo.getId(), photo.getFilename());
    return ResponseEntity.ok(new Photo(photo.getId(), photo.getFilename()));
    }
      
}
