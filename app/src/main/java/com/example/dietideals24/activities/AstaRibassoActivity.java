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


public class AstaRibassoActivity extends AppCompatActivity {

    private String nickname, tipo, imageString;
    Date date;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_ribasso);

        TextView nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        TextView venditoreTextView = findViewById(R.id.venditoreTextView);
        TextView descrizioneTextView = findViewById(R.id.descrizioneTextView);
        TextView categoriaTextView = findViewById(R.id.categoriaTextView);
        TextView keywordsTextView = findViewById(R.id.keywordsTextView);
        TextView prezzoAttualeTextView = findViewById(R.id.prezzoAttualeTextView);
        TextView importoDecrementoTextView = findViewById(R.id.importoDecrementoTextView);
        TextView decrementoPrezzoTextView = findViewById(R.id.decrementoPrezzoTextView);
        TextView vincitoreTextView = findViewById(R.id.vincitoreTextView);
        chronometer = findViewById(R.id.scadenzaChronometer);
        Button acquistaRibassoButton = findViewById(R.id.acquistaRibassoButton);
        Button backBtn = findViewById(R.id.backButtonInfoAsta);
        ImageView fotoProdottoImageView = findViewById(R.id.fotoProdottoImage);


        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        Asta asta = (Asta) getIntent().getSerializableExtra("asta");



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
                        decrementoPrezzoTextView.setText("Venduto per \u20AC" + asta.getOffertaAttuale());
                        vincitoreTextView.setText("Venduto a: " + asta.getVincente());
                        prezzoAttualeTextView.setText("Prezzo vendita: €" + asta.getOffertaAttuale());
                    } else {
                        vincitoreTextView.setText("Non venduto");
                    }
                    decrementoPrezzoTextView.setText("Conclusa");
                    chronometer.setVisibility(View.INVISIBLE);
                    acquistaRibassoButton.setEnabled(false);
                    acquistaRibassoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                    //manda notifiche
                }
            }
        });


        if(asta.getVincente() != null){
            vincitoreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String checkActivity = "notmine";
                    Intent goToVincente = new Intent(AstaRibassoActivity.this, ProfiloActivity.class);
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
                Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                backToHome.putExtra("tipo", tipo);
                startActivity(backToHome);
            }
        });


    }





}