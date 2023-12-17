package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.VincitoreRibassoRequest;
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
    private String nickname, tipo, imageString, tipologia = "asta al ribasso";
    private Date date;
    double differenzaSecondi, numeroIntervalli, tempoPassatoPercentuale, secondiPassatiTimerCorrente, prezzoAttualeDouble;
    int numeroIntervalliPassati, timerRimanenteSecondi;
    BigDecimal prezzoAttuale;
    Asta asta;
    TextView nomeProdottoTextView, venditoreTextView, descrizioneTextView, categoriaTextView, keywordsTextView, prezzoAttualeTextView, importoDecrementoTextView, decrementoPrezzoTextView, vincitoreTextView, countDownRibTxtView;
    ImageView fotoProdottoImageView;
    Button acquistaRibassoButton, backBtn;

    private ImageButton infoAstaRibassoButton;
    private PopupWindow popupWindow;
    private TextView popupText;
    private View mainLayout, cardLayout;


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
        infoAstaRibassoButton = findViewById(R.id.infoAstaRibassoButton);
        mainLayout = findViewById(R.id.astaRibassoLayout);
        cardLayout = findViewById(R.id.astaRibassoCardView);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        id = getIntent().getIntExtra("id", 0);

        aggiornaCard(id);


        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Nascondi il popup quando si tocca fuori da esso
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true; // Indica che l'evento è stato gestito
                }
                return false; // Lascia l'evento di tocco inalterato
            }
        });

        cardLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Nascondi il popup quando si tocca fuori da esso
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true; // Indica che l'evento è stato gestito
                }
                return false; // Lascia l'evento di tocco inalterato
            }
        });

        infoAstaRibassoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    showPopup();
                }


            }
        });



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
                goToVenditore.putExtra("tipologia", tipologia);
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
                    backToHome.putExtra("tempoRimanente", timerRimanenteSecondi);
                    backToHome.putExtra("prezzoAttuale", prezzoAttuale);
                    startActivity(backToHome);
                } else {
                    Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageVenditoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                }
            }
        });


        acquistaRibassoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update table con vincente
                updateVincitore(nickname, id);
                countDownRibTxtView.setVisibility(View.INVISIBLE);
                decrementoPrezzoTextView.setText("Asta conclusa");
                decrementoPrezzoTextView.setTextColor(Color.RED);
                acquistaRibassoButton.setEnabled(false);
                acquistaRibassoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
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


                if (secondsRemaining <= 10) {
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
                    updateRibasso(asta.getId());
                    timerTextView.setText("Conclusa");
                    acquistaRibassoButton.setVisibility(View.INVISIBLE);
                    decrementoPrezzoTextView.setVisibility(View.INVISIBLE);
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





    public void updateRibasso(int id){
        Call<Void> call = apiService.updateRibasso(id);
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


    public void updateVincitore(String nickname, int id){
        VincitoreRibassoRequest vincitoreRibassoRequest = new VincitoreRibassoRequest(nickname, id);
        String validazione = vincitoreRibassoRequest.validate();
        Call<NumeroResponse> call = apiService.updatevincitoreribasso(vincitoreRibassoRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if (response.isSuccessful() && validazione.equals("I valori sono corretti.")) {
                    NumeroResponse num = response.body();
                    if(num.getNumero() == 0){
                        vincitoreTextView.setText("Il prodotto è già stato venduto!");
                    } else {
                        vincitoreTextView.setText("Hai vinto!");
                    }
                } else {
                    vincitoreTextView.setText(validazione);
                }
            }
            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
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
                    vincitoreTextView.setText("Nessuna offerta");
                    importoDecrementoTextView.setText("Importo decremento: " + asta.getImportoDecremento());

                    // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
                    if (asta.getFotoProdotto() != null && !asta.getFotoProdotto().isEmpty()) {
                        imageString = asta.getFotoProdotto();
                        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        // Esegui il ritaglio dell'immagine
                        Bitmap croppedImage = cropImage(decodedByte);

                        // Imposta l'immagine nell'ImageView
                        fotoProdottoImageView.setImageBitmap(croppedImage);

                    } else {
                        // Immagine di fallback o gestisci la situazione come desideri
                        fotoProdottoImageView.setImageResource(R.mipmap.ic_no_icon_foreground);
                    }
                    fotoProdottoImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    if (tipo.equals("venditore")) {
                        acquistaRibassoButton.setVisibility(View.INVISIBLE);
                    }

                    aggiornaValoriAstaRibasso(asta);


                    if (asta.getVincente() != null) {
                        vincitoreTextView.setText("Vincente: " + asta.getVincente());
                        prezzoAttualeTextView.setText("Prezzo di partenza: \u20AC" + asta.getPrezzoIniziale());
                        countDownRibTxtView.setVisibility(View.INVISIBLE);
                        decrementoPrezzoTextView.setText("Asta conclusa");
                        decrementoPrezzoTextView.setTextColor(Color.RED);
                        acquistaRibassoButton.setEnabled(false);
                        acquistaRibassoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                    }

                    else if(prezzoAttuale.compareTo(asta.getSogliaSegreta()) < 0) {
                        prezzoAttualeTextView.setText("Prezzo di partenza: \u20AC" + asta.getPrezzoIniziale());
                        countDownRibTxtView.setVisibility(View.INVISIBLE);
                        decrementoPrezzoTextView.setText("Asta conclusa");
                        decrementoPrezzoTextView.setTextColor(Color.RED);
                        acquistaRibassoButton.setEnabled(false);
                        acquistaRibassoButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
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



    private Bitmap cropImage(Bitmap originalImage) {
        int targetWidth = originalImage.getWidth();
        int targetHeight = (int) (targetWidth * 9.0 / 16.0); // Proporzione 16:9

        int startX = 0;
        int startY = (originalImage.getHeight() - targetHeight) / 2;

        return Bitmap.createBitmap(originalImage, startX, startY, targetWidth, targetHeight);
    }

    private void showPopup(){
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        popupText = popupView.findViewById(R.id.popupText);
        popupText.setText("Ogni volta che il timer scade il prezzo verrà decrementato. Il primo a presentare un'offerta si aggiudicherà il prodotto a quel prezzo.");

        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(300);
        popupView.startAnimation(scaleAnimation);

        int offsetX = -750;
        int offsetY = -350; // Offset verso l'alto rispetto al bottone

        // Mostra il pop-up nella posizione desiderata
        popupWindow.showAsDropDown(infoAstaRibassoButton, offsetX, offsetY);

    }

    @Override
    public void onBackPressed() {
        if(tipo.equals("compratore")){
            Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageCompratoreActivity.class);
            backToHome.putExtra("nickname", nickname);
            backToHome.putExtra("tipo", tipo);
            backToHome.putExtra("tempoRimanente", timerRimanenteSecondi);
            backToHome.putExtra("prezzoAttuale", prezzoAttuale);
            startActivity(backToHome);
        } else {
            Intent backToHome = new Intent(AstaRibassoActivity.this, HomepageVenditoreActivity.class);
            backToHome.putExtra("nickname", nickname);
            backToHome.putExtra("tipo", tipo);
            startActivity(backToHome);
        }
    }



}