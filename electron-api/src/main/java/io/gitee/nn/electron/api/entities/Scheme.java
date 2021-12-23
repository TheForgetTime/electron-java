package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Scheme {
    BASIC("basic"),
    DIGEST("digest"),
    NTLM("ntlm"),
    NEGOTIATE("negotiate");

    private final String value;

    Scheme(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
