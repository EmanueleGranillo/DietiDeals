package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CreateAstaIngRequest {

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

    @SerializedName("soglia_rialzo")
    private BigDecimal sogliaRialzoMinima;

    @SerializedName("creatore")
    private String creatore;

    @SerializedName("reset_timer")
    private long resetTimer;




    //asta inglese
    public CreateAstaIngRequest(String nomeProdotto, String tipologia, String descrizione, String image, String categoria, String paroleChiave, String dataScadenzaTF, BigDecimal prezzoIniziale, BigDecimal sogliaRialzoMinima, String creatore, long resetTimer) {
        this.nomeProdotto = nomeProdotto;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.image = image;
        this.categoria = categoria;
        this.paroleChiave = paroleChiave;
        this.dataScadenzaTF = dataScadenzaTF;
        this.prezzoIniziale = prezzoIniziale;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.creatore = creatore;
        this.resetTimer = resetTimer;
    }




}
