package com.example.dietideals24.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

public class Asta implements Serializable {

    private int id;
    private String nomeProdotto; //a tutte e tre
    private String tipologia; //a tutte e tre
    private String descrizione; //a tutte e tre
    private String dataScadenzaTF; //a tempo fisso
    private BigDecimal offertaAttuale; //tutte e tre
    private BigDecimal sogliaSegreta; //a tempo fisso + ribasso
    private BigDecimal prezzoIniziale; //tutte e tre
    private BigDecimal sogliaRialzoMinima; //inglese
    private long resetTimer; //inglese + ribasso
    private BigDecimal importoDecremento; //asta al ribasso
    private String fotoProdotto;
    private String categoria;
    private String paroleChiave;
    private String creatore;
    private String vincente;


    //Costruttore quando tipologia asta = tempo fisso
    public Asta(int id, String titoloAsta, String tipologia, String descrizione, String dataFineAstaTempoFisso, BigDecimal offertaAttuale, BigDecimal sogliaMinimaSegreta) {
        this.id = id;
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.dataScadenzaTF = dataFineAstaTempoFisso;
        this.offertaAttuale = offertaAttuale;
        this.sogliaSegreta = sogliaMinimaSegreta;
    }


    //Costruttore quando tipologia asta = tempo fisso e image presente
    public Asta(int id, String titoloAsta, String tipologia, String descrizione, String image, String dataFineAstaTempoFisso, BigDecimal offertaAttuale, BigDecimal sogliaMinimaSegreta) {
        this.id = id;
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.fotoProdotto = image;
        this.dataScadenzaTF = dataFineAstaTempoFisso;
        this.offertaAttuale = offertaAttuale;
        this.sogliaSegreta = sogliaMinimaSegreta;
    }

    public Asta(int id, String nomeProdotto, String tipologia, String descrizione, String dataScadenzaTF, BigDecimal offertaAttuale, BigDecimal sogliaSegreta, BigDecimal prezzoIniziale, BigDecimal sogliaRialzoMinima, long resetTimer, BigDecimal importoDecremento, boolean statoAsta, String fotoProdotto, String categoria, String paroleChiave, String creatore, String vincente) {
        this.id = id;
        this.nomeProdotto = nomeProdotto;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.dataScadenzaTF = dataScadenzaTF;
        this.offertaAttuale = offertaAttuale;
        this.sogliaSegreta = sogliaSegreta;
        this.prezzoIniziale = prezzoIniziale;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.resetTimer = resetTimer;
        this.importoDecremento = importoDecremento;
        this.fotoProdotto = fotoProdotto;
        this.categoria = categoria;
        this.paroleChiave = paroleChiave;
        this.creatore = creatore;
        this.vincente = vincente;
    }



    // Costruttore per asta all'inglese CON intervalloTimer specificato
    public Asta(int id, String titoloAsta, String tipologia, String descrizione, BigDecimal baseAsta, BigDecimal sogliaRialzoMinima, long intervalloTimer) {
        this.id = id;
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.prezzoIniziale = baseAsta;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.resetTimer = intervalloTimer;
    }


    // Costruttore per asta all'inglese SENZA specificare l'intervallo del timer
    public Asta(int id, String titoloAsta, String tipologia, String descrizione, BigDecimal baseAsta, BigDecimal sogliaRialzoMinima) {
        this.id = id;
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.resetTimer = 3600 * 1000;
    }





    //costruttore per asta al ribasso
    public Asta(int id, String titoloAsta, String tipologia, String descrizione, BigDecimal prezzoIniziale, long intervalloTimer, BigDecimal importoDecremento, BigDecimal prezzoMinimoSegreto) {
        this.id = id;
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.descrizione = descrizione;
        this.offertaAttuale = prezzoIniziale;
        this.resetTimer = intervalloTimer;
        this.importoDecremento = importoDecremento;
        this.sogliaSegreta = prezzoMinimoSegreto;
    }




    public String getVincente() {
        return vincente;
    }

    public void setVincente(String vincente) {
        this.vincente = vincente;
    }
    public String getCreatore() {
        return creatore;
    }

    public void setCreatore(String creatore) {
        this.creatore = creatore;
    }
    public String getParoleChiave() {
        return paroleChiave;
    }

    public void setParoleChiave(String paroleChiave) {
        this.paroleChiave = paroleChiave;
    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getFotoProdotto() {
        return fotoProdotto;
    }

    public void setFotoProdotto(String fotoProdotto) {
        this.fotoProdotto = fotoProdotto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setDataScadenzaTF(String dataScadenzaTF) {
        this.dataScadenzaTF = dataScadenzaTF;
    }

    public void setOffertaAttuale(BigDecimal offertaAttuale) {
        this.offertaAttuale = offertaAttuale;
    }

    public void setPrezzoIniziale(BigDecimal prezzoIniziale) {
        this.prezzoIniziale = prezzoIniziale;
    }

   // public void setTimer(Timer timer) {
    //    this.timer = timer;
    //}

    public void setSogliaRialzoMinima(BigDecimal sogliaRialzoMinima) {
        this.sogliaRialzoMinima = sogliaRialzoMinima;
    }

    public void setResetTimer(long resetTimer) {
        this.resetTimer = resetTimer;
    }


    public void setImportoDecremento(BigDecimal importoDecremento) {
        this.importoDecremento = importoDecremento;
    }

    public void setSogliaSegreta(BigDecimal sogliaSegreta) {
        this.sogliaSegreta = sogliaSegreta;
    }

    public int getId() {
        return id;
    }
    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getDataScadenzaTF() {
        return dataScadenzaTF;
    }

    public BigDecimal getPrezzoIniziale() {
        return prezzoIniziale;
    }

  //  public Timer getTimer() {
       // return timer;
    //}

    public BigDecimal getSogliaRialzoMinima() {
        return sogliaRialzoMinima;
    }

    public long getResetTimer() {
        return resetTimer;
    }

    public BigDecimal getOffertaAttuale() {
        return offertaAttuale;
    }

    public BigDecimal getImportoDecremento() {
        return importoDecremento;
    }

    public BigDecimal getSogliaSegreta() {
        return sogliaSegreta;
    }


}
