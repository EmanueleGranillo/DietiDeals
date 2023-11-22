package com.example.dietideals24;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

public class Asta implements Serializable {

    private String titoloAsta;
    private String tipologia;
    private Date dataFineAstaTempoFisso;
    private BigDecimal offertaAttuale;
    private BigDecimal sogliaMinimaSegreta;
    private BigDecimal baseAsta;
    private Timer timer;
    private BigDecimal sogliaRialzoMinima;
    private long intervalloTimer;
    private BigDecimal prezzoVendita; //Per asta al ribasso
    private BigDecimal prezzoMinimoSegreto;
    private BigDecimal importoDecremento;


    //Costruttore quando tipologia asta = tempo fisso
    public Asta(String titoloAsta, String tipologia, Date dataFineAstaTempoFisso, BigDecimal offertaAttuale, BigDecimal sogliaMinimaSegreta) {
        this.titoloAsta = titoloAsta;
        this.tipologia = tipologia;
        this.dataFineAstaTempoFisso = dataFineAstaTempoFisso;
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
        this.titoloAsta = titoloAsta;
        this.tipologia = tipologia;
        this.baseAsta = baseAsta;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.intervalloTimer = intervalloTimer;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, intervalloTimer);
    }


    // Costruttore per asta all'inglese SENZA specificare l'intervallo del timer
    public Asta(String titoloAsta, String tipologia, BigDecimal baseAsta, BigDecimal sogliaRialzoMinima) {
        this.titoloAsta = titoloAsta;
        this.tipologia = tipologia;
        this.offertaAttuale = baseAsta;
        this.sogliaRialzoMinima = sogliaRialzoMinima;
        this.intervalloTimer = 3600 * 1000;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, intervalloTimer);
    }


    // Metodo per gestire l'offerta effettuata (asta all'inglese)
    public void effettuaOffertaIng(BigDecimal importo) {
        if (importo.subtract(offertaAttuale).compareTo(sogliaRialzoMinima) > 0) {
            this.offertaAttuale = importo;
            this.timer.cancel(); // Annulla il timer corrente

            //Avvio di nuovo timer di un'ora
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new AstaTimerTask(), 0, intervalloTimer);
        }
    }

    // Classe interna per rappresentare l'attività del timer
    private class AstaTimerTask extends TimerTask {
        @Override
        public void run() {

            if (tipologia.equals("Asta all'inglese")) {
                // Logica da eseguire quando il timer scade
                System.out.println("Timer scaduto per l'asta: " + titoloAsta);

                // Considera l'asta come fallita se nessuna offerta è stata presentata
                if (offertaAttuale.equals(baseAsta)) {
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
        this.prezzoVendita = prezzoVendita.subtract(importoDecremento);

        if (this.prezzoVendita.compareTo(this.prezzoMinimoSegreto) < 0) {
            System.out.println("Asta fallita. Nessuna offerta presentata.");
        }
    }


    //costruttore per asta al ribasso
    public Asta(String titoloAsta, String tipologia, BigDecimal prezzoIniziale, long intervalloTimer, BigDecimal importoDecremento, BigDecimal prezzoMinimoSegreto) {
        this.titoloAsta = titoloAsta;
        this.tipologia = tipologia;
        this.prezzoVendita = prezzoIniziale;
        this.intervalloTimer = intervalloTimer;
        this.sogliaMinimaSegreta = prezzoMinimoSegreto;
        this.importoDecremento = importoDecremento;
        this.prezzoMinimoSegreto = prezzoMinimoSegreto;


        // Inizializza e avvia il timer con l'intervallo specificato
        this.timer = new Timer();
        AstaTimerTask astaTimerTask = new AstaTimerTask();
        this.timer.scheduleAtFixedRate(astaTimerTask, 0, intervalloTimer);

    }


    public void setTitoloAsta(String titoloAsta) {
        this.titoloAsta = titoloAsta;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setDataFineAstaTempoFisso(Date dataFineAstaTempoFisso) {
        this.dataFineAstaTempoFisso = dataFineAstaTempoFisso;
    }

    public void setOffertaAttuale(BigDecimal offertaAttuale) {
        this.offertaAttuale = offertaAttuale;
    }

    public void setSogliaMinimaSegreta(BigDecimal sogliaMinimaSegreta) {
        this.sogliaMinimaSegreta = sogliaMinimaSegreta;
    }

    public void setBaseAsta(BigDecimal baseAsta) {
        this.baseAsta = baseAsta;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setSogliaRialzoMinima(BigDecimal sogliaRialzoMinima) {
        this.sogliaRialzoMinima = sogliaRialzoMinima;
    }

    public void setIntervalloTimer(long intervalloTimer) {
        this.intervalloTimer = intervalloTimer;
    }

    public void setPrezzoVendita(BigDecimal prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    public void setImportoDecremento(BigDecimal importoDecremento) {
        this.importoDecremento = importoDecremento;
    }

    public void setPrezzoMinimoSegreto(BigDecimal prezzoMinimoSegreto) {
        this.prezzoMinimoSegreto = prezzoMinimoSegreto;
    }

    public String getTitoloAsta() {
        return titoloAsta;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Date getDataFineAstaTempoFisso() {
        return dataFineAstaTempoFisso;
    }

    public BigDecimal getOffertaAttuale() {
        return offertaAttuale;
    }

    public BigDecimal getSogliaMinimaSegreta() {
        return sogliaMinimaSegreta;
    }

    public BigDecimal getBaseAsta() {
        return baseAsta;
    }

    public Timer getTimer() {
        return timer;
    }

    public BigDecimal getSogliaRialzoMinima() {
        return sogliaRialzoMinima;
    }

    public long getIntervalloTimer() {
        return intervalloTimer;
    }

    public BigDecimal getPrezzoVendita() {
        return prezzoVendita;
    }

    public BigDecimal getImportoDecremento() {
        return importoDecremento;
    }

    public BigDecimal getPrezzoMinimoSegreto() {
        return prezzoMinimoSegreto;
    }


}
































