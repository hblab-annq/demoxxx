package com.dado.quanlytailieu.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dado.quanlytailieu.model.Image;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.ImageRepository;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private Path fileStorageLocation = null;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileRepository fileRepository;

//    public Image uploadImage(MultipartFile file) throws Exception {
//        if(file.getSize() <= 0){
//            throw new Exception("File size is 0");
//        }
//        return imageRepository.save(Image.builder()
//                .fileName(file.getOriginalFilename())
//                .type(file.getContentType())
//                .image(ImageUtil.compressImage(file.getBytes()))
//                .createdTime(LocalDateTime.now()).build());
//    }
//
//    public Image getImageById(String id) throws Exception {
//        var image = imageRepository.findById(Long.valueOf(id))
//                .orElseThrow(
//                        () -> new Exception("Image with id: " + id + " not exist!"));
//        return image;
//    }
//
    public Resource findImageByName(String name) throws MalformedURLException {
        Path imagePath = Paths.get("uploads/files" + name);
        File folder = new File("uploads/files");
        var image = imageRepository.getImageByFileName();
        Resource imageResource = null;

        File[] listOfFiles = folder.listFiles();
        List<String> listOfFileNames = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                if(listOfFiles[i].getName().equals(image.getFileName())){
                    imageResource = new UrlResource(imagePath.toUri());
                }
            }
        }
        return imageResource;
    }

//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
//        Path imagePath = Paths.get("/path/to/" + imageName);
//        Resource imageResource = new UrlResource(imagePath.toUri());
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(imageResource);
//    }


    @Autowired
    public void ImageService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFile(MultipartFile[] files, String id) throws Exception {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file: files) {
            if(file.getSize() <= 0){
                throw new Exception("File size is 0");
            } else {
                var fileEntity = fileRepository.findById(Long.valueOf(id)).orElseThrow(()-> new Exception("File not exist!"));
                var image = imageRepository.save(Image.builder()
                        .fileName(file.getOriginalFilename())
                        .type(file.getContentType())
                        .createdTime(LocalDateTime.now()).build());
                fileEntity.getImageList().add(image);
                // Normalize file name
                String fileName = file.getOriginalFilename();
                try {
                    // Check if the filename contains invalid characters
                    if (fileName.contains("..")) {
                        throw new RuntimeException(
                                "Sorry! Filename contains invalid path sequence " + fileName);
                    }

                    Path targetLocation = this.fileStorageLocation.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    fileNames.add(fileName);
                } catch (IOException ex) {
                    throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
                }
            }
        }
        String imageUpload = String.join(",", fileNames);
        return imageUpload;
    }
}
