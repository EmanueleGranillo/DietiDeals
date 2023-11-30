package com.example.dietideals24.models;

public class Profilo {

    private String nome;
    private String cognome;
    private String biografia;
    private String email;
    private String link_web;
    private String link_insta;
    private String posizione;
    private String numero_telefono;
    private String foto_profilo;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome){
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setLinkWeb(String link_web){
        this.link_web = link_web;
    }

    public void setLinkInsta(String link_insta) {
        this.link_insta = link_insta;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void setNumeroTelefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }
    public String getFotoProfilo() {
        return foto_profilo;
    }

    public void setFoto_profilo(String foto_profilo) {
        this.foto_profilo = foto_profilo;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() { return email;}

    public String getBiografia() {
        return biografia;
    }

    public String getLinkWeb() {
        return link_web;
    }

    public String getLinkInsta() {
        return link_insta;
    }

    public String getPosizione() {
        return posizione;
    }

    public String getNumeroTelefono(){
        return numero_telefono;
    }





}
