package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import com.dado.quanlytailieu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@ModelAttribute FileUploadDto fileUploadDTO) throws IOException {

        // Kiểm tra định dạng file
        if (!Objects.equals(fileUploadDTO.getFile().getContentType(), "application/pdf") &&
                !Objects.equals(fileUploadDTO.getFile().getContentType(), "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(fileService.uploadFile(fileUploadDTO));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException, MalformedURLException {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

        String uploadDir = "uploads/";
        String filePath = uploadDir + file.getId() + "-" + file.getName();

        Resource resource = new UrlResource("file:" + filePath);

        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
