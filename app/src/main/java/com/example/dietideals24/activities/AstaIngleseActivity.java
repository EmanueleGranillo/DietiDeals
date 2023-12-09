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
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.OffertaIngleseRequest;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Asta;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstaIngleseActivity extends AppCompatActivity {

    private MyApiService apiService;
    private String nickname, tipo, imageString, tipologia = "asta inglese", dateString;
    private boolean check;
    private Date date;
    private Chronometer chronometer;
    private Asta asta;
    private int id;
    private TextView nomeProdottoTextView, venditoreTextView, descrizioneTextView, categoriaTextView, keywordsTextView, offertaAttualeIngTextView, vincenteTextView, tempoRimanenteTextView;
    private Button presentaOffertaIngleseButton, backBtn;
    private ImageView fotoProdottoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_inglese);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nomeProdottoTextView = findViewById(R.id.nomeProdottoTFTextView);
        venditoreTextView = findViewById(R.id.venditoreTextView);
        descrizioneTextView = findViewById(R.id.descrizioneTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        keywordsTextView = findViewById(R.id.keywordsTextView);
        offertaAttualeIngTextView = findViewById(R.id.offertaAttualeIngTextView);
        vincenteTextView = findViewById(R.id.vincenteTextView);
        tempoRimanenteTextView = findViewById(R.id.tempoRimanenteTextView);
        chronometer = findViewById(R.id.scadenzaChronometer);
        presentaOffertaIngleseButton = findViewById(R.id.presentaOffertaIngleseButton);
        backBtn = findViewById(R.id.backButtonInfoAsta);
        fotoProdottoImageView = findViewById(R.id.fotoProdottoImage);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        if(tipo.equals("venditore")){
            presentaOffertaIngleseButton.setVisibility(View.INVISIBLE);
        }
        id = getIntent().getIntExtra("id", 0);
        aggiornaCard(id);


        // LISTENERS

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
                    mandaNotifiche(id);
                    //manda notifiche

                }
            }
        });

        vincenteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(asta.getVincente() != null) {
                    String checkActivity = "notmine";
                    Intent goToVincente = new Intent(AstaIngleseActivity.this, ProfiloActivity.class);
                    goToVincente.putExtra("nickname", nickname);
                    goToVincente.putExtra("tipo", tipo);
                    goToVincente.putExtra("checkActivity", checkActivity);
                    goToVincente.putExtra("other", asta.getVincente());
                    goToVincente.putExtra("id", id);
                    goToVincente.putExtra("tipologia", tipologia);
                    startActivity(goToVincente);
                }
            }
        });

        venditoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    String checkActivity = "notmine";
                    Intent goToVenditore = new Intent(AstaIngleseActivity.this, ProfiloActivity.class);
                    goToVenditore.putExtra("nickname", nickname);
                    goToVenditore.putExtra("tipo", tipo);
                    goToVenditore.putExtra("checkActivity", checkActivity);
                    goToVenditore.putExtra("other", asta.getCreatore());
                    goToVenditore.putExtra("id", id);
                    goToVenditore.putExtra("tipologia", tipologia);
                    startActivity(goToVenditore);
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    Intent backToHome = new Intent(AstaIngleseActivity.this, HomepageCompratoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                } else {
                    Intent backToHome = new Intent(AstaIngleseActivity.this, HomepageVenditoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                }
            }
        });


        presentaOffertaIngleseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal x = new BigDecimal(0) ;
                if(asta.getOffertaAttuale()==null){
                    x = asta.getPrezzoIniziale();
                } else {
                    x = asta.getOffertaAttuale().add(asta.getSogliaRialzoMinima());
                }

                Calendar calendar = Calendar.getInstance();
                Date dataOraAttuale = calendar.getTime();
                calendar.setTime(dataOraAttuale);
                calendar.add(Calendar.SECOND, (int) asta.getResetTimer());
                date = calendar.getTime();
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                dateString = outputFormat.format(date);

                if(offerta(asta.getId(), x, nickname, dateString)){
                    aggiornaCard(id);
                } else {
                    //Offerta fallita
                }

            }
        });

    }

    private boolean offerta(int id, BigDecimal x, String nickname, String dateString) {
        check = true;
        OffertaIngleseRequest offertaIngleseRequest = new OffertaIngleseRequest(id, x, nickname, dateString);
        Call<NumeroResponse> call = apiService.offertaInglese(offertaIngleseRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if(response.isSuccessful()){
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 1) {
                        check = true;
                        Toast.makeText(AstaIngleseActivity.this, "Offerta riuscita", Toast.LENGTH_SHORT).show();
                    } else {
                        check = false;
                        Toast.makeText(AstaIngleseActivity.this, "Offerta fallita", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {

            }
        });

        return check;
    }

    @Override
    public void onBackPressed() {
        Intent backToHome = new Intent(AstaIngleseActivity.this, HomepageCompratoreActivity.class);
        backToHome.putExtra("nickname", nickname);
        backToHome.putExtra("tipo", tipo);
        startActivity(backToHome);
        super.onBackPressed();
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
                    if (asta.getOffertaAttuale() != null) {
                        offertaAttualeIngTextView.setText("Offerta attuale: €" + asta.getOffertaAttuale());
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

                        // Esegui il ritaglio dell'immagine
                        Bitmap croppedImage = cropImage(decodedByte);

                        fotoProdottoImageView.setImageBitmap(croppedImage);
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
                        x = asta.getPrezzoIniziale();
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
                    long elapsedTime = SystemClock.elapsedRealtime() + (timer);
                    chronometer.setBase(elapsedTime);
                    chronometer.start();
                } else {

                }
            }
            @Override
            public void onFailure(Call<Asta> call, Throwable t) {

            }
        });
    }

    public void mandaNotifiche(int id){
        Call<Void> call = apiService.mandaNotifiche(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



    private Bitmap cropImage(Bitmap originalImage) {
        int targetWidth = originalImage.getWidth();
        int targetHeight = (int) (targetWidth * 9.0 / 16.0); // Proporzione 16:9

        int startX = 0;
        int startY = (originalImage.getHeight() - targetHeight) / 2;

        return Bitmap.createBitmap(originalImage, startX, startY, targetWidth, targetHeight);
    }



}