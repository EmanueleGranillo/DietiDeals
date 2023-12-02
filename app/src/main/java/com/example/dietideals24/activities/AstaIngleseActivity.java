package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.dietideals24.R;

public class AstaIngleseActivity extends AppCompatActivity {

    private String nickname, tipo;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_inglese);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        Button backBtn = findViewById(R.id.backButtonInfoAsta);



        // GESTIONE TIMER
        chronometer = findViewById(R.id.scadenzaChronometer);
        long elapsedTime = SystemClock.elapsedRealtime() + 30000;
        chronometer.setBase(elapsedTime);
        chronometer.start();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(chronometer.getBase() < SystemClock.elapsedRealtime() + 10000) {
                    chronometer.setTextColor(Color.RED);
                }
                if(chronometer.getBase() == SystemClock.elapsedRealtime()) {
                    chronometer.stop();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(AstaIngleseActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                backToHome.putExtra("tipo", tipo);
                startActivity(backToHome);
            }
        });
    }
}