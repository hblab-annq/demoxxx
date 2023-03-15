package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    HoSoCongTrinh hoSoCongTrinh;

    String createdUser;

    LocalDateTime createdTime;

}
