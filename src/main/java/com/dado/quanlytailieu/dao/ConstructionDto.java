package com.dado.quanlytailieu.dao;

import com.dado.quanlytailieu.enums.ConstructionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConstructionDto {
    String name;
    String code;
    String location;
    ConstructionType type;
}
