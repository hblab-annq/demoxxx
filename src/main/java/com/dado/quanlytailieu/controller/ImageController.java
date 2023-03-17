package com.dado.quanlytailieu.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import com.dado.quanlytailieu.command.ImageUpdateCommand;
import com.dado.quanlytailieu.command.ImageUploadCommand;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("/download")
    public ResponseEntity downloadImage(@RequestParam("id") String id) throws Exception {
        var image = imageService.downloadImage(id);
        if(image == null){
            return ResponseEntity.badRequest().body("Resource of image error");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"");
        return new ResponseEntity(image, headers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseDto uploadFile(@ModelAttribute ImageUploadCommand command) throws Exception {
        var fileName = imageService.storeImage(command.getFiles(), command.getId());
        return ResponseDto.builder()
                .message(fileName.getMessage())
                .httpCode(fileName.getHttpCode()).build();
    }

    @DeleteMapping("/delete")
    public ResponseDto deleteImageById(@RequestParam(name = "id", required = true) String id) throws Exception {
        var image = imageService.deleteImageById(id);
        return ResponseDto.builder()
                .message(image.getMessage())
                .httpCode(image.getHttpCode())
                .body(image.getBody()).build();
    }

    @PutMapping("/update")
    public ResponseDto updateImageById(
            @ModelAttribute ImageUpdateCommand command) throws Exception {
        var image = imageService.updateImage(command.getId()
                , command.getCreatedTime()
                , command.getCreatedUser()
                , command.getFile()
                , command.getFileId());

        return ResponseDto.builder()
                .message(image.getMessage())
                .httpCode(image.getHttpCode())
                .body(image.getBody()).build();
    }

    @GetMapping(value = "/{id:.+}")
    public ResponseEntity<List<Resource>> getPreviewFile(@RequestParam String id) throws MalformedURLException {
        var resource = imageService.getImagePath(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        if(resource.size()<0){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@RequestParam(name = "id")String id){
        var image = imageService.getImageById(id);
        if(Objects.isNull(image)){
            return ResponseEntity.badRequest().body("Image not exist");
        }
        return ResponseEntity.ok().body(image);
    }

    @GetMapping("/{constructionId}")
    public ResponseEntity<?> getImageByConstructionId(@RequestParam(name = "constructionId") String id) throws MalformedURLException {
        return ResponseEntity.ok().body(imageService.getImagePath(id));
    }
}
