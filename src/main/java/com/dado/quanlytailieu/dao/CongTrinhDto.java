package com.dado.quanlytailieu.dao;

import com.dado.quanlytailieu.enums.TypeCongTrinh;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CongTrinhDto {
    String name;
    String code;
    String location;
    TypeCongTrinh type;
}
