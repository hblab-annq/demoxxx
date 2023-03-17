package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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

    @JsonIgnore
    @OneToOne(mappedBy = "file")
    ConstructionDocument constructionDocument;

    String createdUser;

    @CreatedDate
    LocalDateTime createdTime = LocalDateTime.now();

}
