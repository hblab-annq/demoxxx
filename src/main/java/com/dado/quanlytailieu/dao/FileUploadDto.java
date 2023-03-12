package com.dado.quanlytailieu.dao;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDto {
    @Nonnull
    private MultipartFile file;
    @Nullable
    private String name;
    private String description;
}
