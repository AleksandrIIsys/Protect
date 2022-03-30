package com.example.protect;

import java.util.ArrayList;

public class Lesson {
    String name;
    Integer page;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Lesson(String name, Integer page) {
        this.name = name;
        this.page = page;
    }
}
