package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.dietideals24.R;

public class CreaAstaIngleseActivity extends AppCompatActivity {

    NumberPicker numberPickerHoursIng;
    NumberPicker numberPickerMinutesIng;
    TextView textViewTimerInsertedIng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_inglese);


        numberPickerHoursIng = findViewById(R.id.numberPickerHoursIng);
        numberPickerMinutesIng = findViewById(R.id.numberPickerMinutesIng);
        textViewTimerInsertedIng = findViewById(R.id.textViewTimerInsertedIng);

        // Aggiungi un listener ai NumberPicker per rilevare i cambiamenti
        numberPickerHoursIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateTimerText();
            }
        });

        numberPickerMinutesIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateTimerText();
            }
        });


        Button backButtonAstaIng = findViewById(R.id.backButtonCreateAstaInglese);
        backButtonAstaIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaIngleseActivity.this, CreaAstaPT1Activity.class);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaInglese = findViewById(R.id.creaButtonAstaIng);
        createButtonAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaIngleseActivity.this, HomepageVenditoreActivity.class);
                startActivity(goToHomePageVenditore);
            }
        });

    }



    // Funzione per aggiornare la TextView con le ore e i minuti selezionati
    private void updateTimerText() {
        int hours = numberPickerHoursIng.getValue();
        int minutes = numberPickerMinutesIng.getValue();

        // Crea la stringa nel formato desiderato (puoi personalizzarla a tuo piacimento)
        String timerText = "Timer inserito: " + hours + " ore " + minutes + " minuti";

        // Aggiorna il testo della TextView
        textViewTimerInsertedIng.setText(timerText);
    }

}