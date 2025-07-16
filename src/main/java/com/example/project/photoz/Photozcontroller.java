package com.example.project.photoz;


import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/getphoto")
    public List<Photo> getphoto(){
        System.out.println("photos are:");
        return db_sample;
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
    public Photo createphoto(@RequestParam String id, @RequestParam String filename) {
    System.out.println("photos from db(hashmap)");
    if (db.containsKey(id)) {
        throw new RuntimeException("Photo already exists with id: " + id);
    }
    db.put(id, filename);
    return new Photo(id, filename);
}
      
}
