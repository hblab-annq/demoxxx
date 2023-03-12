package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileEntity uploadFile(FileUploadDto fileUploadDTO) throws IOException {

        // Tạo file
        String filename = StringUtils.cleanPath(fileUploadDTO.getFile().getOriginalFilename());
        FileEntity fileEntity = new FileEntity(fileUploadDTO.getName() != null ? fileUploadDTO.getName() : filename);
        fileEntity = fileRepository.save(fileEntity);

        String uploadDir = "uploads/";
        File uploadDirFileEntity = new File(uploadDir);
        if (!uploadDirFileEntity.exists()) {
            uploadDirFileEntity.mkdir();
        }

        String filePath = uploadDir + fileEntity.getId() + "-" + filename;
        File dest = new File(filePath);
        fileUploadDTO.getFile().transferTo(dest);

        return fileEntity;
    }
}
