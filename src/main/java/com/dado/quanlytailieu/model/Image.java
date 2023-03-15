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
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fileName;
    String url;

    String type;

    @ManyToOne
    @JoinColumn(name = "construction_id")
    Construction construction;

    String createdUser;

    LocalDateTime createdTime;
}
