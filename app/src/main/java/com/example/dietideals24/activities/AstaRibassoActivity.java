package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.OffertaRibassoRequest;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Asta;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AstaRibassoActivity extends AppCompatActivity {
    MyApiService apiService;
    int id;
    private String nickname, tipo, imageString;
    private Date date;
    //private Chronometer chronometer;
    Date dateCreazioneAsta;

    double differenzaSecondi, numeroIntervalli, tempoPassatoPercentuale, secondiPassatiTimerCorrente, prezzoAttualeDouble;
    int numeroIntervalliPassati, timerRimanenteSecondi;
    BigDecimal prezzoAttuale;
    Asta asta;
    TextView nomeProdottoTextView, venditoreTextView, descrizioneTextView, categoriaTextView, keywordsTextView, prezzoAttualeTextView, importoDecrementoTextView, decrementoPrezzoTextView, vincitoreTextView, countDownRibTxtView;
    ImageView fotoProdottoImageView;
    Button acquistaRibassoButton, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_ribasso);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        venditoreTextView = findViewById(R.id.venditoreTextView);
        descrizioneTextView = findViewById(R.id.descrizioneTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        keywordsTextView = findViewById(R.id.keywordsTextView);
        prezzoAttualeTextView = findViewById(R.id.prezzoAttualeTextView);
        importoDecrementoTextView = findViewById(R.id.importoDecrementoTextView);
        decrementoPrezzoTextView = findViewById(R.id.decrementoPrezzoTextView);
        vincitoreTextView = findViewById(R.id.vincitoreTextView);
        countDownRibTxtView = findViewById(R.id.countDownRibTextView);
        acquistaRibassoButton = findViewById(R.id.acquistaRibassoButton);
        backBtn = findViewById(R.id.backButtonInfoAsta);
        fotoProdottoImageView = findViewById(R.id.fotoProdottoImage);


        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        id = getIntent().getIntExtra("id", 0);

        aggiornaCard(id);








        /*
        // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
        if (asta.getFotoProdotto() != null && !asta.getFotoProdotto().isEmpty()) {
            imageString = asta.getFotoProdotto();
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            fotoProdottoImageView.setImageBitmap(decodedByte);
        } else {
            // Immagine di fallback o gestisci la situazione come desideri
            fotoProdottoImageView.setImageResource(R.mipmap.ic_no_icon_foreground);
        }
        fotoProdottoImageView.setScaleType(ImageView.ScaleType.FIT_XY);


        nomeProdottoTextView.setText(asta.getNomeProdotto());
        venditoreTextView.setText("Venditore: " + asta.getCreatore());
        if (asta.getDescrizione().isEmpty()) {
            descrizioneTextView.setText("Nessuna descrizione");
        } else {
            descrizioneTextView.setText(asta.getDescrizione());
        }
        categoriaTextView.setText(asta.getCategoria());
        if (asta.getParoleChiave().isEmpty()) {
            keywordsTextView.setText("Nessuna parola chiave");
        } else {
            keywordsTextView.setText("Parole chiave: " + asta.getParoleChiave());
        }
        if (asta.getOffertaAttuale() != null) {
            prezzoAttualeTextView.setText("Prezzo attuale: €" + asta.getOffertaAttuale().toString());
        } else {
            prezzoAttualeTextView.setText("Prezzo iniziale: €" + asta.getPrezzoIniziale());
        }
        importoDecrementoTextView.setText("Importo decremento: " + asta.getImportoDecremento());


         */







        /*
        differenzaSecondi = (dataOraAttuale.getTime() - dateCreazioneAsta.getTime())/1000;
        System.out.println("differenza secondi: " + differenzaSecondi);
        System.out.println("timer: " + asta.getResetTimer());
        numeroIntervalli = differenzaSecondi/(double) asta.getResetTimer();
        System.out.println("numero intervalli: " + numeroIntervalli);

        numeroIntervalliPassati = (int) numeroIntervalli;
        System.out.println("numero intervalli passati: " + numeroIntervalliPassati);

        tempoPassatoPercentuale = numeroIntervalli - numeroIntervalliPassati;
        System.out.println("tempo passato percentuale: " + tempoPassatoPercentuale);

        secondiPassatiTimerCorrente = tempoPassatoPercentuale * asta.getResetTimer();
        System.out.println("secondi passati timer corrente: " + (int) secondiPassatiTimerCorrente);

        timerRimanenteSecondi = (int)asta.getResetTimer() - (int)secondiPassatiTimerCorrente;
        System.out.println("secondi rimanenti timer corrente: " + (int) timerRimanenteSecondi);
         */














        /*
        if(prezzoAttuale.compareTo(asta.getSogliaSegreta()) <= 0) {
            // Asta conclusa. Fallita per raggiungimento soglia minima
            decrementoPrezzoTextView.setText("Conclusa");
            vincitoreTextView.setText("Non venduta");
            chronometer.setVisibility(View.INVISIBLE);
            acquistaRibassoButton.setEnabled(false);
            acquistaRibassoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
            //manda notifiche

        } else {

            prezzoAttualeTextView.setText("Prezzo attuale: \u20AC" + prezzoAttuale);

            long timer = timerRimanenteSecondi / 1000;


            // GESTIONE TIMER
            long elapsedTime = SystemClock.elapsedRealtime() + timer;
            chronometer.setBase(elapsedTime);
            chronometer.start();

            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if(chronometer.getBase() <= SystemClock.elapsedRealtime() + 10000) {
                        chronometer.setTextColor(Color.RED);
                    }
                    if(chronometer.getBase() < SystemClock.elapsedRealtime() + 1) {
                        chronometer.stop();
                    }
                }
            });
        }
         */


        vincitoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asta.getVincente() != null) {
                    String checkActivity = "notmine";
                    Intent goToVincente = new Intent(AstaRibassoActivity.this, ProfiloActivity.class);
                    goToVincente.putExtra("nickname", nickname);
                    goToVincente.putExtra("tipo", tipo);
                    goToVincente.putExtra("checkActivity", checkActivity);
                    goToVincente.putExtra("other", asta.getVincente());
                    goToVincente.putExtra("asta", asta);
                    startActivity(goToVincente);
                }
            }
        });




        venditoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkActivity = "notmine";
                Intent goToVenditore = new Intent(AstaRibassoActivity.this, ProfiloActivity.class);
                goToVenditore.putExtra("nickname", nickname);
                goToVenditore.putExtra("tipo", tipo);
                goToVenditore.putExtra("checkActivity", checkActivity);
                goToVenditore.putExtra("other", asta.getCreatore());
                goToVenditore.putExtra("asta", asta);
                startActivity(goToVenditore);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageCompratoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                } else {
                    Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageVenditoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                }
            }
        });


    }



    private void createAndStartCountDownTimer(TextView timerTextView, TextView offertaAttualeRibassoTextView, long timeMillis, Asta asta) {
        CountDownTimer countDownTimer = new CountDownTimer(timeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI on each tick (every second)
                long secondsRemaining = millisUntilFinished / 1000;

                // Calcola ore, minuti e secondi rimanenti
                long hours = secondsRemaining / 3600;
                long minutes = (secondsRemaining % 3600) / 60;
                long seconds = secondsRemaining % 60;

                // Converti il tempo rimanente in un formato "00:00:00"
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

                timerTextView.setText(formattedTime);


                if (secondsRemaining < 10) {
                    timerTextView.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFinish() {
                // Actions to be performed when the timer finishes
                timerTextView.setTextColor(Color.BLACK);
                prezzoAttuale = asta.getOffertaAttuale();
                prezzoAttuale = prezzoAttuale.subtract(asta.getImportoDecremento());
                asta.setOffertaAttuale(prezzoAttuale);
                offertaAttualeRibassoTextView.setText("Prezzo attuale: \u20AC" + prezzoAttuale);

                if (prezzoAttuale.compareTo(asta.getSogliaSegreta()) < 0) {
                    updateRibasso(prezzoAttuale, asta.getId());
                    timerTextView.setText("Conclusa");
                } else {
                    createAndStartCountDownTimer(timerTextView, offertaAttualeRibassoTextView, asta.getResetTimer() * 1000, asta);
                }
            }
        };

        // Start the timer
        countDownTimer.start();
    }



    public void aggiornaValoriAstaRibasso(Asta asta) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date dataOraAttuale = calendar.getTime();
        calendar.setTime(dataOraAttuale);
        System.out.println("data ora attuale: " + dataOraAttuale);

        try {
            Date dataCreazioneAsta = inputFormat.parse(asta.getDataScadenzaTF()); // Assumi che esista un metodo getDataCreazione nella classe Asta
            System.out.println("data creazione asta: " + dataCreazioneAsta);
            System.out.println("data creazione asta secondi: " + dataCreazioneAsta.getTime()/1000);
            System.out.println("data creazione asta secondi + un'ora: " + (dataCreazioneAsta.getTime() + 360000)/1000);

            differenzaSecondi = (dataOraAttuale.getTime() - (dataCreazioneAsta.getTime() + 3600000)) / 1000;    //aggiungo a dataCreazioneAsta un'ora perchè sul db GTM00
            System.out.println("differenza secondi: " + differenzaSecondi);

            numeroIntervalli = differenzaSecondi / (double) asta.getResetTimer();
            System.out.println("numero Intervalli : " + numeroIntervalli);

            numeroIntervalliPassati = (int) numeroIntervalli;
            System.out.println("numero Intervalli Passati: " + numeroIntervalliPassati);

            tempoPassatoPercentuale = numeroIntervalli - numeroIntervalliPassati;
            System.out.println("tempo Passato Percentuale: " + tempoPassatoPercentuale);

            secondiPassatiTimerCorrente = tempoPassatoPercentuale * asta.getResetTimer();
            System.out.println("secondi Passati Timer Corrente: " + secondiPassatiTimerCorrente);

            timerRimanenteSecondi = (int) asta.getResetTimer() - (int) secondiPassatiTimerCorrente;
            System.out.println("timer Rimanente Secondi: " + timerRimanenteSecondi);

            prezzoAttualeDouble = (asta.getPrezzoIniziale().subtract(asta.getImportoDecremento().multiply(BigDecimal.valueOf(numeroIntervalliPassati)))).doubleValue();
            System.out.println("Prezzo attuale double: " + prezzoAttualeDouble);

            prezzoAttuale = new BigDecimal(prezzoAttualeDouble);
            System.out.println("prezzo Attuale" + prezzoAttuale);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }





    public void updateRibasso(BigDecimal offertaAttuale, int id){
        OffertaRibassoRequest offertaRibassoRequest = new OffertaRibassoRequest(offertaAttuale, id);
        Call<Void> call = apiService.updateRibasso(offertaRibassoRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                } else {
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }






    public void aggiornaCard(int idasta){
        Call<Asta> call = apiService.getAsta(idasta);
        call.enqueue(new Callback<Asta>() {
            @Override
            public void onResponse(Call<Asta> call, Response<Asta> response) {
                if(response.isSuccessful()){
                    asta = response.body();
                    nomeProdottoTextView.setText(asta.getNomeProdotto());
                    venditoreTextView.setText("Venditore: " + asta.getCreatore());
                    if (asta.getDescrizione().isEmpty()) {
                        descrizioneTextView.setText("Nessuna descrizione");
                    } else {
                        descrizioneTextView.setText(asta.getDescrizione());
                    }
                    categoriaTextView.setText(asta.getCategoria());
                    if (asta.getParoleChiave().isEmpty()) {
                        keywordsTextView.setText("Nessuna parola chiave");
                    } else {
                        keywordsTextView.setText("Parole chiave: " + asta.getParoleChiave());
                    }
                    prezzoAttualeTextView.setText("Prezzo attuale: €" + asta.getOffertaAttuale());
                    if (asta.getVincente() != null) {
                        vincitoreTextView.setText("Vincente: " + asta.getVincente());
                    } else {
                        vincitoreTextView.setText("Nessuna offerta");
                    }
                    importoDecrementoTextView.setText("Importo decremento: " + asta.getImportoDecremento());

                    // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
                    if (asta.getFotoProdotto() != null && !asta.getFotoProdotto().isEmpty()) {
                        imageString = asta.getFotoProdotto();
                        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        fotoProdottoImageView.setImageBitmap(decodedByte);
                    } else {
                        // Immagine di fallback o gestisci la situazione come desideri
                        fotoProdottoImageView.setImageResource(R.mipmap.ic_no_icon_foreground);
                    }
                    fotoProdottoImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    if (tipo.equals("venditore")) {
                        acquistaRibassoButton.setVisibility(View.INVISIBLE);
                    }

                    aggiornaValoriAstaRibasso(asta);

                    if(prezzoAttuale.compareTo(asta.getSogliaSegreta()) < 0) {
                        prezzoAttualeTextView.setText("Prezzo di partenza: \u20AC" + asta.getPrezzoIniziale());
                        countDownRibTxtView.setVisibility(View.INVISIBLE);
                        decrementoPrezzoTextView.setText("Asta conclusa");
                    } else {

                        prezzoAttualeTextView.setText("Prezzo attuale: \u20AC" + prezzoAttuale);

                        asta.setOffertaAttuale(prezzoAttuale);

                        long initialTimeMillis = timerRimanenteSecondi * 1000;

                        createAndStartCountDownTimer(countDownRibTxtView, prezzoAttualeTextView, initialTimeMillis, asta);

                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<Asta> call, Throwable t) {

            }
        });
    }





}