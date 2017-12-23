package com.chandilsachin.simpragmaassignment.fragments.racipeList;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePuppy {

    @SerializedName("title")
    private String title;

    @SerializedName("version")
    private String version;

    @SerializedName("href")
    private String href;

    @SerializedName("results")
    private List<Recipe> results;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
