package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class UserModifiedRequest {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("nome")
    private String nome;

    @SerializedName("cognome")
    private String cognome;

    @SerializedName("biografia")
    private String biografia;

    @SerializedName("link_web")
    private String link_web;

    @SerializedName("link_insta")
    private String link_insta;

    @SerializedName("posizione")
    private String posizione;

    @SerializedName("numero_telefono")
    private String numero_telefono;
    @SerializedName("foto_profilo")
    private String foto_profilo;


    public UserModifiedRequest(String nickname, String nome, String cognome, String biografia, String link_web, String link_insta, String posizione, String numero_telefono, String foto_profilo) {
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
        this.biografia = biografia;
        this.link_web = link_web;
        this.link_insta = link_insta;
        this.posizione = posizione;
        this.numero_telefono = numero_telefono;
        this.foto_profilo = foto_profilo;
    }
}
