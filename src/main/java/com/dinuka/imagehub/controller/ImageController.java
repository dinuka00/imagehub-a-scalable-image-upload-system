package com.dinuka.imagehub.controller;

import com.dinuka.imagehub.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/images")
    public Object uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return imageService.save(file, categoryId);
    }
}
