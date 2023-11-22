package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button compraBtn;
    Button vendiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView registratiTxt = findViewById(R.id.registratiTextButton);
        Button accediBtn = findViewById(R.id.accediButton);
        compraBtn = findViewById(R.id.compraButtonLogin);
        vendiBtn = findViewById(R.id.vendiButtonLogin);
        registratiTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(goToRegister);
            }
        });

        accediBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilo = new Intent(MainActivity.this, HomepageCompratoreActivity.class);
                startActivity(goToProfilo);
            }
        });

        vendiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendiBtn.setBackgroundColor(Color.parseColor("#00CC66"));
                compraBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                vendiBtn.setTextColor(Color.parseColor("#FFFFFF"));
                compraBtn.setTextColor(Color.parseColor("#000000"));
            }
        });

        compraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compraBtn.setBackgroundColor(Color.parseColor("#00CC66"));
                vendiBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                compraBtn.setTextColor(Color.parseColor("#FFFFFF"));
                vendiBtn.setTextColor(Color.parseColor("#000000"));
            }
        });
    }
}