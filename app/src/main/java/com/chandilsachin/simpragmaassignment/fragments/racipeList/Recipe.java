package com.chandilsachin.simpragmaassignment.fragments.racipeList;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("title")
    private String title;

    @SerializedName("href")
    private String href;

    @SerializedName("ingredients")
    private String ingredients;

    @SerializedName("thumbnail")
    private String thumbnail;

    public Recipe(String title, String href, String ingredients, String thumbnail) {
        this.title = title;
        this.href = href;
        this.ingredients = ingredients;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
