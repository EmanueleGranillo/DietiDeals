package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.R;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageVenditoreActivity extends AppCompatActivity {

    private String activity = "homepage";
    private MyApiService apiService;
    ImageView pallinoImg;
    ListView listView;
    ArrayList<Asta> aste;
    CustomBaseAdapterProducts customBaseAdapterProducts;
    Button tutteLeAsteBtn;
    Button asteAttiveBtn;
    Button asteConcluseBtn;
    String nickname;
    String tipo;
    Log log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_venditore);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);


        pallinoImg = findViewById(R.id.pallinoButton);
        tutteLeAsteBtn = findViewById(R.id.buttonTutteLeAste);
        asteAttiveBtn = findViewById(R.id.buttonAsteAttive);
        asteConcluseBtn = findViewById(R.id.buttonAsteConcluse);
        Button creaAstaBtn = findViewById(R.id.sellNewProductButton);
        Button profiloBtn = findViewById(R.id.profiloButtonHomeVenditore);
        Button notificheBtn = findViewById(R.id.notificheButtonHomeVenditore);

        listView = (ListView) findViewById(R.id.customListViewSellProducts);
        tipo = getIntent().getStringExtra("tipo");
        nickname = getIntent().getStringExtra("nickname");
        aste = new ArrayList<Asta>();
        pallinoImg.setVisibility(View.INVISIBLE);

        controllaNotifiche();

        riempiListaPerVenditore();







































        profiloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilo = new Intent(HomepageVenditoreActivity.this, ProfiloActivity.class);
                goToProfilo.putExtra("nickname", nickname);
                goToProfilo.putExtra("tipo", tipo);
                startActivity(goToProfilo);
            }
        });

        notificheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNotifiche = new Intent(HomepageVenditoreActivity.this, NotificationsActivity.class);
                goToNotifiche.putExtra("nickname", nickname);
                goToNotifiche.putExtra("tipo", tipo);
                startActivity(goToNotifiche);
            }
        });

        creaAstaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreaAsta = new Intent(HomepageVenditoreActivity.this, CreaAstaPT1Activity.class);
                goToCreaAsta.putExtra("nickname", nickname);
                goToCreaAsta.putExtra("tipo", tipo);
                goToCreaAsta.putExtra("activity", activity);
                startActivity(goToCreaAsta);
            }
        });

        tutteLeAsteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                tutteLeAsteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                tutteLeAsteBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaPerVenditore();
            }
        });

        asteAttiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteAttiveBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteAttiveBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaAttive();
            }
        });

        asteConcluseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteConcluseBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteConcluseBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaConcluse();
            }
        });



    }


    private void controllaNotifiche() {
        Call<NumeroResponse> call = apiService.checkNotifications(nickname);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if (response.isSuccessful()) {
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 0) {
                        pallinoImg.setVisibility(View.INVISIBLE);
                    } else {
                        pallinoImg.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                Toast.makeText(HomepageVenditoreActivity.this, "Connessione fallita per check notifiche", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void riempiListaPerVenditore(){
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nickname);
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());

                    // Aggiorna la ListView con i nuovi dati custom
                    customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste);
                    listView.setAdapter(customBaseAdapterProducts);
                    CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);

                } else {
                    Toast.makeText(HomepageVenditoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                // Log dell'eccezione per debugging
                log.e("API_FAILURE", "Errore di connessione", t);

                Toast.makeText(HomepageVenditoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void riempiListaAttive(){
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nickname);
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());
                    Iterator<Asta> iterator = aste.iterator();
                    while(iterator.hasNext()){
                        Asta a = iterator.next();
                        if (a.getStatoAsta() == false) {
                            iterator.remove();
                        }
                    }
                    // Aggiorna la ListView con i nuovi dati custom
                    customBaseAdapterProducts.notifyDataSetChanged();

                } else {
                    Toast.makeText(HomepageVenditoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageVenditoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void riempiListaConcluse(){
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nickname);
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());
                    Iterator<Asta> iterator = aste.iterator();
                    while(iterator.hasNext()){
                        Asta a = iterator.next();
                        if (a.getStatoAsta()) {
                            iterator.remove();
                        }
                    }
                    // Aggiorna la ListView con i nuovi dati custom
                    customBaseAdapterProducts.notifyDataSetChanged();

                } else {
                    Toast.makeText(HomepageVenditoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageVenditoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setWhite() {
        tutteLeAsteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        tutteLeAsteBtn.setTextColor(Color.parseColor("#000000"));
        asteAttiveBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        asteAttiveBtn.setTextColor(Color.parseColor("#000000"));
        asteConcluseBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        asteConcluseBtn.setTextColor(Color.parseColor("#000000"));
    }


}