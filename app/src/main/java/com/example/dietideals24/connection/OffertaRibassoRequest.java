package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class OffertaRibassoRequest {

    @SerializedName("offertaAttuale")
    private BigDecimal offertaAttuale;

    @SerializedName("id")
    private int id;


    public OffertaRibassoRequest(BigDecimal offertaAttuale, int id) {
        this.offertaAttuale = offertaAttuale;
        this.id = id;
    }


}