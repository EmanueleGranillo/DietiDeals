package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAstaRibasso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_ribasso);


        Button backButtonAstaRibasso = findViewById(R.id.backButtonCreateAstaRibasso);
        backButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAstaPT1 = new Intent(CreateAstaRibasso.this, CreateAstaPT1.class);
                startActivity(goToCreateAstaPT1);
            }
        });


        Button createButtonAstaRibasso = findViewById(R.id.creaButtonAstaRibasso);
        createButtonAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePageVenditore = new Intent(CreateAstaRibasso.this, HomepageVenditoreActivity.class);
                startActivity(goToHomePageVenditore);
            }
        });


    }
}