package ru.ifmo.se.sofia.pillow;

import java.io.Serializable;
import java.time.ZonedDateTime;

class Ghost implements Comparable<Ghost>, Serializable {

    private final String name;
    private final ZonedDateTime birth;
    private final int height;

    Ghost(String name, ZonedDateTime birth, int height) {
        this.name = name;
        this.birth = birth;
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public ZonedDateTime getBirth() {
        return this.birth;
    }

    @Override
    public int compareTo(Ghost g) {
        if(this.getBirth().isAfter(g.getBirth())) {
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object g) {
        return this.getName().equals(((Ghost) g).getName());
    }
}