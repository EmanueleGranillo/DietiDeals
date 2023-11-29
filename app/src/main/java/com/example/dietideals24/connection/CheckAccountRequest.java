package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;
public class CheckAccountRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("tipo_account")
    private String tipo_account;

    public CheckAccountRequest(String email, String tipo_account) {
        this.email = email;
        this.tipo_account = tipo_account;
    }
}
