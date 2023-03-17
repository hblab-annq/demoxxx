package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileInfoDto;
import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.service.FileService;
import com.dado.quanlytailieu.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    @Value("${extern.resources.path}")
    private String path;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/all")
    public ResponseEntity<List<FileInfoDto>> getAllFileName() {
        return ResponseEntity.ok(fileService.getAllFileName());
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@ModelAttribute FileUploadDto fileUploadDTO, @RequestHeader String authorization) throws IOException {
        String jwt;
        jwt = authorization.substring(7);
        String createdUser = jwtService.extractUsername(jwt);
        return ResponseEntity.ok(fileService.uploadFile(fileUploadDTO, createdUser));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        return fileService.downloadFile(fileId);
    }

    @GetMapping("/info/{fileId}")
    public ResponseEntity<FileInfoDto> getFileInfo(@PathVariable Long fileId) throws FileNotFoundException {
        return ResponseEntity.ok(fileService.getFileInfo(fileId));
    }

    @GetMapping(value = "/file/{fileName:.+}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPreviewFile(@PathVariable String fileName) {
        Path filePath = Paths.get(path, fileName);

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=" + fileName)
                        .contentLength(Files.size(filePath))
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("createdUser") String createdUser
    ) {

        String filename = fileService.saveFile(file);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(filename);
        fileEntity.setCreatedUser(createdUser);
        fileEntity = fileRepository.save(fileEntity);
        return ResponseEntity.ok(fileEntity);
    }

}
