package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "filename", nullable = true)
    String fileName;
    String type;

    @ManyToOne
    @JoinColumn(name = "file_id")
    FileEntity fileEntity;

    String createdUser;

    LocalDateTime createdTime;
}
