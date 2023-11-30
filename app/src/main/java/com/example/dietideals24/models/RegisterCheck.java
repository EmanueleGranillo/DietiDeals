package com.example.dietideals24.models;
import com.google.gson.annotations.SerializedName;

public class RegisterCheck {
    @SerializedName("nickname")
    boolean nickname;
    @SerializedName("account")
    boolean account;

    public boolean isNicknameAvailable() {
        return nickname;
    }

    public boolean isAccountAvailable() {
        return account;
    }

}
