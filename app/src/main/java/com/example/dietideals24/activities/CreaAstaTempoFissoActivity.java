package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.CreateAstaRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreaAstaTempoFissoActivity extends AppCompatActivity {

    private String activity = "creatempofisso";
    private MyApiService apiService;
    private DatePicker datePicker;
    private TextView textViewSelectedDate;
    private String titoloProdotto;
    private String tipologiaSelezionata;
    private String categoriaSelezionata;
    private String paroleChiave;
    private String nickname;
    private String tipo;
    private String base64Image;
    private String descrizione;
    private String selectedDate;    //anno-mese-giorno (2024-05-24)
    private EditText editTextInitialPrice;
    private EditText editTextSogliaMinima;
    private int statoAsta;
    private int tipologiaPosition;
    private int categoriaPosition;

    // private Date date;
    String prezzoIniziale;
    String offertaAttuale;
    String sogliaMinimaSegreta;
    BigDecimal prezzoInizialeBD;
    BigDecimal offertaAttualeBD;
    BigDecimal sogliaMinimaSegretaBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_tf);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        Button createButtonAstaTF = findViewById(R.id.creaButtonAstaTF);
        Button backButtonAstaTF = findViewById(R.id.backButtonCreateAstaTF);
        // Inizializza Retrofit
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        DatePicker datePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();     // Imposta il limite inferiore del DatePicker alla data corrente
        datePicker.setMinDate(calendar.getTimeInMillis());
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        editTextInitialPrice = findViewById(R.id.editTextPrice);
        editTextSogliaMinima = findViewById(R.id.editTextSogliaMinima);


        nickname = getIntent().getStringExtra("nickname");
        titoloProdotto = getIntent().getStringExtra("titoloProdotto");
        base64Image = getIntent().getStringExtra("imageBase64");
        categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
        paroleChiave = getIntent().getStringExtra("paroleChiave");
        descrizione = getIntent().getStringExtra("descrizione");
        tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
        tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", tipologiaPosition);
        categoriaPosition = getIntent().getIntExtra("categoriaPosition", categoriaPosition);

        // Aggiungi un listener per gestire la selezione della data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Imposta il DatePicker con gli spinner nell'ordine desiderato (anno, mese, giorno)
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                // Formatta la data selezionata e impostala nel TextView
                selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                textViewSelectedDate.setText("Data selezionata: " + selectedDate);
            });
        }



        backButtonAstaTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaTempoFissoActivity.this, CreaAstaPT1Activity.class);
                goToCreateAstaPT1.putExtra("activity", activity);
                goToCreateAstaPT1.putExtra("nickname", nickname);
                goToCreateAstaPT1.putExtra("tipo", tipo);
                goToCreateAstaPT1.putExtra("base64Image", base64Image);
                goToCreateAstaPT1.putExtra("titoloProdotto", titoloProdotto);
                goToCreateAstaPT1.putExtra("descrizione", descrizione);
                goToCreateAstaPT1.putExtra("tipologiaSelezionata", tipologiaSelezionata);
                goToCreateAstaPT1.putExtra("tipologiaPosition", tipologiaPosition);
                goToCreateAstaPT1.putExtra("categoriaSelezionata", categoriaSelezionata);
                goToCreateAstaPT1.putExtra("categoriaPosition", categoriaPosition);
                goToCreateAstaPT1.putExtra("paroleChiave", paroleChiave);
                startActivity(goToCreateAstaPT1);
            }
        });


        createButtonAstaTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaTempoFissoActivity.this, HomepageVenditoreActivity.class);
                goToHomePageVenditore.putExtra("nickname", nickname);
                goToHomePageVenditore.putExtra("tipo", tipo);
                startActivity(goToHomePageVenditore);
                prezzoIniziale = editTextInitialPrice.getText().toString().trim();
                offertaAttuale = prezzoIniziale;
                sogliaMinimaSegreta = editTextSogliaMinima.getText().toString().trim();
                // Verifica se il testo è valido come numero decimale
                if (!prezzoIniziale.isEmpty()) {
                    try {
                        prezzoInizialeBD = new BigDecimal(prezzoIniziale);
                    } catch (NumberFormatException e) {
                        // Il testo non è un numero decimale valido
                        e.printStackTrace(); // Tratta l'errore di conversione come necessario
                    }
                } else {
                    // Il campo prezzo di partenza è vuoto... Gestire
                }

                offertaAttualeBD = prezzoInizialeBD;

                if (!sogliaMinimaSegreta.isEmpty()) {
                    try {
                        sogliaMinimaSegretaBD = new BigDecimal(sogliaMinimaSegreta);
                    } catch (NumberFormatException e) {
                        // Il testo non è un numero decimale valido
                        e.printStackTrace(); // Tratta l'errore di conversione come necessario
                    }
                } else {
                    // Il campo prezzo di partenza è vuoto... Gestire
                }
                performCreaAstaHttpRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, statoAsta, selectedDate, prezzoInizialeBD, offertaAttualeBD, sogliaMinimaSegretaBD, nickname);
            }
        });
    }

    private void performCreaAstaHttpRequest(String titoloProdotto, String tipologiaSelezionata, String descrizione, String base64Image, String categoriaSelezionata, String paroleChiave, int statoAsta, String selectedDate, BigDecimal prezzoIniziale, BigDecimal offertaAttuale, BigDecimal sogliaSegreta, String creatore) {
        if(base64Image.isEmpty()){
            base64Image = "";
        }
        CreateAstaRequest createAstaRequest = new CreateAstaRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, statoAsta, selectedDate, prezzoIniziale, offertaAttuale, sogliaSegreta, creatore);
        Call<ResponseBody> call = apiService.createAstatf(createAstaRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent goToHomePageVenditore = new Intent(CreaAstaTempoFissoActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    startActivity(goToHomePageVenditore);
                } else {
                    System.out.println("Richiesta fallita");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


}