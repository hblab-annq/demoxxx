package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String fileName;

    String type;

    @ManyToOne
    @JoinColumn(name = "ho_so_cong_trinh_id")
    ConstructionDocument constructionDocument;

    String createdUser;

    LocalDateTime createdTime;

}
