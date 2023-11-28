package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;
public class NicknameRequest {

    @SerializedName("nickname")
    private String nickname;


    public NicknameRequest(String nickname) {
        this.nickname = nickname;
    }
}
