package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class OffertaTempoFissoRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("offerta")
    private BigDecimal offerta;

    @SerializedName("nickname")
    private String nickname;

    public OffertaTempoFissoRequest(int id, BigDecimal offerta, String nickname) {
        this.id = id;
        this.offerta= offerta;
        this.nickname = nickname;
    }
}
