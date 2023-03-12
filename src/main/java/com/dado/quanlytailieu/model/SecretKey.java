package com.dado.quanlytailieu.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecretKey {
    @Value("${secret.key}")
    private String key;


    @Override
    public String toString() {
        return key;
    }
}
