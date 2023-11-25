package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private MyApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inizializza Retrofit
        apiService = RetrofitClient.getInstance().create(MyApiService.class);


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

                performHttpPostRequest();
            }
        });

    }

    private void performHttpPostRequest() {
        // Crea un oggetto di richiesta con i dati dell'utente
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest("Francesco", "Bruno@gmail.com", "pwd1234", "compratore");

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