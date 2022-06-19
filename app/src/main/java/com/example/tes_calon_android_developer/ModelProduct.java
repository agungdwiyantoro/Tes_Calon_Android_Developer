package com.example.tes_calon_android_developer;

public class ModelProduct {
    String id, imageURL, title, description, category,count, price, rate;

    public ModelProduct(String id, String imageURL, String title, String description, String category, String price, String rate,String count) {
        this.id = id;
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.category = category;
        this.count = count;
        this.price = price;
        this.rate = rate;
    }

    public ModelProduct(String imageURL, String title, String description, String category, String price, String rate, String count) {
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.rate = rate;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getCount() {
        return count;
    }

    public String getPrice() {
        return price;
    }

    public String getRate() {
        return rate;
    }
}
