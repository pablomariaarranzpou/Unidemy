package com.example.unidemy.Model;

public class Curso {

    private String title;
    private String description;
    private int rating;
    private int views;
    private float price;

    public Curso(String title, String description, float price) {
        this.title = title;
        this.description = description;
        this.rating = 0;
        this.views = 0;
        this.price = price;
    }
}
