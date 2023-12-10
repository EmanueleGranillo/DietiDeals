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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
    private String nickname, tipo, imageString, tipologia = "asta a tempo fisso";
    private boolean check;
    private Date date;
    private Asta asta;
    private int id;
    private ImageView fotoProdottoImageView;
    private BigDecimal offerta;
    private ImageButton infoAstaTempoFissoButton;
    private PopupWindow popupWindow;
    private TextView popupText;

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
        infoAstaTempoFissoButton = findViewById(R.id.infoAstaTempoFissoButton);

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







        infoAstaTempoFissoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    showPopup();
                }


            }
        });

        incrementaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal x = new BigDecimal(inserisciOffertaEditText.getText().toString());
                x = x.add(BigDecimal.valueOf(1));
                inserisciOffertaEditText.setText(x.toString());
            }
        });

        decrementaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal x = new BigDecimal(inserisciOffertaEditText.getText().toString());
                x = x.subtract(BigDecimal.valueOf(1));
                inserisciOffertaEditText.setText(x.toString());
            }
        });

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

        vincenteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(asta.getVincente() != null) {
                    String checkActivity = "notmine";
                    Intent goToVincente = new Intent(AstaTempoFissoActivity.this, ProfiloActivity.class);
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
                    Intent goToVenditore = new Intent(AstaTempoFissoActivity.this, ProfiloActivity.class);
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
                            offerta(id, offerta, nickname);
                        }
                    } else {
                        if (offerta.compareTo(asta.getPrezzoIniziale()) < 0) {
                            inserisciOffertaErrorTextView.setText("Inserisci un'offerta più alta del prezzo iniziale");
                        } else {
                            offerta(id, offerta, nickname);
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
                        attivaTextView.setText("");
                        scadutaTextView.setText("SCADUTA");
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
                        attivaTextView.setText("ATTIVA");
                        scadutaTextView.setText("");
                    }

                    if(asta.getOffertaAttuale()==null){
                        inserisciOffertaEditText.setText(asta.getPrezzoIniziale().toString());
                    } else {
                        BigDecimal x = new BigDecimal(asta.getOffertaAttuale().toString());
                        x = x.add(BigDecimal.valueOf(1));
                        inserisciOffertaEditText.setText(x.toString());
                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<Asta> call, Throwable t) {

            }
        });
    }

    private void offerta(int id, BigDecimal x, String nickname) {
        OffertaTempoFissoRequest offertaTempoFissoRequest = new OffertaTempoFissoRequest(id, x, nickname);
        Call<NumeroResponse> call = apiService.offertaTempoFisso(offertaTempoFissoRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if(response.isSuccessful()){
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 1) {
                        aggiornaCard(id);
                        Toast.makeText(AstaTempoFissoActivity.this, "Offerta riuscita", Toast.LENGTH_SHORT).show();
                    } else {
                        aggiornaCard(id);
                        Toast.makeText(AstaTempoFissoActivity.this, "Offerta fallita", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {

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
        popupText.setText("Presenta un'offerta per partecipare all'asta. Alla scadenza il vincente si aggiudicherà il prodotto.");

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
        popupWindow.showAsDropDown(infoAstaTempoFissoButton, offsetX, offsetY);

    }
    @Override
    public void onBackPressed() {
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
}