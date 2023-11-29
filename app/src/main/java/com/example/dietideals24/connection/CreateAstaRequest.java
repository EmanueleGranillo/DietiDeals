package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class CreateAstaRequest {

    @SerializedName("nomeProdotto")
    private String nome_prodotto;

    @SerializedName("tipologia")
    private String tipologia;

    @SerializedName("descrizione")
    private String descrizione;

    @SerializedName("image")
    private String image;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("paroleChiave")
    private String keywords;

    @SerializedName("dataScadenzaTF")
    private String data_scadenza;

    @SerializedName("prezzoIniziale")
    private String prezzo_iniziale;

    @SerializedName("offertaAttuale")
    private String offerta_attuale;

    @SerializedName("sogliaMinimaSegreta")
    private String soglia_segreta;

    @SerializedName("creatore")
    private String creatore;


    public CreateAstaRequest(String nome_prodotto, String tipologia, String descrizione, String tipo_account) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.tipo_account = tipo_account;
    }
}
