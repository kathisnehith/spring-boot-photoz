package com.example.project.photoz;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Photozcontroller {

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
    public ResponseEntity<Photo> createPhoto(@RequestBody Photo photo) {
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
      
}
