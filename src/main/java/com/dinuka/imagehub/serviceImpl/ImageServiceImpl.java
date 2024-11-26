package com.dinuka.imagehub.serviceImpl;

import com.dinuka.imagehub.entity.Category;
import com.dinuka.imagehub.entity.Image;
import com.dinuka.imagehub.repository.CategoryRepository;
import com.dinuka.imagehub.repository.ImageRepository;
import com.dinuka.imagehub.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String upload_Dir = "D:/Projects/imagehub/upload";

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Object save(MultipartFile file, Integer categoryId)  {

       try {

           String mainFileType = file.getContentType();

           String fileType = mainFileType.split("/")[1];

           Long fileSizeInBytes = file.getSize();

           Double fileSizeInKB = fileSizeInBytes / 1024.0;

           fileSizeInKB = Math.round(fileSizeInKB * 100.0) / 100.0;

           Category existingCategory = categoryRepository.findById(categoryId).get();

           if(existingCategory == null) {
               categoryId = null;
           }


           File directory = new File(upload_Dir);
           if (!directory.exists()) {
               directory.mkdirs();
           }

           String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();

           Path path = Paths.get(upload_Dir + fileName);

           file.transferTo(path.toFile());

           String fileUrl = "/uploads/" + fileName;

           Image image = Image.builder()
                   .name(fileName)
                   .url(fileUrl)
                   .size(fileSizeInKB)
                   .type(fileType)
                   .category(existingCategory)
                   .build();

           return imageRepository.save(image);

       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Object update(Image image) {

        return null;
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteById(Long id) {
        return "";
    }
}
