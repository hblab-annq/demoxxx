package com.dado.quanlytailieu.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionDocumentCommand {
    Long constructionId;
    String name;
    String ownUser;
    String createdUser;
    MultipartFile file;
}
