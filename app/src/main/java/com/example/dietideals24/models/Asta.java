package com.example.dietideals24.models;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

public class Asta implements Serializable {

    private String nomeProdotto; //a tutte e tre
    private String tipologia; //a tutte e tre
    private Date dataScadenzaTF; //a tempo fisso
    private BigDecimal offertaAttuale; //tutte e tre
    private BigDecimal sogliaMinimaSegreta; //a tempo fisso + ribasso
    private BigDecimal prezzoIniziale; //tutte e tre
    private Timer timer; //inglese+ribasso
    private BigDecimal sogliaRialzoMinima; //inglese
    private long resetTimer; //inglese + ribasso
    private BigDecimal importoDecremento; //asta al ribasso


    //Costruttore quando tipologia asta = tempo fisso
    public Asta(String titoloAsta, String tipologia, Date dataFineAstaTempoFisso, BigDecimal offertaAttuale, BigDecimal sogliaMinimaSegreta) {
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.dataScadenzaTF = dataFineAstaTempoFisso;
        this.offertaAttuale = offertaAttuale;
        this.sogliaMinimaSegreta = sogliaMinimaSegreta;
    }


    public void presentaOffertaTempoFisso(BigDecimal importo) {
        if (importo.compareTo(sogliaMinimaSegreta) > 0) {
            System.out.println("Oggetto venduto con successo ad acquirente x");
        }
        if ((importo.compareTo(offertaAttuale)) > 0 && (importo.compareTo(sogliaMinimaSegreta) < 0)) {
            System.out.println("Offerta presentata con successo da x");
            this.offertaAttuale = importo;
        } else {
            System.out.println("Impossibile presentare l'offerta.");
        }
    }


    // Costruttore per asta all'inglese con intervalloTimer specificato
    public Asta(String titoloAsta, String tipologia, BigDecimal baseAsta, BigDecimal sogliaRialzoMinima, long intervalloTimer) {
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.prezzoIniziale = baseAsta;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.resetTimer = intervalloTimer;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, intervalloTimer);
    }


    // Costruttore per asta all'inglese SENZA specificare l'intervallo del timer
    public Asta(String titoloAsta, String tipologia, BigDecimal baseAsta, BigDecimal sogliaRialzoMinima) {
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.resetTimer = 3600 * 1000;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, resetTimer);
    }


    // Metodo per gestire l'offerta effettuata (asta all'inglese)
    public void effettuaOffertaIng(BigDecimal importo) {
        if (importo.subtract(offertaAttuale).compareTo(sogliaRialzoMinima) > 0) {
            this.offertaAttuale = importo;
            this.timer.cancel(); // Annulla il timer corrente

            //Avvio di nuovo timer di un'ora
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, resetTimer);
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
                timer.cancel();
            }


            if (tipologia.equals("Asta al ribasso")) {
                decrementaPrezzo();
            }

        }
    }


    // Metodo per decrementare il prezzo (asta al ribasso)
    private void decrementaPrezzo() {
        this.offertaAttuale = offertaAttuale.subtract(importoDecremento);

        if (this.offertaAttuale.compareTo(this.sogliaMinimaSegreta) < 0) {
            System.out.println("Asta fallita. Nessuna offerta presentata.");
        }
    }


    //costruttore per asta al ribasso
    public Asta(String titoloAsta, String tipologia, BigDecimal prezzoIniziale, long intervalloTimer, BigDecimal importoDecremento, BigDecimal prezzoMinimoSegreto) {
        this.nomeProdotto = titoloAsta;
        this.tipologia = tipologia;
        this.offertaAttuale = prezzoIniziale;
        this.resetTimer = intervalloTimer;
        this.sogliaMinimaSegreta = prezzoMinimoSegreto;
        this.importoDecremento = importoDecremento;
        this.sogliaMinimaSegreta = prezzoMinimoSegreto;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        AstaTimerTask astaTimerTask = new AstaTimerTask();
        this.timer.scheduleAtFixedRate(astaTimerTask, 0, intervalloTimer);

    }


    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setDataScadenzaTF(Date dataScadenzaTF) {
        this.dataScadenzaTF = dataScadenzaTF;
    }

    public void setOffertaAttuale(BigDecimal offertaAttuale) {
        this.offertaAttuale = offertaAttuale;
    }

    public void setPrezzoIniziale(BigDecimal prezzoIniziale) {
        this.prezzoIniziale = prezzoIniziale;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setSogliaRialzoMinima(BigDecimal sogliaRialzoMinima) {
        this.sogliaRialzoMinima = sogliaRialzoMinima;
    }

    public void setResetTimer(long resetTimer) {
        this.resetTimer = resetTimer;
    }


    public void setImportoDecremento(BigDecimal importoDecremento) {
        this.importoDecremento = importoDecremento;
    }

    public void setSogliaMinimaSegreta(BigDecimal sogliaMinimaSegreta) {
        this.sogliaMinimaSegreta = sogliaMinimaSegreta;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Date getDataScadenzaTF() {
        return dataScadenzaTF;
    }

    public BigDecimal getPrezzoIniziale() {
        return prezzoIniziale;
    }

    public Timer getTimer() {
        return timer;
    }

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

    public BigDecimal getSogliaMinimaSegreta() {
        return sogliaMinimaSegreta;
    }


}
































