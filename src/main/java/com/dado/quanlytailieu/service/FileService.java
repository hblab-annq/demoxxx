package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public FileEntity uploadFile(FileUploadDto fileUploadDTO) throws IOException {

        // Tạo file
        String filename = StringUtils.cleanPath(fileUploadDTO.getFile().getOriginalFilename());
        FileEntity fileEntity = new FileEntity(fileUploadDTO.getName() != null ? fileUploadDTO.getName() : filename);
        fileEntity = fileRepository.save(fileEntity);

        String uploadDir = resourceLoader.getResource("classpath:static/uploads/").getFile().getAbsolutePath();
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String filePath = uploadDir + File.separator + fileEntity.getId() + "-" + filename;
        File dest = new File(filePath);
        fileUploadDTO.getFile().transferTo(dest);

        return fileEntity;
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) throws FileNotFoundException {

        FileEntity file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);

        String uploadDir = "uploads/";
        String filePath = uploadDir + file.getId() + "-" + file.getName();

        Resource resource = null;
        resource = resourceLoader.getResource("file:" + filePath);
        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
