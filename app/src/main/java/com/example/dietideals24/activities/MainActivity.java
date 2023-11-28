package com.example.dietideals24.activities;

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

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.R;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.UserAccessRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Da aggiungere controlli sui textfield


public class MainActivity extends AppCompatActivity {

    private MyApiService apiService;
    android.widget.Button compraBtn;
    android.widget.Button vendiBtn;
    EditText nicknameEditText;
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
        nicknameEditText = findViewById(R.id.nicknameEditText);
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
                    controlloCredenziali(nicknameEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
                }
                else {
                    tipo = "venditore";
                    controlloCredenziali(nicknameEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
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

    private void controlloCredenziali(String nickname, String password, String tipo) {
        UserAccessRequest accessRequest = new UserAccessRequest(nickname, password, tipo);
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
                            goToProfiloCompratore.putExtra("nickname", nicknameEditText.getText().toString());
                            startActivity(goToProfiloCompratore);
                        } else {
                            Intent goToProfiloVenditore = new Intent(MainActivity.this, HomepageVenditoreActivity.class);
                            goToProfiloVenditore.putExtra("nickname", nicknameEditText.getText().toString());
                            startActivity(goToProfiloVenditore);

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Credenziali non autorizzate", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });


    }

}