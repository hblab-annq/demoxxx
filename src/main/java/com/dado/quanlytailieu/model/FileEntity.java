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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @OneToMany(mappedBy = "fileEntity", cascade = CascadeType.ALL)
    private List<Image> imageList;

    private String createdUser;

    private LocalDateTime createdTime;
}
