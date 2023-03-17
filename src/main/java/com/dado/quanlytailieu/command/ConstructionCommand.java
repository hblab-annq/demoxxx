package com.dado.quanlytailieu.command;

import java.util.List;

import com.dado.quanlytailieu.enums.ConstructionType;
import com.dado.quanlytailieu.model.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ConstructionCommand {
    private String name;
    private String code;
    private String location;
    private String type;
    private MultipartFile[] images;
}
