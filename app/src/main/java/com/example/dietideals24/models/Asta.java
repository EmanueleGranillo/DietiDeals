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


    public void presentaOffertaTempoFisso(BigDecimal importo) {
        if (importo.compareTo(sogliaSegreta) > 0) {
            System.out.println("Oggetto venduto con successo ad acquirente x");
        }
        if ((importo.compareTo(offertaAttuale)) > 0 && (importo.compareTo(sogliaSegreta) < 0)) {
            System.out.println("Offerta presentata con successo da x");
            this.offertaAttuale = importo;
        } else {
            System.out.println("Impossibile presentare l'offerta.");
        }
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


        // Inizializza e avvia il timer con l'intervallo specificato
        //this.timer = new Timer();
        //this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, intervalloTimer);
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


        // Inizializza e avvia il timer con l'intervallo specificato
        //this.timer = new Timer();
        //this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, resetTimer);
    }


    // Metodo per gestire l'offerta effettuata (asta all'inglese)
    public void effettuaOffertaIng(BigDecimal importo) {
        if (importo.subtract(offertaAttuale).compareTo(sogliaRialzoMinima) > 0) {
            this.offertaAttuale = importo;
            //this.timer.cancel(); // Annulla il timer corrente

            //Avvio di nuovo timer di un'ora
            //this.timer = new Timer();
            //this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, resetTimer);
        }
    }

    // Classe interna per rappresentare l'attività del timer
    private class AstaTimerTask extends TimerTask {
        @Override
        public void run() {

            if (tipologia.equals("Asta all'inglese")) {
                // Logica da eseguire quando il timer scade
                System.out.println("Timer scaduto per l'asta: " + nomeProdotto);

                // Considera l'asta come fallita se nessuna offerta è stata presentata
                if (offertaAttuale.equals(prezzoIniziale)) {
                    System.out.println("Asta fallita. Nessuna offerta presentata.");
                    //invio notifiche ecc
                } else {
                    System.out.println("L'asta è stata vinta da chi ha offerto: " + offertaAttuale);
                    //invio notifiche ecc
                }
                // Annulla il timer quando arriva a zero
                //timer.cancel();
            }


            if (tipologia.equals("Asta al ribasso")) {
                decrementaPrezzo();
            }

        }
    }


    // Metodo per decrementare il prezzo (asta al ribasso)
    private void decrementaPrezzo() {
        this.offertaAttuale = offertaAttuale.subtract(importoDecremento);

        if (this.offertaAttuale.compareTo(this.sogliaSegreta) < 0) {
            System.out.println("Asta fallita. Nessuna offerta presentata.");
        }
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


        // Inizializza e avvia il timer con l'intervallo specificato
        //this.timer = new Timer();
        //AstaTimerTask astaTimerTask = new AstaTimerTask();
        //this.timer.scheduleAtFixedRate(astaTimerTask, 0, intervalloTimer);

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
