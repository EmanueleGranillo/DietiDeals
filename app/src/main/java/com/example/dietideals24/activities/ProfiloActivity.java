package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NicknameRequest;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.models.Profilo;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfiloActivity extends AppCompatActivity {

    private String nickname;
    private MyApiService apiService;
    private Profilo profilo = new Profilo();
    private TextView nicknameProfiloTextView;
    private TextView nomeCognomeProfiloTextView;
    private TextView bioProfiloTextView;
    private TextView emailProfiloTextView;
    private TextView cellulareProfiloTextView;
    private TextView posizioneProfiloTextView;
    private TextView sitoWebProfiloTextView;
    private ImageView linkInstagramImageView;
    private String linkInstagram;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        nicknameProfiloTextView = findViewById(R.id.nicknameProfiloTextView);
        nomeCognomeProfiloTextView = findViewById(R.id.nomeCognomeProfiloTextView);
        bioProfiloTextView = findViewById(R.id.bioProfiloTextView);
        emailProfiloTextView = findViewById(R.id.emailProfiloTextView);
        cellulareProfiloTextView = findViewById(R.id.cellulareProfiloTextView);
        posizioneProfiloTextView = findViewById(R.id.posizioneProfiloTextView);
        sitoWebProfiloTextView = findViewById(R.id.sitoWebProfiloTextView);
        linkInstagramImageView = findViewById(R.id.linkInstagramImageView);
        Button backBtn = findViewById(R.id.backButtonProfilo);
        Button logoutBtn = findViewById(R.id.logoutButton);
        TextView modificaTxt = findViewById(R.id.modificaTextView);
        nickname = getIntent().getStringExtra("nickname");

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        getProfiloDaModificare();

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

        linkInstagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkInstagram.isEmpty()){

                } else {
                    Toast.makeText(ProfiloActivity.this, linkInstagram, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void getProfiloDaModificare() {
        Call<Profilo> call = apiService.getUser(nickname);
        call.enqueue(new Callback<Profilo>() {
            @Override
            public void onResponse(Call<Profilo> call, Response<Profilo> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    profilo = response.body();
                    if (profilo != null) {
                        nicknameProfiloTextView.setText(nickname);

                        if (profilo.getNome() != null && profilo.getCognome() != null) {
                            nomeCognomeProfiloTextView.setText(profilo.getNome() + " " + profilo.getCognome());
                        }

                        if (profilo.getBiografia() != null) {
                            bioProfiloTextView.setText(profilo.getBiografia());
                        }

                        if (profilo.getEmail() != null) {
                            emailProfiloTextView.setText(profilo.getEmail());
                        }

                        if (profilo.getNumeroTelefono() != null) {
                            cellulareProfiloTextView.setText(profilo.getNumeroTelefono());
                        }

                        if (profilo.getPosizione() != null) {
                            posizioneProfiloTextView.setText(profilo.getPosizione());
                        }

                        if (profilo.getLinkWeb() != null) {
                            sitoWebProfiloTextView.setText(profilo.getLinkWeb());
                        }

                        if (profilo.getLinkInsta() != null) {
                            linkInstagram = profilo.getLinkInsta();
                        }
                    }
                } else {
                    Toast.makeText(ProfiloActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Profilo> call, Throwable t) {
                Toast.makeText(ProfiloActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
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