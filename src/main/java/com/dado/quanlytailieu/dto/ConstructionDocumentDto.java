package com.dado.quanlytailieu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.Resource;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionDocumentDto {
    private Long id;
    private String name;
    private String ownUser;
    private String createdUser;
    private LocalDateTime createdTime;
    private Resource file;
}
