package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button backBtn = findViewById(R.id.backButtonRegister);
        Button registratiBtn = findViewById(R.id.registratiButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });

        registratiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });
    }
}