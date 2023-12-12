package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.models.Profilo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfiloActivity extends AppCompatActivity {

    private String fotoProfilo;
    private ImageView profiloImage;
    private String nickname;
    private String tipo;
    private String tipologia;
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
    private String linkSitoWeb;
    private String checkActivity;
    private String other;
    private int id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);


        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        checkActivity = getIntent().getStringExtra("checkActivity");



        nicknameProfiloTextView = findViewById(R.id.nicknameProfiloTextView);
        nomeCognomeProfiloTextView = findViewById(R.id.nomeCognomeProfiloTextView);
        bioProfiloTextView = findViewById(R.id.bioProfiloTextView);
        emailProfiloTextView = findViewById(R.id.emailProfiloTextView);
        cellulareProfiloTextView = findViewById(R.id.cellulareProfiloTextView);
        posizioneProfiloTextView = findViewById(R.id.vincenteTextView);
        sitoWebProfiloTextView = findViewById(R.id.sitoWebProfiloTextView);
        linkInstagramImageView = findViewById(R.id.linkInstagramImageView);
        Button backBtn = findViewById(R.id.backButtonProfilo);
        Button logoutBtn = findViewById(R.id.logoutButton);
        TextView modificaTxt = findViewById(R.id.modificaTextView);
        profiloImage = findViewById(R.id.fotoProfiloImage);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        if(checkActivity.equals("notmine")){
            modificaTxt.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            other = getIntent().getStringExtra("other");
            getProfiloDaModificare(other);
            id = getIntent().getIntExtra("id", 0);
            tipologia = getIntent().getStringExtra("tipologia");
        } else {
            getProfiloDaModificare(nickname);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkActivity.equals("notmine")){
                    if(tipologia.equals("asta inglese")){
                        Intent backToAsta = new Intent(ProfiloActivity.this, AstaIngleseActivity.class);
                        backToAsta.putExtra("nickname", nickname);
                        backToAsta.putExtra("tipo", tipo);
                        backToAsta.putExtra("id", id);
                        startActivity(backToAsta);
                    } else if (tipologia.equals("asta al ribasso")) {
                        Intent backToAsta = new Intent(ProfiloActivity.this, AstaRibassoActivity.class);
                        backToAsta.putExtra("nickname", nickname);
                        backToAsta.putExtra("tipo", tipo);
                        backToAsta.putExtra("id", id);
                        startActivity(backToAsta);
                    } else if (tipologia.equals("asta a tempo fisso")) {
                        Intent backToAsta = new Intent(ProfiloActivity.this, AstaTempoFissoActivity.class);
                        backToAsta.putExtra("nickname", nickname);
                        backToAsta.putExtra("tipo", tipo);
                        backToAsta.putExtra("id", id);
                        startActivity(backToAsta);
                    }
                } else {
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
                if(linkSitoWeb.isEmpty()){

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
                    Toast.makeText(ProfiloActivity.this, "Nessun account Instagram collegato", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }


    public void getProfiloDaModificare(String nickname) {
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
                        } else {
                            nomeCognomeProfiloTextView.setText("");
                        }
                        if (profilo.getBiografia() != null) {
                            bioProfiloTextView.setText(profilo.getBiografia());
                        } else {
                            bioProfiloTextView.setText("Nessuna biografia.");
                        }
                        if (profilo.getEmail() != null) {
                            emailProfiloTextView.setText(profilo.getEmail());
                        }
                        if (profilo.getNumeroTelefono() != null) {
                            cellulareProfiloTextView.setText(profilo.getNumeroTelefono());
                        } else {
                            cellulareProfiloTextView.setText("Nessun numero di telefono.");
                        }
                        if (profilo.getPosizione() == null || profilo.getPosizione().toString().isEmpty()) {
                            posizioneProfiloTextView.setText("Nessuna posizione.");
                        } else {
                            posizioneProfiloTextView.setText(profilo.getPosizione());
                        }
                        if (profilo.getLinkWeb() != null || !(profilo.getLinkWeb().toString().isEmpty())) {
                            sitoWebProfiloTextView.setText(profilo.getLinkWeb());
                            sitoWebProfiloTextView.setPaintFlags(sitoWebProfiloTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            linkSitoWeb = profilo.getLinkWeb();
                        } else {
                            sitoWebProfiloTextView.setText("Nessun sito web collegato.");
                            sitoWebProfiloTextView.setTextColor(Color.BLACK);
                            linkSitoWeb = "";
                        }
                        if (profilo.getLinkInsta() != null || !(profilo.getLinkInsta().toString().isEmpty())) {
                            linkInstagram = profilo.getLinkInsta();
                        } else {
                            linkInstagram = "";
                        }
                        if(profilo.getFotoProfilo() != null && !profilo.getFotoProfilo().isEmpty()) {
                            byte[] decodedString = Base64.decode(profilo.getFotoProfilo(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profiloImage.setImageBitmap(decodedByte);
                            profiloImage.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    }
                } else {
                    //Toast.makeText(ProfiloActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Profilo> call, Throwable t) {
                //Toast.makeText(ProfiloActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onBackPressed() {
        if(checkActivity.equals("notmine")){
            if(tipologia.equals("asta inglese")){
                Intent backToAsta = new Intent(ProfiloActivity.this, AstaIngleseActivity.class);
                backToAsta.putExtra("nickname", nickname);
                backToAsta.putExtra("tipo", tipo);
                backToAsta.putExtra("id", id);
                startActivity(backToAsta);
            } else if (tipologia.equals("asta al ribasso")) {
                Intent backToAsta = new Intent(ProfiloActivity.this, AstaRibassoActivity.class);
                backToAsta.putExtra("nickname", nickname);
                backToAsta.putExtra("tipo", tipo);
                backToAsta.putExtra("id", id);
                startActivity(backToAsta);
            } else if (tipologia.equals("asta a tempo fisso")) {
                Intent backToAsta = new Intent(ProfiloActivity.this, AstaTempoFissoActivity.class);
                backToAsta.putExtra("nickname", nickname);
                backToAsta.putExtra("tipo", tipo);
                backToAsta.putExtra("id", id);
                startActivity(backToAsta);
            }
        } else {
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
    }
}