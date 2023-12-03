package com.example.delishes;

public class RecipeItem {
    private String text;
    private String imageUrl;

    public RecipeItem(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
