package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class UserRegistrationRequest {

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("tipo_account")
    private String tipo_account;


    public UserRegistrationRequest(String nickname, String email, String password, String tipo_account) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.tipo_account = tipo_account;
    }
}
