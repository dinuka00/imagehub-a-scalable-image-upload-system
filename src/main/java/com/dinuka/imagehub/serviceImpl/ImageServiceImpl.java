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
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
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
    @Autowired
    private S3Client s3Client;


    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Object save(MultipartFile file, Integer categoryId, Long userId)  throws NullPointerException {

       try {

            String fileType = getFileType(file);

            double fileSizeInKB = Math.round(file.getSize() / 1024.0 * 100.0)*100.0;


           Category existingCategory = categoryRepository.findById(categoryId).orElse(null);


           String fileName = System.currentTimeMillis() + UUID.randomUUID().toString()+"."+fileType;

         //  String fileUrl = saveFileToLocalDirectory(file, fileName, fileType);

           String fileUrl = uploadToS3Bucket(file, fileName);


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

    private String uploadToS3Bucket(MultipartFile file, String fileName) throws IOException {

        String bucketName = "dinuka-img-proj";

        String region = "ap-south-1";

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return "https://"+ bucketName+".s3."+region+ ".amazonaws.com/"+fileName;


    }

    private String saveFileToLocalDirectory(MultipartFile file, String fileName, String fileType) throws IOException {

        File directory = new File(upload_Dir);
        if(!directory.exists()){
            directory.mkdirs();
        }

        Path path = Paths.get(upload_Dir + fileName);
        file.transferTo(path.toFile());

        return "/uploads/"+fileName;
    }

    private String getFileType(MultipartFile file) {

        if(file == null || file.getContentType() == null){
            throw new IllegalArgumentException("Invalid file.add valid file");
        }

        return file.getContentType().split("/")[1];
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
