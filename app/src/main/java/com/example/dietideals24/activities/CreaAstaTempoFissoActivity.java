package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.CreateAstaTFRequest;
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

public class CreaAstaTempoFissoActivity extends AppCompatActivity {

    private MyApiService apiService;
    private DatePicker datePicker;
    private NumberPicker numberPickerHours, numberPickerMinutes;
    private String activity = "creatempofisso", titoloProdotto, tipologiaSelezionata, categoriaSelezionata, paroleChiave, nickname, tipo, imageString, descrizione, selectedDate = "", selectedTime = "";
    private TextView textViewSelectedDate, selezionaDataErrorTextView, prezzoErrorTextView, sogliaMinimaErrorTextView;
    private EditText prezzoInizialeEditText, sogliaMinimaEditText;
    private int tipologiaPosition, categoriaPosition;
    private long timerInSecondi;
    private Button creaAstaTFButton, backButton;
    private BigDecimal prezzoInizialeBD, sogliaMinimaSegretaBD;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_tf);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        creaAstaTFButton = findViewById(R.id.creaAstaTFButton);
        backButton = findViewById(R.id.backButtonCreateAstaTF);
        datePicker = findViewById(R.id.datePicker);
        numberPickerHours = findViewById(R.id.numberPickerHoursTF);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutesTF);
        calendar = Calendar.getInstance();     // Imposta il limite inferiore del DatePicker alla data corrente
        datePicker.setMinDate(calendar.getTimeInMillis());
        textViewSelectedDate = findViewById(R.id.selezionaDataTextView);
        prezzoInizialeEditText = findViewById(R.id.prezzoEditText);
        sogliaMinimaEditText = findViewById(R.id.sogliaMinimaEditText);
        selezionaDataErrorTextView = findViewById(R.id.selectedDateErrorTextView);
        prezzoErrorTextView = findViewById(R.id.prezzoErrorTextView);
        sogliaMinimaErrorTextView = findViewById(R.id.sogliaMinimaErrorTextView);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);

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


        // Aggiungi un listener per gestire la selezione della data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Imposta il DatePicker con gli spinner nell'ordine desiderato (anno, mese, giorno)
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                // Formatta la data selezionata e impostala nel TextView
                selectedDate = String.format(Locale.getDefault(), "%d/%02d/%02d", year, monthOfYear + 1, dayOfMonth);
                textViewSelectedDate.setText("Data selezionata: " + selectedDate + " - " + selectedTime);
            });
        }

        numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " 0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " 0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                }
            }
        });
        numberPickerMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " 0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() >= 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " 0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                } else if ((numberPickerHours.getValue() >= 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewSelectedDate.setText(String.format("Data selezionata: " + selectedDate + " %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    selectedTime = (String.format("%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                    timerInSecondi = convertiStringaInSecondi(selectedTime);

                }
            }
        });

        // LISTENERS
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaTempoFissoActivity.this, CreaAstaPT1Activity.class);
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


        creaAstaTFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prezzoErrorTextView.setText("");
                sogliaMinimaErrorTextView.setText("");
                selezionaDataErrorTextView.setText("");
                if (check()) {
                    creaAsta(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, selectedDate, prezzoInizialeBD, sogliaMinimaSegretaBD, nickname);
                }
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

    public boolean check() {
        //Controllo campi vuoti
        if (selectedTime.isEmpty()) {
            selectedTime = "00:00:00";
        }
        long secondiOggi = calendar.getTime().getHours() * 3600 + calendar.getTime().getMinutes() * 60 + calendar.getTime().getSeconds();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if ((timerInSecondi < secondiOggi) && (selectedDate.equals(dateFormat.format(calendar.getTime())))){
            selezionaDataErrorTextView.setText("Devi selezionare una data futura!");
            return false;
        }
        if (selectedDate.isEmpty()) {
            selezionaDataErrorTextView.setText("Devi selezionare una data!");
            return false;
        }
        if (prezzoInizialeEditText.getText().toString().isEmpty()) {
            prezzoErrorTextView.setText("Devi inserire il prezzo iniziale!");
            return false;
        } else {
            try {
                prezzoInizialeBD = new BigDecimal(prezzoInizialeEditText.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (sogliaMinimaEditText.getText().toString().isEmpty()) {
            sogliaMinimaErrorTextView.setText("Devi inserire una soglia minima!");
            return false;
        } else {
            try {
                sogliaMinimaSegretaBD = new BigDecimal(sogliaMinimaEditText.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        //Controllo lunghezza campi
        if (prezzoInizialeEditText.getText().toString().length() > 15) {
            prezzoErrorTextView.setText("Inserisci un prezzo più piccolo!");
            return false;
        }
        if (sogliaMinimaEditText.getText().toString().length() > 15) {
            sogliaMinimaErrorTextView.setText("Inserisci una soglia più bassa!");
            return false;
        }
        if (sogliaMinimaSegretaBD.compareTo(prezzoInizialeBD) < 0) {
            sogliaMinimaErrorTextView.setText("La soglia minima deve essere maggiore del prezzo iniziale!");
            return false;
        }
        return true;
    }

    private void creaAsta(String titoloProdotto, String tipologiaSelezionata, String descrizione, String imageString, String categoriaSelezionata, String paroleChiave, String selectedDate, BigDecimal prezzoIniziale, BigDecimal sogliaSegreta, String creatore) {
        if(imageString == null) {
            imageString = "";
        }
        String date = selectedDate + " " + selectedTime;
        System.out.println(date);
        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
        CreateAstaTFRequest createAstaRequest = new CreateAstaTFRequest(titoloProdotto, tipologiaSelezionata, descrizione, imageString, categoriaSelezionata, paroleChiave, date, prezzoIniziale, sogliaSegreta, creatore);
        Call<ResponseBody> call = apiService.createAstatf(createAstaRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent goToHomePageVenditore = new Intent(CreaAstaTempoFissoActivity.this, HomepageVenditoreActivity.class);
                    goToHomePageVenditore.putExtra("nickname", nickname);
                    goToHomePageVenditore.putExtra("tipo", tipo);
                    startActivity(goToHomePageVenditore);
                } else {
                    Toast.makeText(CreaAstaTempoFissoActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreaAstaTempoFissoActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goToCreateAstaPT1 = new Intent(CreaAstaTempoFissoActivity.this, CreaAstaPT1Activity.class);
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
