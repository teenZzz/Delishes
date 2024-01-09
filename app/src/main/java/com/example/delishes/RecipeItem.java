package com.example.delishes;

public class RecipeItem {
    private String text;
    private String imageUrl;
    private String receptText;
    private boolean isLiked;
    private String documentId; // Добавлено новое поле

    public RecipeItem(String text, String imageUrl, String receptText) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.receptText = receptText;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getReceptText() {
        return receptText;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
