package com.dado.quanlytailieu.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class HoSoCongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String ownUser;

    String createdUser;

    @CreatedDate
    LocalDateTime createdTime;

    @UpdateTimestamp
    LocalDateTime updatedTime;

    @ManyToOne
    @JoinColumn(name = "cong_trinh_id")
    CongTrinh congTrinh;

    @OneToOne
    @JoinColumn(name = "file_id")
    FileEntity file;
}
