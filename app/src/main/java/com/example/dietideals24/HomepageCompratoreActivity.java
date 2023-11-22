package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;

public class HomepageCompratoreActivity extends AppCompatActivity {
    Asta astaInglese = new Asta("CASA DI RIPOSO", "Asta all'inglese", new BigDecimal(50), new BigDecimal(5), 40000);
    Asta astaRibasso = new Asta("BOTTIGLIA ACQUA", "Asta al ribasso", new BigDecimal(100), 50000, new BigDecimal(10), new BigDecimal(50));
    ArrayList<Asta> aste = new ArrayList<Asta>();

    int productsImages[] = {R.drawable.macbook, R.drawable.casa, R.drawable.bottiglia};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_compratore);

        Date d = new Date();
        d.setDate(1);
        Asta astaTF = new Asta("ESTER", "Asta a tempo fisso", d, new BigDecimal(50), new BigDecimal(100));

        aste.add(astaTF);
        aste.add(astaInglese);
        aste.add(astaRibasso);


        listView = (ListView) findViewById(R.id.customListViewProducts);
        CustomBaseAdapterProducts customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste, productsImages);
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
