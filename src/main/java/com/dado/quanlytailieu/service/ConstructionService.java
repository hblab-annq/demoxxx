package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.ConstructionDto;
import com.dado.quanlytailieu.repository.ConstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructionService {

    @Autowired
    ConstructionRepository constructionRepository;

    public List<ConstructionDto> getAllConstruction() {
        var list = constructionRepository.findAll();
        return list.stream().map(
                entity -> ConstructionDto.builder()
                        .name(entity.getName())
                        .code(entity.getCode())
                        .location(entity.getLocation())
                        .type(entity.getType())
                        .build()
        ).collect(Collectors.toList());
    }

}
