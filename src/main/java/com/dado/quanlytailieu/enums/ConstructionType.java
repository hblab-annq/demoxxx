package com.dado.quanlytailieu.enums;

public enum ConstructionType {

    CONG("Cống"),
    KENH_DAT("Kênh đất"),
    TRAM_BOM("Trạm bơm"),
    KENH_XAY("Kênh xây"),
    KENH_TUOI("Kênh tưới"),
    ;

    private String type;
    ConstructionType(String type) {
        this.type = type;
    }
}
