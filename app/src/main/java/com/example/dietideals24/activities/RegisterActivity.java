package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.R;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.UserRegistrationRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private MyApiService apiService;
    EditText emailEditText;
    EditText nicknameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inizializza Retrofit
        apiService = RetrofitClient.getInstance().create(MyApiService.class);


        Button backBtn = findViewById(R.id.backButtonRegister);
        Button registratiBtn = findViewById(R.id.registratiButton);
        Button compraBtn = findViewById(R.id.compraButtonRegister);
        Button vendiBtn = findViewById(R.id.vendiButtonRegister);
        emailEditText = findViewById(R.id.emailEditText);
        nicknameEditText = findViewById(R.id.nicknameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });

        compraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendiBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C2C2C2")));
                compraBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
        });

        vendiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compraBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C2C2C2")));
                vendiBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
        });

        registratiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo;
                if (compraBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#FFFFFF")){
                    tipo = "compratore";
                } else {
                    tipo = "venditore";
                }
                performHttpPostRequest(nicknameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
            }
        });

    }

    private void performHttpPostRequest(String nickname, String email, String password, String tipo) {
        // Crea un oggetto di richiesta con i dati dell'utente
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest(nickname, email, password, tipo);

        // Esegui la chiamata Retrofit
        Call<ResponseBody> call = apiService.saveUser(registrationRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent backToLogin = new Intent(RegisterActivity.this, HomepageCompratoreActivity.class);
                    startActivity(backToLogin);
                } else {
                    System.out.println("richiesta fallita");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

}