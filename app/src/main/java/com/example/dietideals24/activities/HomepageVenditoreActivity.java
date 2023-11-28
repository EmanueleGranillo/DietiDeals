package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dietideals24.connection.NicknameRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageVenditoreActivity extends AppCompatActivity {

    private MyApiService apiService;
    ListView listView;
    ArrayList<Asta> aste;
    CustomBaseAdapterProducts customBaseAdapterProducts;
    Button tutteLeAsteBtn;
    Button asteAttiveBtn;
    Button asteConcluseBtn;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_venditore);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        tutteLeAsteBtn = findViewById(R.id.buttonTutteLeAste);
        asteAttiveBtn = findViewById(R.id.buttonAsteAttive);
        asteConcluseBtn = findViewById(R.id.buttonAsteConcluse);
        Button creaAstaBtn = findViewById(R.id.sellNewProductButton);
        Button profiloBtn = findViewById(R.id.profiloButtonHomeVenditore);
        Button notificheBtn = findViewById(R.id.notificheButtonHomeVenditore);

        listView = (ListView) findViewById(R.id.customListViewSellProducts);

        nickname = getIntent().getStringExtra("nickname");
        aste = new ArrayList<Asta>();
        riempiListaPerVenditore(nickname);


        profiloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilo = new Intent(HomepageVenditoreActivity.this, ProfiloActivity.class);
                startActivity(goToProfilo);
            }
        });

        notificheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNotifiche = new Intent(HomepageVenditoreActivity.this, NotificationsActivity.class);
                startActivity(goToNotifiche);
            }
        });

        creaAstaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreaAsta = new Intent(HomepageVenditoreActivity.this, CreaAstaPT1Activity.class);
                startActivity(goToCreaAsta);
            }
        });

        tutteLeAsteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                tutteLeAsteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                tutteLeAsteBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaPerVenditore(nickname);
            }
        });

        asteAttiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteAttiveBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteAttiveBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaAttive(nickname);
            }
        });

        asteConcluseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteConcluseBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteConcluseBtn.setTextColor(Color.parseColor("#FFFFFF"));
                riempiListaConcluse(nickname);
            }
        });



    }

    public void riempiListaPerVenditore(String nickname){
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nicknameRequest);
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
                Toast.makeText(HomepageVenditoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void riempiListaAttive(String nickname){
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nicknameRequest);
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

    public void riempiListaConcluse(String nickname){
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<ArrayList<Asta>> call = apiService.getAstePerVenditore(nicknameRequest);
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