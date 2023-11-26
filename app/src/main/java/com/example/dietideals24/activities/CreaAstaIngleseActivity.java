package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dietideals24.R;

public class CreaAstaIngleseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_inglese);

        Button backButtonAstaIng = findViewById(R.id.backButtonCreateAstaInglese);
        backButtonAstaIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreaAstaIngleseActivity.this, CreaAstaPT1Activity.class);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaInglese = findViewById(R.id.creaButtonAstaInglese);
        createButtonAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreaAstaIngleseActivity.this, HomepageVenditoreActivity.class);
                startActivity(goToHomePageVenditore);
            }
        });

    }
}