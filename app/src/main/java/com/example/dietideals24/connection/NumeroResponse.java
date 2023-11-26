package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class NumeroResponse {
    @SerializedName("numero")
    private int numero;

    public int getNumero() {
        return numero;
    }
}
