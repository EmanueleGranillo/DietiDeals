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

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.R;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageCompratoreActivity extends AppCompatActivity {

    private MyApiService apiService;
    //Asta astaInglese = new Asta(5,"CASA DI RIPOSO", "Asta all'inglese", new BigDecimal(50), new BigDecimal(5), 40000);
    //Asta astaRibasso = new Asta(6,"BOTTIGLIA ACQUA", "Asta al ribasso", new BigDecimal(100), 50000, new BigDecimal(10), new BigDecimal(50));
    ArrayList<Asta> aste = new ArrayList<Asta>();

    int productsImages[] = {R.drawable.macbook, R.drawable.casa, R.drawable.bottiglia};
    ListView listView;
    CustomBaseAdapterProducts customBaseAdapterProducts;

    Button tutteBtn;
    Button elettronicaBtn;
    Button motoriBtn;
    Button animaliBtn;
    Button modaBtn;
    Button intrattenimentoBtn;
    Button immobiliBtn;
    Button sportBtn;
    Button arredamentoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_compratore);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        tutteBtn = findViewById(R.id.buttonTutteLeCategorie);
        elettronicaBtn = findViewById(R.id.buttonElettronica);
        motoriBtn = findViewById(R.id.buttonMotori);
        animaliBtn = findViewById(R.id.buttonAnimali);
        modaBtn = findViewById(R.id.buttonModa);
        intrattenimentoBtn = findViewById(R.id.buttonIntrattenimento);
        immobiliBtn = findViewById(R.id.buttonImmobili);
        sportBtn = findViewById(R.id.buttonSport);
        arredamentoBtn = findViewById(R.id.buttonArredamento);

        Date d = new Date();
        //Asta astaTF = new Asta(0, "ESTER", "Asta a tempo fisso", d, new BigDecimal(50), new BigDecimal(100));

        //aste.add(astaTF);
        //aste.add(astaInglese);
        //aste.add(astaRibasso);

        riempiLista();


        listView = (ListView) findViewById(R.id.customListViewProducts);
        customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste);
        listView.setAdapter(customBaseAdapterProducts);
        CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);



        Button profiloBtn = findViewById(R.id.profiloButtonHomeCompratore);
        Button notificheBtn = findViewById(R.id.notificheButtonHomeCompratore);
        profiloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilo = new Intent(HomepageCompratoreActivity.this, ProfiloActivity.class);
                startActivity(goToProfilo);
            }
        });

        notificheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNotifiche = new Intent(HomepageCompratoreActivity.this, NotificationsActivity.class);
                startActivity(goToNotifiche);
            }
        });

        tutteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                tutteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                tutteBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        elettronicaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                elettronicaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                elettronicaBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        motoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                motoriBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                motoriBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        animaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                animaliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                animaliBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        modaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                modaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                modaBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        intrattenimentoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                intrattenimentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                intrattenimentoBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        immobiliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                immobiliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                immobiliBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                sportBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                sportBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        arredamentoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                arredamentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                arredamentoBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }

    private void riempiLista() {
        Call<ArrayList<Asta>> call = apiService.getAste();
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste = response.body();

                    // Aggiorna la ListView con i nuovi dati
                    CustomBaseAdapterProducts customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste);
                    listView.setAdapter(customBaseAdapterProducts);
                    CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);


                    //Toast.makeText(HomepageCompratoreActivity.this, "Ci siamo quasi: "+ aste.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomepageCompratoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setWhite() {
        tutteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        tutteBtn.setTextColor(Color.parseColor("#000000"));
        elettronicaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        elettronicaBtn.setTextColor(Color.parseColor("#000000"));
        motoriBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        motoriBtn.setTextColor(Color.parseColor("#000000"));
        animaliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        animaliBtn.setTextColor(Color.parseColor("#000000"));
        modaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        modaBtn.setTextColor(Color.parseColor("#000000"));
        intrattenimentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        intrattenimentoBtn.setTextColor(Color.parseColor("#000000"));
        immobiliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        immobiliBtn.setTextColor(Color.parseColor("#000000"));
        sportBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        sportBtn.setTextColor(Color.parseColor("#000000"));
        arredamentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        arredamentoBtn.setTextColor(Color.parseColor("#000000"));
    }

}









//CREAZIONI ASTE PER PROVARE
//ASTA A TEMPO FISSO
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date dataFineAstaTempoFisso;
//        try {
//            dataFineAstaTempoFisso = dateFormat.parse("2024-01-12");
//            astaTempoFisso = new Asta("MACBOOK PRO", "Asta a tempo fisso", dataFineAstaTempoFisso, new BigDecimal(30), new BigDecimal(100));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        aste = new ArrayList<Asta>();
//        aste.add(astaTempoFisso);
//        aste.add(astaInglese);
//        aste.add(astaRibasso);
