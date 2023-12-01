package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

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
    NumberPicker numberPickerHours;
    NumberPicker numberPickerMinutes;
    TextView textViewTimerInsertedRibasso;
    private String nickname;
    private String tipo;
    String timerInsertedString;
    long timerInSecondi;
    Date dataScadenza;
    String dataScadenzaString;
    private int statoAsta;
    private String titoloProdotto, base64Image, categoriaSelezionata, paroleChiave, descrizione, tipologiaSelezionata;
    private EditText editTextBaseAsta;
    private EditText editTextimportoDecremento;
    BigDecimal prezzoBaseAstaBD;
    BigDecimal importoDecrementoBD;
    BigDecimal prezzoInizialeBD;
    String prezzoBaseAsta;
    String importoDecremento;
    String prezzoIniziale;
    int tipologiaPosition, categoriaPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_ribasso);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        Button backButtonAstaRibasso = findViewById(R.id.backButtonCreateAstaRibasso);
        Button createButtonAstaRibasso = findViewById(R.id.creaButtonAstaRibasso);
        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        textViewTimerInsertedRibasso = findViewById(R.id.textViewTimerInsertedRibasso);
        editTextBaseAsta = findViewById(R.id.editTextPrezzoDiPartenza);
        editTextimportoDecremento = findViewById(R.id.editTextSogliaDecremento);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
        timerInsertedString = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));



        nickname = getIntent().getStringExtra("nickname");
        titoloProdotto = getIntent().getStringExtra("titoloProdotto");
        base64Image = getIntent().getStringExtra("imageBase64");
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



        backButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaRibassoActivity.this, CreaAstaPT1Activity.class);
                goToCreateAstaPT1.putExtra("nickname", nickname);
                goToCreateAstaPT1.putExtra("tipo", tipo);
                startActivity(goToCreateAstaPT1);
            }
        });



        createButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
                goToHomePageVenditore.putExtra("nickname", nickname);
                goToHomePageVenditore.putExtra("tipo", tipo);
                startActivity(goToHomePageVenditore);

                prezzoBaseAsta = editTextBaseAsta.getText().toString().trim();
                prezzoIniziale = prezzoBaseAsta;
                importoDecremento = editTextimportoDecremento.getText().toString().trim();
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
                System.out.println("titoloProdotto: " + titoloProdotto);
                System.out.println("tipologiaSelezionata: " + tipologiaSelezionata);
                System.out.println("descrizione: " + descrizione);
                System.out.println("base64Image: " + base64Image.length());
                System.out.println("categoriaSelezionata: " + categoriaSelezionata);
                System.out.println("paroleChiave: " + paroleChiave);
                System.out.println("statoAsta: " + statoAsta);
                System.out.println("dataScadenzaString: " + dataScadenzaString);
                System.out.println("prezzoInizialeBD: " + prezzoInizialeBD);
                System.out.println("prezzoBaseAstaBD: " + prezzoBaseAstaBD);
                System.out.println("importoDecrementoBD: " + importoDecrementoBD);
                System.out.println("nickname: " + nickname);
                System.out.println("timerInSecondi: " + timerInSecondi);
                performCreaAstaHttpRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, statoAsta, dataScadenzaString, prezzoInizialeBD, prezzoBaseAstaBD, importoDecrementoBD, nickname, timerInSecondi);
            }
        });
    }



    private void performCreaAstaHttpRequest(String titoloProdotto, String tipologiaSelezionata, String descrizione, String base64Image, String categoriaSelezionata, String paroleChiave, int statoAsta, String selectedDate, BigDecimal prezzoIniziale, BigDecimal prezzoBaseAsta, BigDecimal importoDecremento, String creatore, long timerInSecondi) {
        if(base64Image == null){
            base64Image = "";
        }
        CreateAstaRibassoRequest createAstaRequest = new CreateAstaRibassoRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, statoAsta, selectedDate, prezzoIniziale, prezzoBaseAsta, importoDecremento, creatore, timerInSecondi);
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


}