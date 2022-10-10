package com.example.daun.ioc;

public abstract class Ingredient {

    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
