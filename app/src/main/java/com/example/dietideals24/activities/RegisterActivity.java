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

import com.example.dietideals24.connection.CheckAccountRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.R;
import com.example.dietideals24.connection.NicknameRequest;
import com.example.dietideals24.connection.NumeroResponse;
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
    EditText confermaPasswordEditText;
    TextView emailErrorTextView;
    TextView nicknameErrorTextView;
    TextView passwordErrorTextView;
    TextView confermaPasswordErrorTextView;
    private String tipo;
    private boolean check;

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
        emailEditText = findViewById(R.id.nicknameEditText);
        nicknameEditText = findViewById(R.id.nicknameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confermaPasswordEditText = findViewById(R.id.confermapasswordEditText);
        emailErrorTextView = findViewById(R.id.emailErrorRegisterTextView);
        nicknameErrorTextView = findViewById(R.id.nicknameErrorRegisterTextView);
        passwordErrorTextView = findViewById(R.id.passwordErrorRegisterTextView);
        confermaPasswordErrorTextView = findViewById(R.id.confermaPasswordErrorRegisterTextView);

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

                emailErrorTextView.setText("");
                nicknameErrorTextView.setText("");
                passwordErrorTextView.setText("");
                confermaPasswordErrorTextView.setText("");
                if (compraBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")){
                    tipo = "compratore";
                } else {
                    tipo = "venditore";
                }
                String nickname = nicknameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confermaPassword = confermaPasswordEditText.getText().toString();

                if( check(nickname, email, password, confermaPassword) && checkNickname(nickname) && checkAccount(email, tipo) ){
                    performHttpPostRequest(nickname, email, password, tipo);
                } else {
                    //Toast.makeText(RegisterActivity.this, "Errore nella registrazione!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void performHttpPostRequest(String nickname, String email, String password, String tipo) {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest(nickname, email, password, tipo);
        Call<ResponseBody> call = apiService.saveUser(registrationRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                    Toast.makeText(RegisterActivity.this, "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
                    startActivity(backToLogin);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrazione fallita!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connessione fallita!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean check(String nickname, String email, String password, String confermaPassword) {
        // Controllo campi vuoti
        int check = 0;
        if(email.isEmpty()){
            emailErrorTextView.setText("Non hai inserito l'email!");
            check++;
        }
        if(nickname.isEmpty()){
            nicknameErrorTextView.setText("Non hai inserito il nickname!");
            check++;
        }
        if(password.isEmpty()){
            passwordErrorTextView.setText("Non hai inserito la password!");
            check++;
        }
        if(confermaPassword.isEmpty() && !(password.isEmpty())){
            confermaPasswordErrorTextView.setText("Non hai confermato la password!");
            check++;
        }
        if(check>0){
            return false;
        }
        // Controllo lunghezza campi
        int check2 = 0;
        if(email.length() > 50){
            emailErrorTextView.setText("Email troppo lunga!");
            check2++;
        }
        if(nickname.length() > 30){
            nicknameErrorTextView.setText("Nickname troppo lungo!");
            check2++;
        }
        if(password.length() > 50){
            passwordErrorTextView.setText("Password troppo lunga!");
            check2++;
        }
        if(check2>0){
            return false;
        }
        // Controllo password
        if(!(passwordEditText.getText().toString().equals(confermaPasswordEditText.getText().toString()))){
            passwordErrorTextView.setText("Le password non coincidono!");
            return false;
        }
        return true;
    }

    public boolean checkNickname(String nickname){
        check = false;
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<NumeroResponse> call = apiService.checkNickname(nicknameRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if(response.isSuccessful()) {
                    NumeroResponse num = response.body();
                    if(num.getNumero() == 1) {
                        nicknameErrorTextView.setText("Nickname già esistente!");
                        check = false;
                    } else {
                        check = true;
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
        return check;
    }

    public boolean checkAccount(String email, String tipo_account) {
        check = false;
        CheckAccountRequest checkAccountRequest = new CheckAccountRequest(email, tipo_account);
        Call<NumeroResponse> call = apiService.checkAccount(checkAccountRequest);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if(response.isSuccessful()){
                    NumeroResponse num = response.body();
                    if(num.getNumero() == 1){
                        check = false;
                        emailErrorTextView.setText("Account "+tipo_account+" già associato a questa email!");
                    } else {
                        check = true;
                        Toast.makeText(RegisterActivity.this, "Errore account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Richiesta fallita!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connessione fallita!", Toast.LENGTH_SHORT).show();
            }
        });
        return check;
    }

}