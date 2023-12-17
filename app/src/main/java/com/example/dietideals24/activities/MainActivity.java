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
import com.example.dietideals24.models.ProfiloCheck;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private MyApiService apiService;
    android.widget.Button compraBtn;
    android.widget.Button vendiBtn;
    EditText nicknameEditText;
    EditText passwordEditText;

    TextView nicknameErrorTextView;
    TextView passwordErrorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        TextView registratiTxt = findViewById(R.id.registratiTextButton);
        Button accediBtn = findViewById(R.id.accediButton);
        compraBtn = findViewById(R.id.compraButtonLogin);
        vendiBtn = findViewById(R.id.vendiButtonLogin);
        nicknameEditText = findViewById(R.id.nicknameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nicknameErrorTextView = findViewById(R.id.nicknameErrorTextView);
        passwordErrorTextView = findViewById(R.id.passwordErrorTextView);



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
                nicknameErrorTextView.setText("");
                passwordErrorTextView.setText("");
                String tipo;
                if (compraBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {
                    tipo = "compratore";
                }
                else {
                    tipo = "venditore";
                }
                ProfiloCheck profiloCheck = new ProfiloCheck(nicknameEditText.getText().toString(), passwordEditText.getText().toString());
                boolean controllo = profiloCheck.check();
                if (check() && controllo){
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
                if (response.isSuccessful()) {
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 1) {
                        if (tipo == "compratore") {
                            Intent goToProfiloCompratore = new Intent(MainActivity.this, HomepageCompratoreActivity.class);
                            goToProfiloCompratore.putExtra("nickname", nicknameEditText.getText().toString());
                            goToProfiloCompratore.putExtra("tipo", "compratore");
                            startActivity(goToProfiloCompratore);
                        } else {
                            Intent goToProfiloVenditore = new Intent(MainActivity.this, HomepageVenditoreActivity.class);
                            goToProfiloVenditore.putExtra("nickname", nicknameEditText.getText().toString());
                            goToProfiloVenditore.putExtra("tipo", "venditore");
                            startActivity(goToProfiloVenditore);
                        }
                    } else {
                        passwordErrorTextView.setText("Credenziali non autorizzate!");
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

    private boolean check() {
        //Controllo campi vuoti
        if(nicknameEditText.getText().toString().isEmpty()){
            nicknameErrorTextView.setText("Non hai inserito il nickname!");
            return false;
        } else if (nicknameEditText.getText().toString().length() > 30){
            nicknameErrorTextView.setText("Nickname non trovato!");
            return false;
        }

        if(passwordEditText.getText().toString().isEmpty()){
            passwordErrorTextView.setText("Non hai inserito la password");
            return false;
        } else if(passwordEditText.getText().toString().length() > 50){
            passwordErrorTextView.setText("Password errata!");
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
    }
}