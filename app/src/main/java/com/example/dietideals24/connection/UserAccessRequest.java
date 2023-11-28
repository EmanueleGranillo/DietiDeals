package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class UserAccessRequest {

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("password")
    private String password;

    @SerializedName("tipo_account")
    private String tipo_account;

    public UserAccessRequest(String nickname, String password, String tipo_account) {
        this.nickname = nickname;
        this.password = password;
        this.tipo_account = tipo_account;
    }

}
