package com.example.dietideals24.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.customs.ImageUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import android.util.Base64;
import android.graphics.Bitmap;

public class CreaAstaPT1Activity extends AppCompatActivity {

    ImageView uploadImage;
    private String titoloProdotto;
    private String tipologiaSelezionata;
    private String categoriaSelezionata;
    private String paroleChiave;
    private String nickname;
    private String tipo;
    private String base64String;
    private String descrizione;
    private EditText editTextTitle;
    private EditText editTextDescrizione;
    private EditText editTextParoleChiavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_pt1);
        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescrizione = findViewById(R.id.editTextDescrizione);
        editTextParoleChiavi = findViewById(R.id.editTextParoleChiavi);
        Button createAstaPT1 = findViewById(R.id.forwardButtonCreateAsta);
        Button backButtonHPVenditore = findViewById(R.id.backButtonHomePageVenditore);
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

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSelezionata = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //caso da gestire se permettiamo di non inserire nessuna tipologia asta
            }
        });


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CreaAstaPT1Activity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(240, 240)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });



        createAstaPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tipologiaSelezionata.equals("Asta a tempo fisso")) {
                    titoloProdotto = editTextTitle.getText().toString().trim();
                    descrizione = editTextDescrizione.getText().toString().trim();
                    paroleChiave = editTextParoleChiavi.getText().toString().trim();

                    Intent goToCreateAstaTF = new Intent(CreaAstaPT1Activity.this, CreaAstaTempoFissoActivity.class);
                    goToCreateAstaTF.putExtra("nickname", nickname);
                    goToCreateAstaTF.putExtra("tipo", tipo);
                    goToCreateAstaTF.putExtra("titoloProdotto", titoloProdotto);
                    goToCreateAstaTF.putExtra("imageBase64", base64String);
                    goToCreateAstaTF.putExtra("categoriaSelezionata", categoriaSelezionata);
                    goToCreateAstaTF.putExtra("paroleChiave", paroleChiave);
                    goToCreateAstaTF.putExtra("descrizione", descrizione);
                    goToCreateAstaTF.putExtra("tipologiaSelezionata", tipologiaSelezionata);
                    startActivity(goToCreateAstaTF);
                }

                if (tipologiaSelezionata.equals("Asta all'inglese")) {
                    Intent goToCreateAstaInglese = new Intent(CreaAstaPT1Activity.this, CreaAstaIngleseActivity.class);
                    goToCreateAstaInglese.putExtra("nickname", nickname);
                    goToCreateAstaInglese.putExtra("tipo", tipo);
                    startActivity(goToCreateAstaInglese);
                }

                if (tipologiaSelezionata.equals("Asta al ribasso")) {
                    Intent goToCreateAstaRibasso = new Intent(CreaAstaPT1Activity.this, CreaAstaRibassoActivity.class);
                    goToCreateAstaRibasso.putExtra("nickname", nickname);
                    goToCreateAstaRibasso.putExtra("tipo", tipo);
                    startActivity(goToCreateAstaRibasso);
                }

            }
        });



        backButtonHPVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHPVenditore = new Intent(CreaAstaPT1Activity.this, HomepageVenditoreActivity.class);
                goToHPVenditore.putExtra("tipo", tipo);
                goToHPVenditore.putExtra("nickname", nickname);
                startActivity(goToHPVenditore);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        uploadImage.setImageURI(uri);
        // Ottieni un'immagine Bitmap da qualche fonte (ad esempio, dalla fotocamera o dalla galleria)
        Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
        System.out.println(uri.getPath());
        // Converti l'immagine Bitmap in una stringa Base64
        base64String = ImageUtils.bitmapToBase64(imageBitmap);
    }

}