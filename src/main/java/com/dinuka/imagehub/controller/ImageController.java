package com.dinuka.imagehub.controller;

import com.dinuka.imagehub.dto.ImageDTO;
import com.dinuka.imagehub.entity.Image;
import com.dinuka.imagehub.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImageController {


    @Autowired
    private ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                              @RequestParam(value = "userId") Long userId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.save(file, categoryId, userId));
    }

    @PutMapping("/images/{id}")
    public ResponseEntity<Object> updateImage(@RequestBody ImageDTO imageDTO, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.update(imageDTO,id));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findById(id));
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findAll());
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteById(id));
    }
}
