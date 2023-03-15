package com.dado.quanlytailieu.controller;

import com.dado.quanlytailieu.dao.CongTrinhDto;
import com.dado.quanlytailieu.service.CongTrinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/congtrinh")
public class CongTrinhController {

    @Autowired
    CongTrinhService congTrinhService;

    @GetMapping("/all")
    public List<CongTrinhDto> getAllCongTrinh() {
        return congTrinhService.getAllCongTrinh();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCongTrinh(
    ) {
        return null;
    }
}
