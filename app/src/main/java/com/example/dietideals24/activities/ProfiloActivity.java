package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietideals24.R;

public class ProfiloActivity extends AppCompatActivity {

    private String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        Button backBtn = findViewById(R.id.backButtonProfilo);
        Button logoutBtn = findViewById(R.id.logoutButton);
        TextView modificaTxt = findViewById(R.id.modificaTextView);
        nickname = getIntent().getStringExtra("nickname");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(ProfiloActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                startActivity(backToHome);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToLogin = new Intent(ProfiloActivity.this, MainActivity.class);
                startActivity(backToLogin);
            }
        });

        modificaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToModifica = new Intent(ProfiloActivity.this, ModificaProfiloActivity.class);
                goToModifica.putExtra("nickname", nickname);
                startActivity(goToModifica);
            }
        });


    }













    /*private static String encodeImage(String imgPath) {

        //da mettere sopra
        Uri uri = data.getData();
        String imgString = uri.toString();
        profiloImage.setImageURI(uri);
        String stringDB = encodeImage(imgString);
        System.out.println(stringDB);
        //Caricare stringa sul server




        try {
            FileInputStream imageStream = new FileInputStream(imgPath);
            byte[] data = new byte[0];
            String imageString = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                data = imageStream.readAllBytes();
                imageString = Base64.getEncoder().encodeToString(data);
            }
            return imageString;
        } catch (Exception e){
        }
        return imgPath;

    }*/


}