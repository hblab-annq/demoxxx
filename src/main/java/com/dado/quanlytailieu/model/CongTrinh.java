package com.dado.quanlytailieu.model;

import com.dado.quanlytailieu.enums.TypeCongTrinh;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CongTrinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String code;
    String location;
    TypeCongTrinh type;

    @OneToMany(mappedBy = "congTrinh", cascade = CascadeType.ALL)
    List<Image> image;

    @OneToMany(mappedBy = "congTrinh", cascade = CascadeType.ALL)
    List<FileEntity> file;
}
