package ru.ifmo.se.sofia.pillow;

import java.util.Arrays;
import java.util.Optional;

public enum Request {

    ADD((byte) 1),
    DELETE((byte) 2),
    REMOVE_LOWER((byte) 3),
    CLEAR((byte) 4),
    LIST((byte) 5),
    UPDATE((byte) 6);

    static Optional<Request> withCode(byte code) {
        return Arrays.stream(Request.values()).filter(c -> c.getCode() == code).findFirst();
    }

    private final byte code;

    Request(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
