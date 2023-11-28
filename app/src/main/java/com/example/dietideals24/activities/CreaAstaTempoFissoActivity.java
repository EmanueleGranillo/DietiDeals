package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.dietideals24.R;

import java.util.Calendar;
import java.util.Locale;

public class CreaAstaTempoFissoActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TextView textViewSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_tf);

        DatePicker datePicker = findViewById(R.id.datePicker);

        // Imposta il limite inferiore del DatePicker alla data corrente
        Calendar calendar = Calendar.getInstance();
        datePicker.setMinDate(calendar.getTimeInMillis());

        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);

        // Aggiungi un listener per gestire la selezione della data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
                // Formatta la data selezionata e impostala nel TextView
                String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                textViewSelectedDate.setText("Data selezionata: " + selectedDate);
            });
        }


        Button backButtonAstaTF = findViewById(R.id.backButtonCreateAstaTF);
        backButtonAstaTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaTempoFissoActivity.this, CreaAstaPT1Activity.class);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaTF = findViewById(R.id.creaButtonAstaTF);
        createButtonAstaTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaTempoFissoActivity.this, HomepageVenditoreActivity.class);
                startActivity(goToHomePageVenditore);
            }
        });


    }
}