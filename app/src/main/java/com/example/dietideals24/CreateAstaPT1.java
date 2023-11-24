package com.example.dietideals24;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class CreateAstaPT1 extends AppCompatActivity {

    ImageView uploadImage;
    private String tipologiaSelezionata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_pt1);

        uploadImage = findViewById(R.id.uploadImageIcon);

        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerTipologia = findViewById(R.id.spinnerTipologiaType);

        // Crea un ArrayAdapter usando un array di valori
        String[] valori = {"Asta a tempo fisso", "Asta all'inglese", "Asta al ribasso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valori);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerTipologia.setAdapter(adapter);

        spinnerTipologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipologiaSelezionata = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //caso da gestire se permettiamo di non inserire nessuna tipologia asta
            }
        });


        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategory);

        // Crea un ArrayAdapter usando un array di valori
        String[] categorie = {"Elettronica", "Motori", "Animali", "Moda e bellezza", "Intrattenimento", "Immobili", "Sport", "Arredamento"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerCategoria.setAdapter(adapterCategorie);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CreateAstaPT1.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


        Button createAstaPT1 = findViewById(R.id.forwardButtonCreateAsta);
        createAstaPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tipologiaSelezionata.equals("Asta a tempo fisso")) {
                    Intent goToCreateAstaTF = new Intent(CreateAstaPT1.this, CreateAstaTF.class);
                    startActivity(goToCreateAstaTF);
                }

                if (tipologiaSelezionata.equals("Asta all'inglese")) {
                    Intent goToCreateAstaInglese = new Intent(CreateAstaPT1.this, CreateAstaInglese.class);
                    startActivity(goToCreateAstaInglese);
                }

                if (tipologiaSelezionata.equals("Asta al ribasso")) {
                    Intent goToCreateAstaRibasso = new Intent(CreateAstaPT1.this, CreateAstaRibasso.class);
                    startActivity(goToCreateAstaRibasso);
                }

            }
        });


        Button backButtonHPVenditore = findViewById(R.id.backButtonHomePageVenditore);
        backButtonHPVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHPVenditore = new Intent(CreateAstaPT1.this, HomepageVenditoreActivity.class);
                startActivity(goToHPVenditore);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        uploadImage.setImageURI(uri);
    }

}