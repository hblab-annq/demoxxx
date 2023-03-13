package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.FileInfoDto;
import com.dado.quanlytailieu.dao.FileUploadDto;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.dto.ResponseDto;
import com.dado.quanlytailieu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Value("${extern.resoures.path}")
    private String path;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public ResponseDto getAllFileName() {
        List<FileEntity> fileEntityList = fileRepository.findAll();
        var listFile = fileEntityList.stream().map(file -> new FileInfoDto(file.getId(), file.getName(), file.getCreatedUser())).collect(Collectors.toList());
        return ResponseDto.builder()
                .message("Successfully")
                .httpCode(HttpStatus.OK)
                .body(listFile).build();
    }

    public FileEntity uploadFile(FileUploadDto fileUploadDTO, String createdUser) throws IOException {
        MultipartFile file = fileUploadDTO.getFile();;
        if(file.isEmpty())
        {
            throw  new RuntimeException("please provide a valide file");
        }

        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(file.getInputStream());
            byte[] b = in.readAllBytes();
            String fullPath = decideFullPath(file);
            out = new BufferedOutputStream(new FileOutputStream(fullPath));
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String filename = StringUtils.cleanPath(fileUploadDTO.getFile().getOriginalFilename());
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(filename);
        fileEntity.setCreatedUser(createdUser);
        fileEntity = fileRepository.save(fileEntity);
        return fileEntity;
    }

    public ResponseEntity<?> downloadFile(Long fileId) {

        var file = fileRepository.findById(fileId).orElse(null);
        if(file == null) {
            return ResponseEntity.badRequest().body("File not exist!");
        }
        String filePath = path + "/" + file.getName();

        Resource resource = null;
        resource = resourceLoader.getResource("file:" + filePath);
        if (!resource.exists()) {
            return ResponseEntity.badRequest().body("File resource exist!");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String decideFullPath(MultipartFile file) {
        String filename = file.getOriginalFilename();
        int index = filename.indexOf('.');
        String extension = filename.substring(index+1).toUpperCase();
        return path + File.separator + File.separator+ filename;
    }
}
