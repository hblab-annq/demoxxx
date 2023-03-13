package com.dado.quanlytailieu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dado.quanlytailieu.model.Image;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private Path fileStorageLocation = null;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileRepository fileRepository;

    public ResponseDto findImageByName(String name) throws IOException {
        Path imagePath = Paths.get("uploads/files/" + name);
        File folder = new File("uploads/files");
        var image = imageRepository.getImageByFileName(name);
        if(image == null){
            return ResponseDto.builder()
                    .message("Image doesn't exist!")
                    .httpCode(HttpStatus.BAD_REQUEST).build();
        }
        Resource imageResource = null;

        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {

                if(listOfFiles[i].getName().equals(image.getFileName())){
                    imageResource = new UrlResource(imagePath.toUri());
                }
        }
        if(imageResource == null) {
            return ResponseDto.builder()
                    .message("Cannot get resource of image")
                    .httpCode(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseDto.builder()
                .message("Get resource successfully")
                .httpCode(HttpStatus.OK)
                .body(imageResource).build();
    }

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

    public ResponseDto storeFile(MultipartFile[] files, String id) throws Exception {
        Set<String> set = new HashSet<>();
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file: files) {
            if(file.getSize() <= 0){
                return ResponseDto.builder()
                        .message("File size < 0!")
                        .httpCode(HttpStatus.BAD_REQUEST).build();
            }else if (!set.add(file.getOriginalFilename())){
                return ResponseDto.builder()
                        .message("File name is duplicate!")
                        .httpCode(HttpStatus.BAD_REQUEST).build();
            } else {
                var fileEntity = fileRepository.findById(Long.valueOf(id)).get();
                if(fileEntity == null){
                    return ResponseDto.builder()
                            .message("File not exist!")
                            .httpCode(HttpStatus.BAD_REQUEST).build();
                }
                var imageInfo = imageRepository.getImageByFileId(fileEntity.getId(),file.getOriginalFilename());
                if(imageInfo != null){
                    if(imageInfo.getFileName().equals(file.getOriginalFilename())){
                        return ResponseDto.builder()
                                .message("Image is exist!")
                                .httpCode(HttpStatus.BAD_REQUEST).build();
                    }
                }
                var imageEntity = Image.builder()
                        .fileName(file.getOriginalFilename())
                        .type(file.getContentType())
                        .createdTime(LocalDateTime.now())
                        .fileEntity(fileEntity).build();
                imageRepository.save(imageEntity);

                // Normalize file name
                String fileName = file.getOriginalFilename();
                try {
                    // Check if the filename contains invalid characters
                    if (fileName.contains("..")) {
                        return ResponseDto.builder()
                                .message("Sorry! Filename contains invalid path sequence " + fileName)
                                .httpCode(HttpStatus.BAD_REQUEST).build();
                    }

                    Path targetLocation = this.fileStorageLocation.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    fileNames.add(fileName);
                } catch (IOException ex) {
                    return ResponseDto.builder()
                            .message("Could not store file " + fileName)
                            .httpCode(HttpStatus.BAD_REQUEST).build();
                }
            }
        }
        String imageUpload = String.join(",", fileNames);
        return ResponseDto.builder()
                .message("Successfully")
                .httpCode(HttpStatus.OK)
                .body(imageUpload).build();
    }

    public ResponseDto deleteImageById(String id) throws Exception {
        var image = imageRepository.findById(Long.valueOf(id)).get();
        if(image == null){
            return ResponseDto.builder()
                    .message("Image is not exist! ")
                    .httpCode(HttpStatus.BAD_REQUEST).build();
        }
        Path imagePath = Paths.get("uploads/files/" + image.getFileName());
        File folder = new File("uploads/files");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {

            if(listOfFiles[i].getName().equals(image.getFileName())){
                Files.deleteIfExists(imagePath);
            }
        }
        imageRepository.deleteById(image.getId());
        return ResponseDto.builder()
                .message("Successfully")
                .httpCode(HttpStatus.OK).build();
    }

    public ResponseDto updateImage(String id, String createdTime, String createdUser, MultipartFile file, int fileId ) throws Exception {
        var image = imageRepository.findById(Long.valueOf(id)).get();
        if(image == null){
            return ResponseDto.builder()
                    .message("Image is not exist! ")
                    .httpCode(HttpStatus.BAD_REQUEST).build();
        }
        Path imagePath = Paths.get("uploads/files/" + image.getFileName());
        File folder = new File("uploads/files");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            Files.deleteIfExists(imagePath);
            if(listOfFiles[i].getName().equals(image.getFileName())){
                String fileName = file.getOriginalFilename();
                try {
                    // Check if the filename contains invalid characters
                    if (fileName.contains("..")) {
                        return ResponseDto.builder()
                                .message("Sorry! Filename contains invalid path sequence " + fileName)
                                .httpCode(HttpStatus.BAD_REQUEST).build();
                    }

                    Path targetLocation = this.fileStorageLocation.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    return ResponseDto.builder()
                            .message("Could not store file " + fileName)
                            .httpCode(HttpStatus.BAD_REQUEST).build();
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(createdTime, formatter);

                imageRepository.updateImageById(dateTime, createdUser, fileName, file.getContentType(), fileId, Integer.valueOf(image.getId().toString()));
            }
        }
        return ResponseDto.builder()
                .message("Successfully")
                .httpCode(HttpStatus.OK).build();
    }
}
