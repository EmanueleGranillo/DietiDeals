package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfiloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        Button backBtn = findViewById(R.id.backButtonProfilo);
        Button logoutBtn = findViewById(R.id.logoutButton);
        TextView modificaTxt = findViewById(R.id.modificaTextView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(ProfiloActivity.this, HomepageCompratoreActivity.class);
                startActivity(backToHome);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(ProfiloActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });

        modificaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToModifica = new Intent(ProfiloActivity.this, ModificaProfiloActivity.class);
                startActivity(goToModifica);
            }
        });
    }
}