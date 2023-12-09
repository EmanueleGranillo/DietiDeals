package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.OffertaIngleseRequest;
import com.example.dietideals24.connection.OffertaTempoFissoRequest;
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

public class AstaTempoFissoActivity extends AppCompatActivity {

    private MyApiService apiService;
    private TextView nomeProdottoTextView, venditoreTextView, descrizioneTextView, categoriaTextView, keywordsTextView, offertaAttualeTFTextView, vincenteTextView, dataScadenzaTextView, scadutaTextView, attivaTextView, inserisciOffertaErrorTextView;
    private EditText inserisciOffertaEditText;
    private Button presentaOffertaTFButton, backButton, incrementaButton, decrementaButton;
    private String nickname, tipo, imageString;
    private boolean check;
    private Date date;
    private Asta asta;
    private int id;
    private ImageView fotoProdottoImageView;
    private BigDecimal offerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_tempo_fisso);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nomeProdottoTextView = findViewById(R.id.nomeProdottoTFTextView);
        venditoreTextView = findViewById(R.id.venditoreTFTextView);
        descrizioneTextView = findViewById(R.id.descrizioneTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        keywordsTextView = findViewById(R.id.keywordsTextView);
        offertaAttualeTFTextView = findViewById(R.id.offertaAttualeTextView);
        vincenteTextView = findViewById(R.id.vincenteTextView);
        dataScadenzaTextView = findViewById(R.id.dataScadenzaAstaTextView);
        scadutaTextView = findViewById(R.id.scadutaTFTextView);
        attivaTextView = findViewById(R.id.attivaTFTextView);
        inserisciOffertaErrorTextView = findViewById(R.id.offertaAttualeErrorTextView);
        inserisciOffertaEditText = findViewById(R.id.inserisciImportoOffertaEditText);
        presentaOffertaTFButton = findViewById(R.id.presentaOffertaTempoFissoButton);
        backButton = findViewById(R.id.astaTFBackButton);
        incrementaButton = findViewById(R.id.incrementaButton);
        decrementaButton = findViewById(R.id.decrementaButton);
        fotoProdottoImageView = findViewById(R.id.fotoProdottoTFImageView);

        inserisciOffertaErrorTextView.setText("");
        presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
        presentaOffertaTFButton.setEnabled(false);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        if(tipo.equals("venditore")){
            presentaOffertaTFButton.setVisibility(View.INVISIBLE);
            inserisciOffertaEditText.setVisibility(View.INVISIBLE);
            incrementaButton.setVisibility(View.INVISIBLE);
            decrementaButton.setVisibility(View.INVISIBLE);
        }
        id = getIntent().getIntExtra("id", 0);
        aggiornaCard(id);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    Intent backToHome = new Intent(AstaTempoFissoActivity.this, HomepageCompratoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                } else {
                    Intent backToHome = new Intent(AstaTempoFissoActivity.this, HomepageVenditoreActivity.class);
                    backToHome.putExtra("nickname", nickname);
                    backToHome.putExtra("tipo", tipo);
                    startActivity(backToHome);
                }
            }
        });

        inserisciOffertaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Questo metodo viene chiamato per notificare che qualcosa sta per cambiare nel testo.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Questo metodo viene chiamato per notificare che il testo è stato cambiato.
                // Qui puoi mettere il tuo codice per abilitare/disabilitare il bottone.
                if (attivaTextView.getVisibility() == View.VISIBLE) {
                    if(charSequence.toString().isEmpty() || charSequence.length() > 15) {
                        presentaOffertaTFButton.setEnabled(false);
                        presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                    } else {
                        presentaOffertaTFButton.setEnabled(true);
                        presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Questo metodo viene chiamato per notificare che il testo è stato cambiato in modo completo.
            }
        });

        presentaOffertaTFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserisciOffertaErrorTextView.setText("");
                if (inserisciOffertaEditText.getText().toString().isEmpty()) {
                    inserisciOffertaErrorTextView.setText("Inserisci un'offerta");
                } else {
                    offerta = new BigDecimal(inserisciOffertaEditText.getText().toString());

                    if (asta.getOffertaAttuale() != null) {
                        if (offerta.compareTo(asta.getOffertaAttuale()) <= 0) {
                            inserisciOffertaErrorTextView.setText("Inserisci un'offerta più alta di quella attuale");
                        } else {
                            if (offerta(id, offerta, nickname)) {
                                aggiornaCard(id);
                                Toast.makeText(AstaTempoFissoActivity.this, "Offerta riuscita", Toast.LENGTH_SHORT).show();
                            } else {
                                aggiornaCard(id);
                                Toast.makeText(AstaTempoFissoActivity.this, "Offerta fallita", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (offerta.compareTo(asta.getPrezzoIniziale()) <= 0) {
                            inserisciOffertaErrorTextView.setText("Inserisci un'offerta più alta del prezzo iniziale");
                        } else {
                            if (offerta(id, offerta, nickname)) {
                                aggiornaCard(id);
                                Toast.makeText(AstaTempoFissoActivity.this, "Offerta riuscita", Toast.LENGTH_SHORT).show();
                            } else {
                                aggiornaCard(id);
                                Toast.makeText(AstaTempoFissoActivity.this, "Offerta fallita", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });

    }
    public void aggiornaCard(int idasta){
        Call<Asta> call = apiService.getAsta(idasta);
        call.enqueue(new Callback<Asta>() {
            @Override
            public void onResponse(Call<Asta> call, Response<Asta> response) {
                if(response.isSuccessful()){
                    asta = response.body();            //RECUPERA ASTA
                    if (asta.getFotoProdotto() != null && !asta.getFotoProdotto().isEmpty()) {   //IMPOSTA FOTO
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
                    nomeProdottoTextView.setText(asta.getNomeProdotto());              //IMPOSTA NOME
                    venditoreTextView.setText("Venditore: " + asta.getCreatore());     //IMPOSTA VENDITORE
                    if (asta.getDescrizione().isEmpty()) {                             //IMPOSTA DESCRIZIONE
                        descrizioneTextView.setText("Nessuna descrizione");
                    } else {
                        descrizioneTextView.setText(asta.getDescrizione());
                    }
                    categoriaTextView.setText("Categoria: " + asta.getCategoria());                     //IMPOSTA CATEGORIA
                    if (asta.getParoleChiave().isEmpty()) {                             //IMPOSTA PAROLE CHIAVE
                        keywordsTextView.setText("Nessuna parola chiave");
                    } else {
                        keywordsTextView.setText("Parole chiave: " + asta.getParoleChiave());
                    }
                    if (asta.getOffertaAttuale() != null) {                              //IMPOSTA OFFERTA ATTUALE
                        offertaAttualeTFTextView.setText("Offerta attuale: €" + asta.getOffertaAttuale());
                    } else {
                        offertaAttualeTFTextView.setText("Prezzo iniziale: €" + asta.getPrezzoIniziale());
                    }
                    if (asta.getVincente() != null) {                                     //IMPOSTA VINCENTE
                        vincenteTextView.setText("Vincente: " + asta.getVincente());
                    } else {
                        vincenteTextView.setText("Nessuna offerta");
                    }

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    try {
                        date = inputFormat.parse(asta.getDataScadenzaTF());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dataScadenzaTextView.setText("Data di scadenza: " + outputFormat.format(date));
                    Calendar calendar = Calendar.getInstance();                           //IMPOSTA DATA SCADENZA
                    Date dataAttuale = calendar.getTime();
                    if(date.before(dataAttuale)){
                        presentaOffertaTFButton.setEnabled(false);
                        presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                        incrementaButton.setEnabled(false);
                        incrementaButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                        decrementaButton.setEnabled(false);
                        decrementaButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                        attivaTextView.setVisibility(View.INVISIBLE);
                        scadutaTextView.setVisibility(View.VISIBLE);
                        inserisciOffertaEditText.setFocusable(false);
                        inserisciOffertaEditText.setClickable(false);
                        inserisciOffertaEditText.setCursorVisible(false);
                        inserisciOffertaEditText.setFocusableInTouchMode(false);
                        if(asta.getOffertaAttuale() != null){
                            offertaAttualeTFTextView.setText("Venduto per " + asta.getOffertaAttuale());
                            vincenteTextView.setText("Venduto a: " + asta.getVincente());

                        } else {
                            offertaAttualeTFTextView.setText("Il prodotto non è stato venduto.");
                            vincenteTextView.setText("Nessun vincente.");
                        }
                    } else {
                        attivaTextView.setVisibility(View.VISIBLE);
                        scadutaTextView.setVisibility(View.INVISIBLE);
                    }

                    if(asta.getOffertaAttuale()==null){
                        inserisciOffertaEditText.setText(asta.getPrezzoIniziale().toString());
                    } else {
                        inserisciOffertaEditText.setText(asta.getOffertaAttuale().toString());
                    }



                    Toast.makeText(AstaTempoFissoActivity.this, asta.getId()+asta.getNomeProdotto(), Toast.LENGTH_LONG).show();
                } else {

                }
            }
            @Override
            public void onFailure(Call<Asta> call, Throwable t) {

            }
        });
    }

    private boolean offerta(int id, BigDecimal x, String nickname) {
        check = true;
        OffertaTempoFissoRequest offertaTempoFissoRequest = new OffertaTempoFissoRequest(id, x, nickname);
        Call<NumeroResponse> call = apiService.offertaTempoFisso(offertaTempoFissoRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if(response.isSuccessful()){
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 1) {
                        check = true;
                    } else {
                        check = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {

            }
        });

        return check;
    }



    private Bitmap cropImage(Bitmap originalImage) {
        int targetWidth = originalImage.getWidth();
        int targetHeight = (int) (targetWidth * 9.0 / 16.0); // Proporzione 16:9

        int startX = 0;
        int startY = (originalImage.getHeight() - targetHeight) / 2;

        return Bitmap.createBitmap(originalImage, startX, startY, targetWidth, targetHeight);
    }

}