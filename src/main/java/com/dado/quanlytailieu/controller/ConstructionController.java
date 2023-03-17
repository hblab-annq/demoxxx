package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.command.ConstructionCommand;
import com.dado.quanlytailieu.dto.ConstructionDto;
import com.dado.quanlytailieu.service.ConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/construction")
public class ConstructionController {

    @Autowired
    ConstructionService constructionService;

//    @GetMapping("/all")
//    public List<ConstructionDto> getAllConstruction() {
//        return constructionService.getAllConstruction();
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createConstruction(@ModelAttribute ConstructionCommand command
    ) throws Exception {
        return ResponseEntity.ok().body(constructionService.createConstruction(command));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllConstruction() throws MalformedURLException {
        return ResponseEntity.ok().body(constructionService.getAllContruction());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConstructionById(@RequestParam(name = "id") String id) throws MalformedURLException {
        var cons = constructionService.findConstructionById(id);
        if(Objects.isNull(cons)){
            return ResponseEntity.badRequest().body("Construction is not exist!");
        }
        return ResponseEntity.ok().body(cons);
    }
}
