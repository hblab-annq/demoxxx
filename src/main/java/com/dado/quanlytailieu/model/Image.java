package com.dado.quanlytailieu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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

    String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "construction_id")
    Construction construction;

    String createdUser;

    @CreatedDate
    LocalDateTime createdTime = LocalDateTime.now();
}
