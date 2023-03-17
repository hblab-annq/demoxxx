package com.dado.quanlytailieu.controller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import com.dado.quanlytailieu.command.ConstructionDocumentCommand;
import com.dado.quanlytailieu.command.ImageUploadCommand;
import com.dado.quanlytailieu.dto.ConstructionDocumentDto;
import com.dado.quanlytailieu.model.ConstructionDocument;
import com.dado.quanlytailieu.model.FileEntity;
import com.dado.quanlytailieu.service.ConstructionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class ConstructionDocumentController {
    @Autowired
    ConstructionDocumentService constructionDocumentService;

    @PostMapping("/create")
    public ResponseEntity<ConstructionDocument> createDocument(@ModelAttribute ConstructionDocumentCommand command) throws Exception {
        return ResponseEntity.ok()
                .body(constructionDocumentService.createConstructionDocs(command));
    }

    @GetMapping("{id}")
    public ResponseEntity<ConstructionDocumentDto> getDocsById(@RequestParam(name = "id") String id) throws MalformedURLException {
        var docs = constructionDocumentService.getConstructionDocsById(id);
        if(Objects.isNull(docs)){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok()
                .body(docs);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ConstructionDocumentDto>>getAll() throws MalformedURLException {
        return ResponseEntity.ok()
                .body(constructionDocumentService.getAllDocs());
    }
}
