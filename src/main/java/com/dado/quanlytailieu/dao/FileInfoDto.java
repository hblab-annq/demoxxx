package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FileInfoDto {
    long id;
    String fileName;
    String createdUser;
}
