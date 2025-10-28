package com.example.kukuadmin;

public class Home{
    public String title;
    public String imageUrl;

    public Home() {
        // Default constructor required for calls to DataSnapshot.getValue(Upload.class)
    }

    public Home(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

