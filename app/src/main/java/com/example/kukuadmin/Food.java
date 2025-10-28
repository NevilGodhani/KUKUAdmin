package com.example.kukuadmin;

public class Food {

    private String title;
    private double price;
    private String imageUrl;

    public  Food () {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public  Food (String title, double price, String imageUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

