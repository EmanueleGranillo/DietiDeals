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
    private String nickname;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_inglese);

        Button backButtonAstaIng = findViewById(R.id.backButtonCreateAstaInglese);
        numberPickerHoursIng = findViewById(R.id.numberPickerHoursIng);
        numberPickerMinutesIng = findViewById(R.id.numberPickerMinutesIng);
        textViewTimerInsertedIng = findViewById(R.id.textViewTimerInsertedIng);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");


        numberPickerHoursIng.setMinValue(0);
        numberPickerHoursIng.setMaxValue(23);
        numberPickerMinutesIng.setMinValue(0);
        numberPickerMinutesIng.setMaxValue(59);
        textViewTimerInsertedIng.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));

        numberPickerHoursIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() > 10) && (numberPickerMinutesIng.getValue() > 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() > 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() > 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                }
            }
        });

        numberPickerMinutesIng.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     0%s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() > 10) && (numberPickerMinutesIng.getValue() > 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     %s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() < 10) && (numberPickerMinutesIng.getValue() > 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     0%s:%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                } else if ((numberPickerHoursIng.getValue() > 10) && (numberPickerMinutesIng.getValue() < 10)) {
                    textViewTimerInsertedIng.setText(String.format("Timer inserito:     %s:0%s:00", numberPickerHoursIng.getValue(), numberPickerMinutesIng.getValue()));
                }
            }
        });





        backButtonAstaIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaIngleseActivity.this, CreaAstaPT1Activity.class);
                goToCreateAstaPT1.putExtra("tipo", tipo);
                goToCreateAstaPT1.putExtra("nickname", nickname);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaInglese = findViewById(R.id.creaButtonAstaIng);
        createButtonAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaIngleseActivity.this, HomepageVenditoreActivity.class);
                goToHomePageVenditore.putExtra("nickname", nickname);
                goToHomePageVenditore.putExtra("tipo", tipo);
                startActivity(goToHomePageVenditore);
            }
        });

    }



}