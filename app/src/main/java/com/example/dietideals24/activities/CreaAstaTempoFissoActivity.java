package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dietideals24.R;

public class CreaAstaTempoFissoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_tf);


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