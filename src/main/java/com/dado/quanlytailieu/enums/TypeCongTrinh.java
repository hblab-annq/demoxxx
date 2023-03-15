package com.dado.quanlytailieu.enums;

public enum TypeCongTrinh {

    CONG("Cống"),
    KENH_DAT("Kênh đất"),
    TRAM_BOM("Trạm bơm"),
    KENH_XAY("Kênh xây"),
    KENH_TUOI("Kênh tưới"),
    ;

    private String type;
    TypeCongTrinh(String type) {
        this.type = type;
    }
}
