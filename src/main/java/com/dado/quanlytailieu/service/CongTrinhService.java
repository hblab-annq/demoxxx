package com.dado.quanlytailieu.service;

import com.dado.quanlytailieu.dao.CongTrinhDto;
import com.dado.quanlytailieu.repository.CongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CongTrinhService {

    @Autowired
    CongTrinhRepository congTrinhRepository;

    public List<CongTrinhDto> getAllCongTrinh() {
        var list = congTrinhRepository.findAll();
        return list.stream().map(
                entity -> CongTrinhDto.builder()
                        .name(entity.getName())
                        .code(entity.getCode())
                        .location(entity.getLocation())
                        .type(entity.getType())
                        .build()
        ).collect(Collectors.toList());
    }

}
