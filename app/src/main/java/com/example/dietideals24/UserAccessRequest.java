package com.example.dietideals24;
import com.google.gson.annotations.SerializedName;

public class UserAccessRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("tipo_account")
    private String tipo_account;

    public UserAccessRequest(String email, String password, String tipo_account) {
        this.email = email;
        this.password = password;
        this.tipo_account = tipo_account;
    }

}
