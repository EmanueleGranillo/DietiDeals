package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModificaProfiloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaprofilo);

        Button annullaBtn = findViewById(R.id.annullaButtonModificaProfilo);
        Button confermaBtn = findViewById(R.id.confermaButtonModificaProfilo);
        annullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                startActivity(backToProfilo);
            }
        });

        confermaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                startActivity(backToProfilo);
            }
        });
    }
}
