package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class CreateAstaRequest {

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

    @SerializedName("statoAsta")
    private int statoAsta;

    @SerializedName("data_scadenza")
    private String dataScadenzaTF;

    @SerializedName("prezzo_iniziale")
    private BigDecimal prezzoIniziale;

    @SerializedName("offerta_attuale")
    private BigDecimal offertaAttuale;

    @SerializedName("soglia_segreta")
    private BigDecimal sogliaMinimaSegreta;

    @SerializedName("creatore")
    private String creatore;


    public CreateAstaRequest(String nomeProdotto, String tipologia, String descrizione, String image, String categoria, String paroleChiave, int statoAsta, String dataScadenzaTF, BigDecimal prezzoIniziale, BigDecimal offertaAttuale, BigDecimal sogliaMinimaSegreta, String creatore) {
        this.nomeProdotto = nomeProdotto;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.image = image;
        this.categoria = categoria;
        this.paroleChiave = paroleChiave;
        this.statoAsta = statoAsta;
        this.dataScadenzaTF = dataScadenzaTF;
        this.prezzoIniziale = prezzoIniziale;
        this.offertaAttuale = offertaAttuale;
        this.sogliaMinimaSegreta = sogliaMinimaSegreta;
        this.creatore = creatore;
    }
}
