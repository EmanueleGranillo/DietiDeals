package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.CreateAstaRibassoRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreaAstaRibassoActivity extends AppCompatActivity {
    private MyApiService apiService;
    private TextView timerInsertedErrorTextView, prezzoInizialeErrorTextView, decrementoErrorTextView, textViewTimerInsertedRibasso;
    private EditText baseAstaEditText, decrementoEditText;
    private Button backButton, createAstaRibassoButton;
    private NumberPicker numberPickerHours, numberPickerMinutes;
    private String activity = "crearibasso", titoloProdotto, tipologiaSelezionata, categoriaSelezionata, paroleChiave, nickname, tipo, base64Image, descrizione, timerInsertedString, dataScadenzaString, prezzoBaseAsta, importoDecremento, prezzoIniziale;
    private long timerInSecondi;
    private Date dataScadenza;
    private BigDecimal prezzoBaseAstaBD, importoDecrementoBD, prezzoInizialeBD;
    private int tipologiaPosition, categoriaPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_ribasso);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        backButton = findViewById(R.id.backButtonCreateAstaRibasso);
        createAstaRibassoButton = findViewById(R.id.creaButtonAstaRibasso);
        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        textViewTimerInsertedRibasso = findViewById(R.id.timerInsertedTextView);
        baseAstaEditText = findViewById(R.id.prezzoInizialeEditText);
        decrementoEditText = findViewById(R.id.decrementoEditText);
        timerInsertedErrorTextView = findViewById(R.id.timerInsertedErrorTextView);
        prezzoInizialeErrorTextView = findViewById(R.id.prezzoInizialeErrorTextView);
        decrementoErrorTextView = findViewById(R.id.decrementoErrorTextView);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
        timerInsertedString = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));


        // GET EXTRAS
        nickname = getIntent().getStringExtra("nickname");
        titoloProdotto = getIntent().getStringExtra("titoloProdotto");
        base64Image = getIntent().getStringExtra("imageBase64");
        Toast.makeText(CreaAstaRibassoActivity.this, base64Image, Toast.LENGTH_SHORT).show();
        categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
        paroleChiave = getIntent().getStringExtra("paroleChiave");
        descrizione = getIntent().getStringExtra("descrizione");
        tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
        tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", tipologiaPosition);
        categoriaPosition = getIntent().getIntExtra("categoriaPosition", categoriaPosition);


        // Aggiungi un listener ai NumberPicker per rilevare i cambiamenti
        numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                }
            }
        });
        numberPickerMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);
                    aggiornaDataScadenza((int) timerInSecondi);

                }
            }
        });



        // LISTENERS
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaRibassoActivity.this, CreaAstaPT1Activity.class);
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

        createAstaRibassoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerInsertedErrorTextView.setText("");
                prezzoInizialeErrorTextView.setText("");
                decrementoErrorTextView.setText("");
                if(check()){
                    creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, dataScadenzaString, prezzoInizialeBD, prezzoBaseAstaBD, importoDecrementoBD, nickname, timerInSecondi);
                    Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                }


                prezzoBaseAsta = baseAstaEditText.getText().toString().trim();
                prezzoIniziale = prezzoBaseAsta;
                importoDecremento = decrementoEditText.getText().toString().trim();
                // Verifica se il testo è valido come numero decimale
                if (!prezzoBaseAsta.isEmpty()) {
                    try {
                        prezzoBaseAstaBD = new BigDecimal(prezzoBaseAsta);
                    } catch (NumberFormatException e) {
                        // Il testo non è un numero decimale valido
                        e.printStackTrace(); // Tratta l'errore di conversione come necessario
                    }
                } else {
                    // Il campo prezzo di partenza è vuoto... Gestire
                }

                prezzoInizialeBD = prezzoBaseAstaBD;

                if (!importoDecremento.isEmpty()) {
                    try {
                        importoDecrementoBD = new BigDecimal(importoDecremento);
                    } catch (NumberFormatException e) {
                        // Il testo non è un numero decimale valido
                        e.printStackTrace(); // Tratta l'errore di conversione come necessario
                    }
                } else {
                    // Il campo prezzo di partenza è vuoto... Gestire
                }
            }
        });
    }



    private void creaAsta(String titoloProdotto, String tipologiaSelezionata, String descrizione, String base64Image, String categoriaSelezionata, String paroleChiave, String selectedDate, BigDecimal prezzoIniziale, BigDecimal prezzoBaseAsta, BigDecimal importoDecremento, String creatore, long timerInSecondi) {
        if(base64Image == null){
            base64Image = "";
        }
        CreateAstaRibassoRequest createAstaRequest = new CreateAstaRibassoRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, selectedDate, prezzoIniziale, prezzoBaseAsta, importoDecremento, creatore, timerInSecondi);
        Call<ResponseBody> call = apiService.createAstaRibasso(createAstaRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
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

    public long convertiStringaInSecondi(String tempoStringa) {
        SimpleDateFormat formatoTimer = new SimpleDateFormat("HH:mm:ss");

        try {
            Date orario = formatoTimer.parse(tempoStringa);
            return orario.getHours() * 3600 + orario.getMinutes() * 60 + orario.getSeconds();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiornaDataScadenza(int timerSeconds){
        Calendar calendar = Calendar.getInstance();
        Date dataOraAttuale = calendar.getTime();  //otteniamo data e ora attuale
        calendar.setTime(dataOraAttuale);
        // Aggiunta di 7200 secondi (2 ore)
        calendar.add(Calendar.SECOND, (int) timerSeconds);

        // Ottenere la nuova data (cioè la data di scadenza del timer)
        dataScadenza = calendar.getTime();

        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataScadenzaString = formatoData.format(dataScadenza);
    }

    public boolean check(){
        if(decrementoErrorTextView.
        return false;
    }
}