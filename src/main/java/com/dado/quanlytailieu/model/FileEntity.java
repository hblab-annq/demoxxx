package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @OneToMany(mappedBy = "fileEntity")
    @JsonIgnore
    private List<Image> imageList;

    private String createdUser;

    private LocalDateTime createdTime;
}
