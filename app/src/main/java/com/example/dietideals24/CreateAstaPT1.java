package com.example.dietideals24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateAstaPT1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_pt1);

        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerTipologia = findViewById(R.id.spinnerAuctionType);

        // Crea un ArrayAdapter usando un array di valori
        String[] valori = {"Asta a tempo fisso", "Asta all'inglese", "Asta al ribasso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valori);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerTipologia.setAdapter(adapter);



        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategory);

        // Crea un ArrayAdapter usando un array di valori
        String[] categorie = {"Elettronica", "Motori", "Animali", "Moda e bellezza", "Intrattenimento", "Immobili", "Sport", "Arredamento"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerCategoria.setAdapter(adapterCategorie);

    }
}