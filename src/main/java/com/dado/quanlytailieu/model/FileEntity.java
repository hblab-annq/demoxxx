package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String url;

    @OneToMany(mappedBy = "fileEntity", cascade = CascadeType.ALL)
    List<Image> imageList;

    String createdUser;

    LocalDateTime createdTime;

    public FileEntity(String name) {

    }
}
