package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dietideals24.R;

public class InformazioniAstaActivity extends AppCompatActivity {

    private String nickname;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informazioni_asta);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        Button backBtn = findViewById(R.id.backButtonInfoAsta);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(InformazioniAstaActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                backToHome.putExtra("tipo", tipo);
                startActivity(backToHome);
            }
        });
    }
}