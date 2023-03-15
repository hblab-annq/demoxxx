package com.dado.quanlytailieu.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUpdateCommand {
    private String id;
    private String createdTime;
    private String createdUser;
    private MultipartFile file;
    private int fileId;
}
