package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.dietideals24.R;



public class CreaAstaRibassoActivity extends AppCompatActivity {

    NumberPicker numberPickerHours;
    NumberPicker numberPickerMinutes;
    TextView textViewTimerInsertedRibasso;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_ribasso);

        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        textViewTimerInsertedRibasso = findViewById(R.id.textViewTimerInsertedRibasso);

        nickname = getIntent().getStringExtra("nickname");

        // Aggiungi un listener ai NumberPicker per rilevare i cambiamenti
        numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateTimerText();
            }
        });

        numberPickerMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateTimerText();
            }
        });


        Button backButtonAstaRibasso = findViewById(R.id.backButtonCreateAstaRibasso);
        backButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaRibassoActivity.this, CreaAstaPT1Activity.class);
                goToCreateAstaPT1.putExtra("nickname", nickname);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaRibasso = findViewById(R.id.creaButtonAstaRibasso);
        createButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaRibassoActivity.this, HomepageVenditoreActivity.class);
                goToHomePageVenditore.putExtra("nickname", nickname);
                startActivity(goToHomePageVenditore);
            }
        });


    }

    // Funzione per aggiornare la TextView con le ore e i minuti selezionati
    private void updateTimerText() {
        int hours = numberPickerHours.getValue();
        int minutes = numberPickerMinutes.getValue();

        // Crea la stringa nel formato desiderato (puoi personalizzarla a tuo piacimento)
        String timerText = "Timer inserito: " + hours + " ore " + minutes + " minuti";

        // Aggiorna il testo della TextView
        textViewTimerInsertedRibasso.setText(timerText);
    }

}