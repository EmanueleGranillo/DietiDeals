package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class CategoriaRequest {

    @SerializedName("nomecategoria")
    private String nomecategoria;


    public CategoriaRequest(String categoria) {
        this.nomecategoria = categoria;
    }
}