package com.dinuka.imagehub.service;

import com.dinuka.imagehub.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<Image>  findAll();

    Object save(MultipartFile file, Integer categoryId) ;

    Object update(Image image);

    Image findById(Long id);

    String deleteById(Long id);


}
