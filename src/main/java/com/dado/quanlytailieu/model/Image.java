package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @JsonIgnore
    @ToString.Exclude
    FileEntity fileEntity;

    String createdUser;

    LocalDateTime createdTime;
}
