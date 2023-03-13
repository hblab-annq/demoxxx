package com.dado.quanlytailieu.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileInfoDto {
    long id;
    String fileName;
    String createdUser;
}
