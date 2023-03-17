package com.dado.quanlytailieu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    Long id;
    String fileName;
    String type;
    String constructionId;
    String createdUser;
    LocalDateTime createdTime;
}
