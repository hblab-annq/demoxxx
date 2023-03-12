package com.dado.quanlytailieu.controller;

import java.util.ArrayList;
import java.util.List;

import com.dado.quanlytailieu.model.Image;
import com.dado.quanlytailieu.service.ImageService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    ImageService imageService;

//    @PostMapping("upload")
//    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file) throws Exception {
//        imageService.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Image uploaded successfully: " +
//                        file.getOriginalFilename());
//    }
//
    @GetMapping("/get/{name}")
    public ResponseEntity<Resource> getImageDetails(@RequestParam("name") String name) throws Exception {
        var image = imageService.findImageByName(name);
        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.IMAGE_JPEG);
        type.add(MediaType.IMAGE_PNG);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(type);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
//
//    @GetMapping("/allimages")
//    public void getAllImage(){
//        imageService.getAllImages();
//    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam(name = "file", required = true) MultipartFile[] file,
            @RequestParam(name= "idFile", required = true) String id
    ) throws Exception {
        String fileName = imageService.storeFile(file, id);
        return ResponseEntity.ok().body("Image uploaded successfully");
    }
}
