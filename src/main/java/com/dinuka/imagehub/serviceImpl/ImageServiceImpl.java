package com.dinuka.imagehub.serviceImpl;

import com.dinuka.imagehub.dto.ImageDTO;
import com.dinuka.imagehub.entity.Category;
import com.dinuka.imagehub.entity.Image;
import com.dinuka.imagehub.entity.User;
import com.dinuka.imagehub.exceptions.ImageNotFoundException;
import com.dinuka.imagehub.exceptions.UserNotFoundException;
import com.dinuka.imagehub.repository.CategoryRepository;
import com.dinuka.imagehub.repository.ImageRepository;
import com.dinuka.imagehub.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    private static final String upload_Dir = "D:/Projects/imagehub/upload";



    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Object save(MultipartFile file, Integer categoryId, Long userId)  throws NullPointerException {

       try {

           String mainFileType = file.getContentType();

           assert mainFileType != null;
           String fileType = mainFileType.split("/")[1];

           long fileSizeInBytes = file.getSize();

           double fileSizeInKB = fileSizeInBytes / 1024.0;

           fileSizeInKB = Math.round(fileSizeInKB * 100.0) / 100.0;

           Category existingCategory = categoryRepository.findById(categoryId).orElse(null);

           File directory = new File(upload_Dir);
           if (!directory.exists()) {
               directory.mkdirs();
           }

           String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();

           Path path = Paths.get(upload_Dir + fileName);

           file.transferTo(path.toFile());

           String fileUrl = "/uploads/" + fileName;

           User existingUser = userRepository.findById(userId).orElseThrow(
                   () -> new UserNotFoundException("User not found with id: "+ userId)
           );

           Image image = Image.builder()
                   .name(fileName)
                   .url(fileUrl)
                   .size(fileSizeInKB)
                   .type(fileType)
                   .category(existingCategory)
                   .user(existingUser)
                   .build();


           return imageRepository.save(image);

       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Object update(ImageDTO image, Long id) {

        Image existingImage = imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Image not found with id: "+ id));

        if(image.getName() != null){
            existingImage.setName(image.getName());
        }

        return imageRepository.save(existingImage);

    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Image not found with id: "+ id)
        );
    }

    @Override
    public String deleteById(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(
                () -> new ImageNotFoundException("Image not found with id: "+ id)
        );

        if(image != null){
            imageRepository.deleteById(id);

            return "Image deleted";
        }else {
            return "Image not found";
        }
        
    }
}
