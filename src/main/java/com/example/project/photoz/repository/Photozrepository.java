package com.example.project.photoz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.photoz.model.Photo;

public interface Photozrepository extends JpaRepository<Photo, String>{

} 