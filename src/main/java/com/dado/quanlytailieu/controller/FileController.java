package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/all")
    public ResponseDto getAllFileName() {
        var files = fileService.getAllFileName();
        return ResponseDto.builder()
                .message("Successfully!")
                .httpCode(HttpStatus.OK)
                .body(files.getBody()).build();
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@ModelAttribute FileUploadDto fileUploadDTO, @RequestHeader String authorization) throws IOException {
        String jwt;
        jwt = authorization.substring(7);
        String createdUser = jwtService.extractUsername(jwt);
        return ResponseEntity.ok(fileService.uploadFile(fileUploadDTO, createdUser));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        return fileService.downloadFile(fileId);
    }

}
