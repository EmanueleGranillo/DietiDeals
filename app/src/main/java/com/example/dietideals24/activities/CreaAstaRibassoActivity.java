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
    private TextView timerInsertedErrorTextView, prezzoInizialeErrorTextView, decrementoErrorTextView, sogliaMinimaErrorTextView, textViewTimerInsertedRibasso;
    private EditText baseAstaEditText, decrementoEditText, sogliaMinimaEditText;
    private Button backButton, createAstaRibassoButton;
    private NumberPicker numberPickerHours, numberPickerMinutes;
    private String activity = "crearibasso", titoloProdotto, tipologiaSelezionata, categoriaSelezionata, paroleChiave, nickname, tipo, base64Image, descrizione, timerInsertedString, dataScadenzaString;
    private long timerInSecondi;
    private Date dataScadenza;
    private BigDecimal prezzoInizialeBD, importoDecrementoBD, sogliaMinimaBD;
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
        sogliaMinimaEditText = findViewById(R.id.sogliaMinimaEditText);
        timerInsertedErrorTextView = findViewById(R.id.timerInsertedErrorTextView);
        prezzoInizialeErrorTextView = findViewById(R.id.prezzoInizialeErrorTextView);
        decrementoErrorTextView = findViewById(R.id.decrementoErrorTextView);
        sogliaMinimaErrorTextView = findViewById(R.id.sogliaMinimaErrorTextView);


        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        numberPickerHours.setValue(1);
        textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
        timerInsertedString = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
        timerInSecondi = convertiStringaInSecondi(timerInsertedString);
        aggiornaDataScadenza((int) timerInSecondi);


        // GET EXTRAS
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
                sogliaMinimaErrorTextView.setText("");
                if(check()){
                    creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, dataScadenzaString, prezzoInizialeBD, importoDecrementoBD, nickname, timerInSecondi);
                }
            }
        });
    }



    private void creaAsta(String titoloProdotto, String tipologiaSelezionata, String descrizione, String base64Image, String categoriaSelezionata, String paroleChiave, String selectedDate, BigDecimal prezzoIniziale, BigDecimal importoDecremento, String creatore, long timerInSecondi) {
        if(base64Image == null){
            base64Image = "";
        }
        CreateAstaRibassoRequest createAstaRequest = new CreateAstaRibassoRequest(titoloProdotto, tipologiaSelezionata, descrizione, base64Image, categoriaSelezionata, paroleChiave, selectedDate, prezzoIniziale, importoDecremento, creatore, timerInSecondi);
        Call<ResponseBody> call = apiService.createAstaRibasso(createAstaRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                } else {
                    Toast.makeText(CreaAstaRibassoActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreaAstaRibassoActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
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
        // Controllo campi vuoti
        /*if(timerInsertedString.isEmpty()){
            // timerInsertedString =       da impostare di default 1 ora
        }*/
        if(baseAstaEditText.getText().toString().isEmpty()){
            prezzoInizialeErrorTextView.setText("Inserire il prezzo iniziale");
            return false;
        } else {
            try {
                prezzoInizialeBD = new BigDecimal(baseAstaEditText.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(decrementoEditText.getText().toString().isEmpty()){
            decrementoErrorTextView.setText("Inserisci l'importo di decremento");
            return false;
        } else {
            try {
                importoDecrementoBD = new BigDecimal(decrementoEditText.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(sogliaMinimaEditText.getText().toString().isEmpty()){
            sogliaMinimaErrorTextView.setText("Inserire una soglia minima");
            return false;
        } else {
            try {
                sogliaMinimaBD = new BigDecimal(sogliaMinimaEditText.getText().toString());
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        // Controllo lunghezza
        if(baseAstaEditText.getText().toString().length() > 15) {
            prezzoInizialeErrorTextView.setText("Inserisci un prezzo minore");
            return false;
        }
        if(decrementoEditText.getText().toString().length() > 15) {
            decrementoErrorTextView.setText("Inserisci un importo minore");
            return false;
        }
        if(sogliaMinimaEditText.getText().toString().length() > 15) {
            sogliaMinimaErrorTextView.setText("Inserisci una soglia minima più bassa");
            return false;
        }
        // Controllo correttezza
        if(prezzoInizialeBD.compareTo(importoDecrementoBD) < 0) {
            decrementoErrorTextView.setText("L'importo deve essere minore del prezzo iniziale");
            return false;
        }
        if(prezzoInizialeBD.compareTo(sogliaMinimaBD) < 0){
            sogliaMinimaErrorTextView.setText("La soglia minima non può essere maggiore del prezzo iniziale");
            return false;
        }
        return true;
    }
}