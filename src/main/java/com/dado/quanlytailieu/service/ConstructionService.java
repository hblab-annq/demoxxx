package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.command.ConstructionCommand;
import com.dado.quanlytailieu.dto.ConstructionDto;
import com.dado.quanlytailieu.enums.ConstructionType;
import com.dado.quanlytailieu.model.Construction;
import com.dado.quanlytailieu.model.Image;
import com.dado.quanlytailieu.repository.ConstructionRepository;
import com.dado.quanlytailieu.repository.ContructionDocumentRepository;
import com.dado.quanlytailieu.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConstructionService {

    @Autowired
    ConstructionRepository constructionRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ContructionDocumentRepository contructionDocumentRepository;

    @Autowired
    ConstructionDocumentService constructionDocumentService;

    public Construction createConstruction(ConstructionCommand command) throws Exception {
        Construction construction = new Construction();
        construction.setName(command.getName());
        construction.setCode(command.getCode());
        construction.setLocation(command.getLocation());
        construction.setType(ConstructionType.valueOf(command.getType()));
        var cons = constructionRepository.save(construction);
        imageService.storeImageForConstruction(command.getImages(),cons);
        return cons;
    }


    public ConstructionDto findConstructionById(String id) throws MalformedURLException {
        var construction = constructionRepository.findById(Long.valueOf(id)).orElse(null);
        var docs = constructionDocumentService.getConstructionDocsByConstructionId(id);
        if(Objects.isNull(construction)){
            return null;
        }
        ConstructionDto dto = new ConstructionDto();
        dto.setName(construction.getName());
        dto.setCode(construction.getCode());
        dto.setLocation(construction.getLocation());
        dto.setType(construction.getType());
        dto.setDocs(docs);
        dto.setImages(imageService.getImagePath(id));
        return dto;
    }

    public List<ConstructionDto> getAllContruction() throws MalformedURLException {
        var constructions = constructionRepository.findAll();
        List<ConstructionDto> constructionDtos = new ArrayList<>();
        for (Construction construction:constructions) {
            var docs = constructionDocumentService
                    .getConstructionDocsByConstructionId(String.valueOf(construction.getId()));
            ConstructionDto constructionDto = new ConstructionDto();
            constructionDto.setLocation(construction.getLocation());
            constructionDto.setCode(construction.getCode());
            constructionDto.setName(construction.getName());
            constructionDto.setType(construction.getType());
            constructionDto.setImages(imageService.getImagePath(String.valueOf(construction.getId())));
            constructionDto.setDocs(docs);
            constructionDtos.add(constructionDto);
        }
        return constructionDtos;
    }
}
