package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
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

public class AstaTempoFissoActivity extends AppCompatActivity {

    private TextView nomeProdottoTextView, creatoreTextView, descrizioneTextView, categoriaTextView, keywordsTextView, offertaAttualeTextView, vincenteTextView, dataScadenzaTextView, inserisciOffertaErrorTextView;
    private EditText inserisciOffertaEditText;
    private Button presentaOffertaTFButton, backButton;
    private String nickname, tipo, imageString;
    private Date date;
    private ImageView fotoProdottoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_tempo_fisso);

        nomeProdottoTextView = findViewById(R.id.nomeProdottoTFTextView);
        creatoreTextView = findViewById(R.id.creatoreTextView);
        descrizioneTextView = findViewById(R.id.descrizioneTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        keywordsTextView = findViewById(R.id.keywordsTextView);
        offertaAttualeTextView = findViewById(R.id.offertaAttualeTextView);
        vincenteTextView = findViewById(R.id.vincenteTextView);
        dataScadenzaTextView = findViewById(R.id.dataScadenzaAstaTextView);
        inserisciOffertaErrorTextView = findViewById(R.id.offertaAttualeErrorTextView);
        inserisciOffertaEditText = findViewById(R.id.inserisciImportoOffertaEditText);
        presentaOffertaTFButton = findViewById(R.id.presentaOffertaTempoFissoButton);
        backButton = findViewById(R.id.astaTFBackButton);
        fotoProdottoImageView = findViewById(R.id.fotoProdottoTFImageView);

        presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        Asta asta = (Asta) getIntent().getSerializableExtra("asta");

        nomeProdottoTextView.setText(asta.getNomeProdotto());
        creatoreTextView.setText("Venditore: " +asta.getCreatore());
        if(asta.getDescrizione().isEmpty()){
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
        if(asta.getOffertaAttuale() != null) {
            offertaAttualeTextView.setText("Offerta attuale: €" + asta.getOffertaAttuale().toString());
        } else {
            offertaAttualeTextView.setText("Prezzo iniziale: €" + asta.getPrezzoIniziale());
        }
        if(asta.getVincente() != null) {
            vincenteTextView.setText("Vincente: " + asta.getVincente());
        } else {
            vincenteTextView.setText("Nessuna offerta");
        }
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
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            date = inputFormat.parse(asta.getDataScadenzaTF());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date dataOraAttuale = calendar.getTime();
        dataScadenzaTextView.setText("Data di scadenza: " + outputFormat.format(date));


        inserisciOffertaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Questo metodo viene chiamato per notificare che qualcosa sta per cambiare nel testo.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Questo metodo viene chiamato per notificare che il testo è stato cambiato.
                // Qui puoi mettere il tuo codice per abilitare/disabilitare il bottone.
                if (charSequence.toString().isEmpty() || charSequence.length() > 15) {
                    presentaOffertaTFButton.setEnabled(false);
                    presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F4F8")));
                } else {
                    presentaOffertaTFButton.setEnabled(true);
                    presentaOffertaTFButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Questo metodo viene chiamato per notificare che il testo è stato cambiato in modo completo.
            }
        });

    }
}