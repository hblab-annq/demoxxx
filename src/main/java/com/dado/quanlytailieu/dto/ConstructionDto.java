package com.dado.quanlytailieu.dto;

import java.util.List;

import com.dado.quanlytailieu.enums.ConstructionType;
import com.dado.quanlytailieu.model.ConstructionDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionDto {
    String name;
    String code;
    String location;
    ConstructionType type;
    List<Resource> images;
    List<ConstructionDocumentDto> docs;
}
