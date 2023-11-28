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
        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(24);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(60);
        textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
        nickname = getIntent().getStringExtra("nickname");

        // Aggiungi un listener ai NumberPicker per rilevare i cambiamenti
        numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() > 10) && (numberPickerMinutes.getValue() > 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() > 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() > 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                }
            }
        });

        numberPickerMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() > 10) && (numberPickerMinutes.getValue() > 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() < 10) && (numberPickerMinutes.getValue() > 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                } else if ((numberPickerHours.getValue() > 10) && (numberPickerMinutes.getValue() < 10)) {
                    textViewTimerInsertedRibasso.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHours.getValue(), numberPickerMinutes.getValue()));
                }
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


}