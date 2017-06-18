package ru.ifmo.se.sofia.pillow;

import java.util.Arrays;
import java.util.Optional;

public enum Response {

    OK((byte) 0),
    LIST((byte) 5),
    OUT_OF_DATE((byte) -1);

    static Optional<Response> withCode(byte code) {
        return Arrays.stream(Response.values()).filter(c -> c.getCode() == code).findFirst();
    }

    private final byte code;

    Response(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
