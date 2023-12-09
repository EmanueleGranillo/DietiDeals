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
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreaAstaRibassoActivity extends AppCompatActivity {

    private MyApiService apiService;
    private TextView timerInsertedErrorTextView, prezzoInizialeErrorTextView, decrementoErrorTextView, sogliaMinimaErrorTextView, textViewTimerInsertedRibasso;
    private EditText prezzoInizialeEditText, decrementoEditText, sogliaMinimaEditText;
    private Button backButton, createAstaRibassoButton;
    private NumberPicker numberPickerHours, numberPickerMinutes;
    private String activity = "crearibasso", titoloProdotto, tipologiaSelezionata, categoriaSelezionata, paroleChiave, nickname, tipo, imageString, descrizione, timerInsertedString, dataScadenzaString;
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
        prezzoInizialeEditText = findViewById(R.id.prezzoInizialeEditText);
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



        // GET EXTRAS
        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        titoloProdotto = getIntent().getStringExtra("titoloProdotto");
        imageString = getIntent().getStringExtra("imageString");
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

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

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

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInsertedString = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInsertedString);

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
                goToCreateAstaPT1.putExtra("imageString", imageString);
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


                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY, -1);  // Sottrai un'ora
                Date dataOraAttuale = calendar.getTime();   // Ora attuale meno un'ora

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                String dataOraAttualeString = inputFormat.format(dataOraAttuale);

                aggiornaDataScadenza((int) timerInSecondi);

                timerInsertedErrorTextView.setText("");
                prezzoInizialeErrorTextView.setText("");
                decrementoErrorTextView.setText("");
                sogliaMinimaErrorTextView.setText("");
                if(check()){
                    //creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, dataScadenzaString, prezzoInizialeBD, importoDecrementoBD, sogliaMinimaBD, nickname, timerInSecondi);
                    creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, dataOraAttualeString, prezzoInizialeBD, importoDecrementoBD, sogliaMinimaBD, nickname, timerInSecondi);
                    Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                }
            }
        });
    }



    private void creaAsta(String titoloProdotto, String tipologiaSelezionata, String descrizione, String imageString, String categoriaSelezionata, String paroleChiave, String selectedDate, BigDecimal prezzoIniziale, BigDecimal importoDecremento, BigDecimal sogliaMinimaBD,String creatore, long timerInSecondi) {
        if(imageString == null){
            imageString = "";
        }
        CreateAstaRibassoRequest createAstaRequest = new CreateAstaRibassoRequest(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, selectedDate, prezzoIniziale, importoDecremento, sogliaMinimaBD, creatore, timerInSecondi);
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
        // Aggiunta di tot secondi (timerSeconds ore)
        calendar.add(Calendar.SECOND, (int) timerSeconds);

        // Ottenere la nuova data (cioè la data di scadenza del timer)
        dataScadenza = calendar.getTime();

        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataScadenzaString = formatoData.format(dataScadenza);
    }

    public boolean check(){
        // Controllo campi vuoti
        if(prezzoInizialeEditText.getText().toString().isEmpty()){
            prezzoInizialeErrorTextView.setText("Inserire il prezzo iniziale");
            return false;
        } else {
            try {
                prezzoInizialeBD = new BigDecimal(prezzoInizialeEditText.getText().toString());
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
        if(prezzoInizialeEditText.getText().toString().length() > 15) {
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

    @Override
    public void onBackPressed() {
        Intent goToCreateAstaPT1 = new Intent(CreaAstaRibassoActivity.this, CreaAstaPT1Activity.class);
        goToCreateAstaPT1.putExtra("activity", activity);
        goToCreateAstaPT1.putExtra("nickname", nickname);
        goToCreateAstaPT1.putExtra("tipo", tipo);
        goToCreateAstaPT1.putExtra("imageString", imageString);
        goToCreateAstaPT1.putExtra("titoloProdotto", titoloProdotto);
        goToCreateAstaPT1.putExtra("descrizione", descrizione);
        goToCreateAstaPT1.putExtra("tipologiaSelezionata", tipologiaSelezionata);
        goToCreateAstaPT1.putExtra("tipologiaPosition", tipologiaPosition);
        goToCreateAstaPT1.putExtra("categoriaSelezionata", categoriaSelezionata);
        goToCreateAstaPT1.putExtra("categoriaPosition", categoriaPosition);
        goToCreateAstaPT1.putExtra("paroleChiave", paroleChiave);
        startActivity(goToCreateAstaPT1);
        super.onBackPressed();
    }
}