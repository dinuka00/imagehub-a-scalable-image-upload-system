package com.dinuka.imagehub.controller;

import com.dinuka.imagehub.dto.ImageDTO;
import com.dinuka.imagehub.entity.Image;
import com.dinuka.imagehub.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/images")
    public Object uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return imageService.save(file, categoryId);
    }

    @PutMapping("/images/{id}")
    public Object updateImage(@RequestBody ImageDTO imageDTO, @PathVariable Long id) {

        return imageService.update(imageDTO,id);
    }

    @GetMapping("/image/{id}")
    public Image getImage(@PathVariable Long id) {
        return imageService.findById(id);
    }

    @GetMapping("/images")
    public List<Image> getImages() {
        return imageService.findAll();
    }

    @DeleteMapping("/image/{id}")
    public String deleteImage(@PathVariable Long id) {
        return imageService.deleteById(id);
    }

}
