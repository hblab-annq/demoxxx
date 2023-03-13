package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.command.ImageUpdateCommand;
import com.dado.quanlytailieu.command.ImageUploadCommand;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity getImageDetails(@RequestParam("name") String name) throws Exception {
        var image = imageService.findImageByName(name);
        if(image.getBody() == null){
            return ResponseEntity.badRequest().body(image.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");
        return new ResponseEntity(image.getBody(), headers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseDto uploadFile(@ModelAttribute ImageUploadCommand command) throws Exception {
        var fileName = imageService.storeFile(command.getFiles(), command.getId());
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
}
