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
import com.example.dietideals24.connection.CreateAstaIngRequest;
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

public class CreaAstaIngleseActivity extends AppCompatActivity {

    private MyApiService apiService;
    private String activity = "creainglese", titoloProdotto, tipologiaSelezionata, categoriaSelezionata, paroleChiave, nickname, tipo, imageString, descrizione, dataScadenzaString = "", timerInserted;
    private TextView timerInsertedTextView, timerInsertedErrorTextView, baseAstaErrorTextView, sogliaRialzoErrorTextView;
    private EditText baseAstaEditText, sogliaRialzoMinimaEditText;
    NumberPicker numberPickerHoursIng, numberPickerMinutesIng;
    long timerInSecondi;
    private Date dataScadenza;
    private int tipologiaPosition, categoriaPosition;
    private Button creaAstaIngleseButton, backButton;
    private BigDecimal sogliaRialzoMinimaBigD, prezzoInizialeBigD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_inglese);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);


        backButton = findViewById(R.id.backButtonCreateAstaInglese);
        creaAstaIngleseButton = findViewById(R.id.creaButtonAstaIng);
        numberPickerHoursIng = findViewById(R.id.numberPickerHoursIng);
        numberPickerMinutesIng = findViewById(R.id.numberPickerMinutesIng);
        timerInsertedTextView = findViewById(R.id.timerInsertedTextView);
        baseAstaEditText = findViewById(R.id.baseAstaEditText);
        sogliaRialzoMinimaEditText = findViewById(R.id.sogliaRialzoEditText);
        timerInsertedErrorTextView = findViewById(R.id.timerErrorTextView);
        baseAstaErrorTextView = findViewById(R.id.baseAstaErrorTextView);
        sogliaRialzoErrorTextView = findViewById(R.id.sogliaRialzoErrorTextView);


        // GET EXTRAS
        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        titoloProdotto = getIntent().getStringExtra("titoloProdotto");
        imageString = getIntent().getStringExtra("imageString");
        paroleChiave = getIntent().getStringExtra("paroleChiave");
        descrizione = getIntent().getStringExtra("descrizione");
        tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
        tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", tipologiaPosition);
        categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
        categoriaPosition = getIntent().getIntExtra("categoriaPosition", categoriaPosition);
        sogliaRialzoMinimaBigD = new BigDecimal(10);

        // Gestione timer
        numberPickerHoursIng.setMinValue(0);
        numberPickerHoursIng.setMaxValue(23);
        numberPickerMinutesIng.setMinValue(0);
        numberPickerMinutesIng.setMaxValue(59);
        numberPickerHoursIng.setValue(1);
        timerInsertedTextView.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
        timerInserted = (String.format("0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
        timerInSecondi = convertiStringaInSecondi(timerInserted);
        aggiornaDataScadenza((int) timerInSecondi);

        numberPickerHoursIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() >= 10) && (numberPickerMinutesIng.getValue() >= 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() >= 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() >= 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);
                }
            }
        });

        numberPickerMinutesIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() >= 10) && (numberPickerMinutesIng.getValue() >= 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() >= 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);

                } else if ((numberPickerHoursIng.getValue() >= 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    timerInsertedTextView.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInserted = (String.format("%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                    timerInSecondi = convertiStringaInSecondi(timerInserted);
                    aggiornaDataScadenza((int) timerInSecondi);
                }
            }
        });


        // LISTENERS

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaIngleseActivity.this, CreaAstaPT1Activity.class);
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

        creaAstaIngleseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseAstaErrorTextView.setText("");
                sogliaRialzoErrorTextView.setText("");
                timerInsertedErrorTextView.setText("");
                if(check()){
                    creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, dataScadenzaString, prezzoInizialeBigD, sogliaRialzoMinimaBigD, nickname, timerInSecondi);
                    Intent goToHomePageVenditore = new Intent(CreaAstaIngleseActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                }
            }
        });

    }




    private void creaAsta(String titoloProdotto, String tipologiaSelezionata, String descrizione, String imageString, String categoriaSelezionata, String paroleChiave, String selectedDate, BigDecimal prezzoIniziale, BigDecimal sogliaSegreta, String creatore, long timerInSecondi) {
        if(imageString == null){
            imageString = "";
        }
        CreateAstaIngRequest createAstaRequest = new CreateAstaIngRequest(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, selectedDate, prezzoIniziale, sogliaSegreta, creatore, timerInSecondi);
        Call<ResponseBody> call = apiService.createAstaIng(createAstaRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent goToHomePageVenditore = new Intent(CreaAstaIngleseActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                } else {
                    Toast.makeText(CreaAstaIngleseActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreaAstaIngleseActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean check() {
        //INSERIRE UN if SUL TIMER
        if(dataScadenzaString.isEmpty()){
            timerInsertedErrorTextView.setText("Inserire la data di scadenza!");
            return false;
        }
        if(baseAstaEditText.getText().toString().isEmpty()){
            baseAstaErrorTextView.setText("Inserire la base d'asta!");
            return false;
        } else {
            try {
                prezzoInizialeBigD = new BigDecimal(baseAstaEditText.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Il testo non è un numero decimale valido
                e.printStackTrace(); // Tratta l'errore di conversione come necessario
            }
        }
        if(baseAstaEditText.getText().toString().length() > 15){
            baseAstaErrorTextView.setText("Inserire una base d'asta più piccola!");
            return false;
        }
        //INSERIRE CONTROLLO SU OFFERTA ATTUALE IN FUTURO
        //INSERIRE UN POPUP INFO DI OGNI ASTA
        if(!(sogliaRialzoMinimaEditText.getText().toString().isEmpty()) && (sogliaRialzoMinimaEditText.getText().toString().length() > 15)){
            sogliaRialzoErrorTextView.setText("Inserire una soglia di rialzo più bassa");
            return false;
        } else if(!(sogliaRialzoMinimaEditText.getText().toString().isEmpty())){
            try {
                sogliaRialzoMinimaBigD = new BigDecimal(sogliaRialzoMinimaEditText.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Il testo non è un numero decimale valido
                e.printStackTrace(); // Tratta l'errore di conversione come necessario
            }
        }

        return true;
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

        // DA AGGIUSTARE
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataScadenzaString = formatoData.format(dataScadenza);
    }

    @Override
    public void onBackPressed() {
        Intent goToCreateAstaPT1 = new Intent(CreaAstaIngleseActivity.this, CreaAstaPT1Activity.class);
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