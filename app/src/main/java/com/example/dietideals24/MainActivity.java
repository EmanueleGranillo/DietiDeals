package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private MyApiService apiService;
    android.widget.Button compraBtn;
    android.widget.Button vendiBtn;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        TextView registratiTxt = findViewById(R.id.registratiTextButton);
        Button accediBtn = findViewById(R.id.accediButton);
        compraBtn = findViewById(R.id.compraButtonLogin);
        vendiBtn = findViewById(R.id.vendiButtonLogin);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
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
                String tipo;
                if (compraBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {
                    tipo = "compratore";
                    controlloCredenziali(emailEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
                }
                else {
                    tipo = "venditore";
                    controlloCredenziali(emailEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
                }
            }
        });

        vendiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendiBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                compraBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                vendiBtn.setTextColor(Color.parseColor("#FFFFFF"));
                compraBtn.setTextColor(Color.parseColor("#000000"));
            }
        });

        compraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compraBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                vendiBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                compraBtn.setTextColor(Color.parseColor("#FFFFFF"));
                vendiBtn.setTextColor(Color.parseColor("#000000"));
            }
        });
    }

    private void controlloCredenziali(String email, String password, String tipo) {
        // Crea un oggetto di richiesta con i dati dell'utente
        UserAccessRequest accessRequest = new UserAccessRequest(email, password, tipo);

        // Esegui la chiamata Retrofit
        Call<NumeroResponse> call = apiService.checkCredentials(accessRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 1) {
                        if (tipo == "compratore") {
                            Intent goToProfiloCompratore = new Intent(MainActivity.this, HomepageCompratoreActivity.class);
                            startActivity(goToProfiloCompratore);
                        } else {
                            Intent goToProfiloVenditore = new Intent(MainActivity.this, HomepageVenditoreActivity.class);
                            startActivity(goToProfiloVenditore);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Credenziali non autorizzate", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("richiesta fallita");
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

}