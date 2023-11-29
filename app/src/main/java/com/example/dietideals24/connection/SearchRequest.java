package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class SearchRequest {
    @SerializedName("ricerca")
    private String ricerca;

    public SearchRequest(String ricerca) {
        this.ricerca = ricerca;
    }
}
