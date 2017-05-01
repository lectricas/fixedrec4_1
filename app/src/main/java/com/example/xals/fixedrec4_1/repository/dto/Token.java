package com.example.xals.fixedrec4_1.repository.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Token {

    public static final String TOKEN_EMPTY = "";
    private static final String TOKEN_SUPPLY = "Token ";

    String auth_token;

    public static String configure(String token) {
        return TOKEN_SUPPLY + token;
    }
}
