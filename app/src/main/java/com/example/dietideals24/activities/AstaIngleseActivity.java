package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietideals24.R;
import com.example.dietideals24.models.Asta;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AstaIngleseActivity extends AppCompatActivity {

    private String nickname, tipo, imageString;
    Date date;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_inglese);

        TextView nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        TextView venditoreTextView = findViewById(R.id.venditoreTextView);
        TextView descrizioneTextView = findViewById(R.id.descrizioneTextView);
        TextView categoriaTextView = findViewById(R.id.categoriaTextView);
        TextView keywordsTextView = findViewById(R.id.keywordsTextView);
        TextView offertaAttualeIngTextView = findViewById(R.id.offertaAttualeIngTextView);
        TextView vincenteTextView = findViewById(R.id.vincenteTextView);
        TextView tempoRimanenteTextView = findViewById(R.id.tempoRimanenteTextView);
        chronometer = findViewById(R.id.scadenzaChronometer);
        Button presentaOffertaIngleseButton = findViewById(R.id.presentaOffertaIngleseButton);
        Button backBtn = findViewById(R.id.backButtonInfoAsta);
        ImageView fotoProdottoImageView = findViewById(R.id.fotoProdottoImage);

        nickname = getIntent().getStringExtra("nickname");
        //Fare controlli sul tipo
        tipo = getIntent().getStringExtra("tipo");
        Asta asta = (Asta) getIntent().getSerializableExtra("asta");

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
            offertaAttualeIngTextView.setText("Offerta attuale: €" + asta.getOffertaAttuale().toString());
        } else {
            offertaAttualeIngTextView.setText("Prezzo iniziale: €" + asta.getPrezzoIniziale());
        }
        if (asta.getVincente() != null) {
            vincenteTextView.setText("Vincente: " + asta.getVincente());
        } else {
            vincenteTextView.setText("Nessuna offerta");
        }

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

        if (asta.getOffertaAttuale() != null) {
            BigDecimal x = new BigDecimal(0);
            x = asta.getOffertaAttuale().add(asta.getSogliaRialzoMinima());
            presentaOffertaIngleseButton.setText("Offri \u20AC" + x.toString());
        } else {
            BigDecimal x = new BigDecimal(0);
            x = asta.getPrezzoIniziale().add(asta.getSogliaRialzoMinima());
            presentaOffertaIngleseButton.setText("Offri \u20AC" + x.toString());
        }




        Calendar calendar = Calendar.getInstance();
        Date dataOraAttuale = calendar.getTime();
        calendar.setTime(dataOraAttuale);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        try {
            date = inputFormat.parse(asta.getDataScadenzaTF());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timer = date.getTime() - dataOraAttuale.getTime();

        // GESTIONE TIMER
        long elapsedTime = SystemClock.elapsedRealtime() + timer;
        chronometer.setBase(elapsedTime);
        chronometer.start();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getBase() < SystemClock.elapsedRealtime() + 10000) {
                    chronometer.setTextColor(Color.RED);
                }
                if(chronometer.getBase() < SystemClock.elapsedRealtime() + 1) {
                    chronometer.stop();
                    if(asta.getVincente() != null){
                        offertaAttualeIngTextView.setText("Venduto per \u20AC" + asta.getOffertaAttuale());
                        vincenteTextView.setText("Venduto a " + asta.getVincente());
                    } else {
                        vincenteTextView.setText("Non venduto");
                    }
                    tempoRimanenteTextView.setText("Conclusa");
                    chronometer.setVisibility(View.INVISIBLE);
                    presentaOffertaIngleseButton.setEnabled(false);
                    presentaOffertaIngleseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                    //manda notifiche

                }
            }
        });







        if(asta.getVincente() != null){
            vincenteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String checkActivity = "notmine";
                    Intent goToVincente = new Intent(AstaIngleseActivity.this, ProfiloActivity.class);
                    goToVincente.putExtra("nickname", nickname);
                    goToVincente.putExtra("tipo", tipo);
                    goToVincente.putExtra("checkActivity", checkActivity);
                    goToVincente.putExtra("other", asta.getVincente());
                    goToVincente.putExtra("asta", asta);
                    startActivity(goToVincente);
                }
            });
        }

        venditoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkActivity = "notmine";
                Intent goToVenditore = new Intent(AstaIngleseActivity.this, ProfiloActivity.class);
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
                Intent backToHome = new Intent(AstaIngleseActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                backToHome.putExtra("tipo", tipo);
                startActivity(backToHome);
            }
        });

        presentaOffertaIngleseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}