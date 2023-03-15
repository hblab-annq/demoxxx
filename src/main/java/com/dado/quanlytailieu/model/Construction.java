package com.dado.quanlytailieu.model;

import com.dado.quanlytailieu.enums.ConstructionType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Construction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String code;
    String location;
    ConstructionType type;

    @OneToMany(mappedBy = "construction")
    List<Image> images;

    @OneToMany(mappedBy = "construction")
    List<ConstructionDocument> constructionDocuments;
}
