package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String url;

    @ManyToOne
    @JoinColumn(name = "file_id")
    FileEntity fileEntity;

    String createdUser;

    LocalDateTime createdTime;
}
