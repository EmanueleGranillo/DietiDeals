package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.R;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.UserRegistrationRequest;
import com.example.dietideals24.models.RegisterCheck;

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
    private PopupWindow popupWindow;
    private TextView popupText;
    private View mainLayout, cardLayout;
    private ImageButton infoRegistrazioneButton;

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
        confermaPasswordEditText = findViewById(R.id.confermapasswordEditText);
        emailErrorTextView = findViewById(R.id.emailErrorRegisterTextView);
        nicknameErrorTextView = findViewById(R.id.nicknameErrorRegisterTextView);
        passwordErrorTextView = findViewById(R.id.passwordErrorRegisterTextView);
        confermaPasswordErrorTextView = findViewById(R.id.confermaPasswordErrorRegisterTextView);
        infoRegistrazioneButton = findViewById(R.id.infoRegistrazioneButton);
        mainLayout = findViewById(R.id.registrazioneLayout);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });




        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Nascondi il popup quando si tocca fuori da esso
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true; // Indica che l'evento è stato gestito
                }
                return false; // Lascia l'evento di tocco inalterato
            }
        });

        infoRegistrazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    showPopup();
                }


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

        vendiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendiBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                compraBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                vendiBtn.setTextColor(Color.parseColor("#FFFFFF"));
                compraBtn.setTextColor(Color.parseColor("#000000"));
            }
        });

        registratiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailErrorTextView.setText("");
                nicknameErrorTextView.setText("");
                passwordErrorTextView.setText("");
                confermaPasswordErrorTextView.setText("");
                if (compraBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {
                    tipo = "compratore";
                } else {
                    tipo = "venditore";
                }
                if (check()) {
                    performHttpPostRequest(nicknameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString(), tipo);
                }
            }
        });

    }

    private void showPopup() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        popupText = popupView.findViewById(R.id.popupText);
        popupText.setText("Puoi associare la tua email ad un account compratore e ad un account venditore. Non puoi creare due account con lo stesso nickname.");

        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(300);
        popupView.startAnimation(scaleAnimation);

        int offsetX = -750;
        int offsetY = -350; // Offset verso l'alto rispetto al bottone

        // Mostra il pop-up nella posizione desiderata
        popupWindow.showAsDropDown(infoRegistrazioneButton, offsetX, offsetY);
    }

    private boolean check() {
        int chk = 0;
        if (nicknameEditText.getText().toString().isEmpty()) {
            nicknameErrorTextView.setText("Non hai inserito il nickname!");
            chk++;
        }
        if (emailEditText.getText().toString().isEmpty()) {
            emailErrorTextView.setText("Non hai inserito l'email!");
            chk++;
        }
        if (passwordEditText.getText().toString().isEmpty()) {
            passwordErrorTextView.setText("Non hai inserito la password!");
            chk++;
        }
        if (!(passwordEditText.getText().toString().isEmpty()) && confermaPasswordEditText.getText().toString().isEmpty()) {
            confermaPasswordErrorTextView.setText("Non hai confermato la password!");
            chk++;
        }
        if (chk > 0) {
            return false;
        }
        if (!(passwordEditText.getText().toString().equals(confermaPasswordEditText.getText().toString()))) {
            passwordErrorTextView.setText("Le password non coincidono!");
            return false;
        }
        int chk2 = 0;
        if (emailEditText.getText().toString().length() > 50) {
            emailErrorTextView.setText("Email troppo lunga!");
            chk2++;
        }
        if (nicknameEditText.getText().toString().length() > 30) {
            nicknameErrorTextView.setText("Nickname troppo lungo!");
            chk2++;
        }
        if (passwordEditText.getText().toString().length() > 50) {
            passwordErrorTextView.setText("Password troppo lunga!");
            chk2++;
        }
        if (chk2 > 0) {
            return false;
        }
        return true;
    }

    private void performHttpPostRequest(String nickname, String email, String password, String tipo) {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest(nickname, email, password, tipo);
        Call<RegisterCheck> call = apiService.saveUser(registrationRequest);
        call.enqueue(new Callback<RegisterCheck>() {
            @Override
            public void onResponse(Call<RegisterCheck> call, Response<RegisterCheck> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    RegisterCheck rc = response.body();
                    if (!rc.isNicknameAvailable()) {
                        nicknameErrorTextView.setText("Nickname già esistente!");
                    } else if (!rc.isAccountAvailable()) {
                        emailErrorTextView.setText("Account " + tipo + " già associato a questa email!");
                    } else {
                        Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                        Toast.makeText(RegisterActivity.this, "Registrazione avvenuta con successo!", Toast.LENGTH_LONG).show();
                        startActivity(backToLogin);
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrazione fallita!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterCheck> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connessione fallita!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent backToLogin = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(backToLogin);
    }
}