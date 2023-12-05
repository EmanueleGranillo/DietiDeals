package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class OffertaIngleseRequest {

    @SerializedName("id")
    private int id;

    @SerializedName("offerta")
    private BigDecimal offerta;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("dateString")
    private String dateString;

    public OffertaIngleseRequest(int id, BigDecimal offerta, String nickname, String dateString) {
        this.id = id;
        this.offerta= offerta;
        this.nickname = nickname;
        this.dateString = dateString;
    }
}


