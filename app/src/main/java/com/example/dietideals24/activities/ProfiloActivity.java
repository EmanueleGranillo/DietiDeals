package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Profilo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfiloActivity extends AppCompatActivity {

    private String fotoProfilo;
    private ImageView profiloImage;
    private String nickname;
    private String tipo;
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

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

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
        profiloImage = findViewById(R.id.fotoProfiloImage);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        getProfiloDaModificare();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    Intent backToHomeCompratore = new Intent(ProfiloActivity.this, HomepageCompratoreActivity.class);
                    backToHomeCompratore.putExtra("nickname", nickname);
                    backToHomeCompratore.putExtra("tipo", tipo);
                    startActivity(backToHomeCompratore);
                } else if (tipo.equals("venditore")) {
                    Intent backToHomeVenditore = new Intent(ProfiloActivity.this, HomepageVenditoreActivity.class);
                    backToHomeVenditore.putExtra("nickname", nickname);
                    backToHomeVenditore.putExtra("tipo", tipo);
                    startActivity(backToHomeVenditore);
                }
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
                goToModifica.putExtra("tipo", tipo);
                startActivity(goToModifica);
            }
        });

        sitoWebProfiloTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sitoWebProfiloTextView.getText().toString().isEmpty()){

                } else {
                    Uri uri;
                    if ((sitoWebProfiloTextView.getText().toString().startsWith("http://")) || (sitoWebProfiloTextView.getText().toString().startsWith("https://"))){
                        uri = Uri.parse(sitoWebProfiloTextView.getText().toString());
                    } else {
                        uri = Uri.parse("http://"+sitoWebProfiloTextView.getText().toString());
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            }
        });

        linkInstagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkInstagram.isEmpty()){

                } else {
                    if ((linkInstagram.startsWith("http://")) || (linkInstagram.startsWith("https://"))){
                        Uri uri = Uri.parse(linkInstagram);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        Uri uri = Uri.parse("http://"+linkInstagram);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }

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
                        if(profilo.getFotoProfilo() != null) {
                            byte[] decodedString = Base64.decode(profilo.getFotoProfilo(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profiloImage.setImageBitmap(decodedByte);
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