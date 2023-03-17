package com.dado.quanlytailieu.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dado.quanlytailieu.command.ConstructionCommand;
import com.dado.quanlytailieu.command.ConstructionDocumentCommand;
import com.dado.quanlytailieu.dto.ConstructionDocumentDto;
import com.dado.quanlytailieu.model.Construction;
import com.dado.quanlytailieu.model.ConstructionDocument;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.repository.ConstructionRepository;
import com.dado.quanlytailieu.repository.ContructionDocumentRepository;
import com.dado.quanlytailieu.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConstructionDocumentService {
    @Autowired
    ContructionDocumentRepository contructionDocumentRepository;

    @Autowired
    ConstructionRepository constructionRepository;

    @Autowired
    FileService fileService;

    @Autowired
    FileRepository fileRepository;

    @Value("${extern.resources.path}")
    private String path;

    public ConstructionDocument createConstructionDocs(ConstructionDocumentCommand command) throws Exception {

        MultipartFile file = command.getFile();
        String filename = fileService.saveFile(file);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(filename);
        fileEntity.setCreatedUser(command.getCreatedUser());
        fileEntity.setType(file.getContentType());
        fileRepository.save(fileEntity);

        var construction = constructionRepository.findById(command.getConstructionId()).orElseThrow(()->new Exception("construction not exist"));
        ConstructionDocument document = new ConstructionDocument();
        document.setName(command.getName());
        document.setOwnUser(command.getOwnUser());
        document.setCreatedUser(command.getCreatedUser());
        document.setConstruction(construction);
        document.setFile(fileEntity);
        contructionDocumentRepository.save(document);
        return document;
    }

    public ConstructionDocumentDto getConstructionDocsById(String id) throws MalformedURLException {
        var docs = contructionDocumentRepository.findById(Long.valueOf(id)).orElse(null);
        Path filePath = Paths.get(path, docs.getName());
        Resource resource = new UrlResource(filePath.toUri());
        ConstructionDocumentDto dto = new ConstructionDocumentDto();
        dto.setId(docs.getId());
        dto.setOwnUser(docs.getOwnUser());
        dto.setCreatedTime(docs.getCreatedTime());
        dto.setCreatedUser(docs.getCreatedUser());
        dto.setName(docs.getName());
        dto.setFile(resource);

        return dto;
    }

    public List<ConstructionDocumentDto> getConstructionDocsByConstructionId(String id) throws MalformedURLException {
        var docs = contructionDocumentRepository.getConstructionDocsByConstructionId(id);
        List<ConstructionDocumentDto> dtos = new ArrayList<>();
        for (ConstructionDocument document: docs) {
            Path filePath = Paths.get(path, document.getName());
            Resource resource = new UrlResource(filePath.toUri());
            ConstructionDocumentDto dto = new ConstructionDocumentDto();
            dto.setId(document.getId());
            dto.setOwnUser(document.getOwnUser());
            dto.setCreatedTime(document.getCreatedTime());
            dto.setCreatedUser(document.getCreatedUser());
            dto.setName(document.getName());
            dto.setFile(resource);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ConstructionDocumentDto> getAllDocs() throws MalformedURLException {
        var list = contructionDocumentRepository.findAll();
        List<ConstructionDocumentDto> documentDtos = new ArrayList<>();
        for (ConstructionDocument document:list) {
            Path filePath = Paths.get(path, document.getName());
            Resource resource = new UrlResource(filePath.toUri());
            ConstructionDocumentDto dto = new ConstructionDocumentDto();
            dto.setId(document.getId());
            dto.setOwnUser(document.getOwnUser());
            dto.setCreatedTime(document.getCreatedTime());
            dto.setCreatedUser(document.getCreatedUser());
            dto.setName(document.getName());
            dto.setFile(resource);
            documentDtos.add(dto);
        }
        return documentDtos;
    }
}
