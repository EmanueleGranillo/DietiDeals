package com.example.dietideals24.models;

public class Notifica {

    String intestazione;
    String descrizione;
    boolean letta;


    public Notifica(String intestazione, String descrizione, boolean letta) {
        this.intestazione = intestazione;
        this.descrizione = descrizione;
        this.letta = letta;
    }


    public String getIntestazione() {
        return intestazione;
    }

    public void setIntestazione(String intestazione) {
        this.intestazione = intestazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isLetta() {
        return letta;
    }

    public void setLetta(boolean letta) {
        this.letta = letta;
    }

}
