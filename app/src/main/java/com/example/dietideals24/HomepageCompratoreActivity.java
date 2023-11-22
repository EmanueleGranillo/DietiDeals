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

    ArrayList<Asta> aste;
    Asta astaTempoFisso;
    Asta astaInglese;
    Asta astaRibasso;
    int productsImages[] = {R.drawable.macbookPro, R.drawable.casaDiRiposo, R.drawable.bottigliaAcqua};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //CREAZIONI ASTE PER PROVARE
        //ASTA A TEMPO FISSO
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataFineAstaTempoFisso;
        try {
            dataFineAstaTempoFisso = dateFormat.parse("2024-01-12");
            astaTempoFisso = new Asta("MACBOOK PRO", "Asta a tempo fisso", dataFineAstaTempoFisso, new BigDecimal(30), new BigDecimal(100));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //ASTA ALL'INGLESE
        astaInglese = new Asta("CASA DI RIPOSO", "Asta all'inglese", new BigDecimal(50), new BigDecimal(5), 40000);

        //ASTA AL RIBASSO
        astaRibasso = new Asta("BOTTIGLIA ACQUA", "Asta al ribasso", new BigDecimal(100), 50000, new BigDecimal(10), new BigDecimal(50));

        aste = new ArrayList<Asta>();
        aste.add(astaTempoFisso);
        aste.add(astaInglese);
        aste.add(astaRibasso);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_compratore);

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

        listView = (ListView) findViewById(R.id.);
        CustomBaseAdapterNotifications customBaseAdapter = new CustomBaseAdapterNotifications(getApplicationContext(), notificationsTitleList, checkOrXIcons);
        listView.setAdapter(customBaseAdapter);
    }
}