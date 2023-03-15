package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.ConstructionDto;
import com.dado.quanlytailieu.service.ConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/congtrinh")
public class ConstructionController {

    @Autowired
    ConstructionService constructionService;

    @GetMapping("/all")
    public List<ConstructionDto> getAllConstruction() {
        return constructionService.getAllConstruction();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createConstruction(
    ) {
        return null;
    }
}
