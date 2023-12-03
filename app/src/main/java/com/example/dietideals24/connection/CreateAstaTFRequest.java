package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CreateAstaTFRequest {

    @SerializedName("nomeProdotto")
    private String nomeProdotto;

    @SerializedName("tipologia")
    private String tipologia;

    @SerializedName("descrizione")
    private String descrizione;

    @SerializedName("image")
    private String image;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("keywords")
    private String paroleChiave;

    @SerializedName("data_scadenza")
    private String dataScadenzaTF;

    @SerializedName("prezzo_iniziale")
    private BigDecimal prezzoIniziale;

    @SerializedName("soglia_segreta")
    private BigDecimal sogliaMinimaSegreta;

    @SerializedName("creatore")
    private String creatore;


    public CreateAstaTFRequest(String nomeProdotto, String tipologia, String descrizione, String image, String categoria, String paroleChiave, String dataScadenzaTF, BigDecimal prezzoIniziale, BigDecimal sogliaMinimaSegreta, String creatore) {
        this.nomeProdotto = nomeProdotto;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.image = image;
        this.categoria = categoria;
        this.paroleChiave = paroleChiave;
        this.dataScadenzaTF = dataScadenzaTF;
        this.prezzoIniziale = prezzoIniziale;
        this.sogliaMinimaSegreta = sogliaMinimaSegreta;
        this.creatore = creatore;
    }


}
