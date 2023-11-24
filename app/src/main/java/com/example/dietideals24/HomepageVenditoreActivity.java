package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class HomepageVenditoreActivity extends AppCompatActivity {

    ListView listView;

    Button tutteLeAsteBtn;
    Button asteAttiveBtn;
    Button asteConcluseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_venditore);

        tutteLeAsteBtn = findViewById(R.id.buttonTutteLeAste);
        asteAttiveBtn = findViewById(R.id.buttonAsteAttive);
        asteConcluseBtn = findViewById(R.id.buttonAsteConcluse);

        Button creaAstaBtn = findViewById(R.id.sellNewProductButton);
        Button profiloBtn = findViewById(R.id.profiloButtonHomeVenditore);
        Button notificheBtn = findViewById(R.id.notificheButtonHomeVenditore);

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
                Intent goToCreaAsta = new Intent(HomepageVenditoreActivity.this, CreateAstaPT1.class);
                startActivity(goToCreaAsta);
            }
        });

        tutteLeAsteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                tutteLeAsteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                tutteLeAsteBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        asteAttiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteAttiveBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteAttiveBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        asteConcluseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWhite();
                asteConcluseBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                asteConcluseBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });


        Asta astaInglese = new Asta("CASA DI RIPOSO", "Asta all'inglese", new BigDecimal(50), new BigDecimal(5), 40000);
        Asta astaRibasso = new Asta("BOTTIGLIA ACQUA", "Asta al ribasso", new BigDecimal(100), 50000, new BigDecimal(10), new BigDecimal(50));
        ArrayList<Asta> aste = new ArrayList<Asta>();

        int productsImages[] = {R.drawable.macbook, R.drawable.casa, R.drawable.bottiglia};

        Date d = new Date();
        Asta astaTF = new Asta("ESTER", "Asta a tempo fisso", d, new BigDecimal(50), new BigDecimal(100));

        aste.add(astaTF);
        aste.add(astaInglese);
        aste.add(astaRibasso);

        listView = (ListView) findViewById(R.id.customListViewSellProducts);
        CustomBaseAdapterProducts customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste, productsImages);
        listView.setAdapter(customBaseAdapterProducts);
        CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);

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