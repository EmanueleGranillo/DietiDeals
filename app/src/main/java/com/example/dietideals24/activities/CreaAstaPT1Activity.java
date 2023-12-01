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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.customs.ImageUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import android.graphics.Bitmap;

public class CreaAstaPT1Activity extends AppCompatActivity {

    String activity;
    ImageView uploadImage;
    String base64Image;
    private String titoloProdotto;
    private String tipologiaSelezionata;
    private int tipologiaPosition = 0;
    private String categoriaSelezionata;
    private int categoriaPosition = 0;
    private String paroleChiave;
    private String nickname;
    private String tipo;
    private String base64String = "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";
    private String descrizione;
    private EditText titoloProdottoEditText;
    private EditText descrizioneEditText;
    private EditText keywordsEditText;
    private TextView titoloErrorTextView;
    private TextView descrizioneErrorTextView;
    private TextView keywordsErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_pt1);
        titoloProdottoEditText = findViewById(R.id.editTextTitle);
        descrizioneEditText = findViewById(R.id.editTextDescrizione);
        keywordsEditText = findViewById(R.id.editTextParoleChiavi);
        Button createAstaPT1 = findViewById(R.id.forwardButtonCreateAsta);
        Button backButtonHPVenditore = findViewById(R.id.backButtonHomePageVenditore);
        uploadImage = findViewById(R.id.uploadImageIcon);
        titoloErrorTextView = findViewById(R.id.titoloAstaErrorTextView);
        descrizioneErrorTextView = findViewById(R.id.descrizioneErrorTextView);
        keywordsErrorTextView = findViewById(R.id.keywordsErrorEditText);


        activity = getIntent().getStringExtra("activity");
        Toast.makeText(CreaAstaPT1Activity.this, activity, Toast.LENGTH_SHORT).show();

        if(activity.equals("homepage")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
        } else if (activity.equals("creainglese")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
            titoloProdotto = getIntent().getStringExtra("titoloProdotto");
            base64Image = getIntent().getStringExtra("imageBase64");
            categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
            paroleChiave = getIntent().getStringExtra("paroleChiave");
            descrizione = getIntent().getStringExtra("descrizione");
            tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
            tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", 0);
            categoriaPosition = getIntent().getIntExtra("categoriaPosition", 0);
            titoloProdottoEditText.setText(titoloProdotto);
            descrizioneEditText.setText(descrizione);
            keywordsEditText.setText(paroleChiave);
        } else if (activity.equals("creatempofisso")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
            titoloProdotto = getIntent().getStringExtra("titoloProdotto");
            base64Image = getIntent().getStringExtra("imageBase64");
            categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
            paroleChiave = getIntent().getStringExtra("paroleChiave");
            descrizione = getIntent().getStringExtra("descrizione");
            tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
            tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", 0);
            categoriaPosition = getIntent().getIntExtra("categoriaPosition", 0);
            titoloProdottoEditText.setText(titoloProdotto);
            descrizioneEditText.setText(descrizione);
            keywordsEditText.setText(paroleChiave);
        } else if (activity.equals("crearibasso")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
            titoloProdotto = getIntent().getStringExtra("titoloProdotto");
            base64Image = getIntent().getStringExtra("imageBase64");
            categoriaSelezionata = getIntent().getStringExtra("categoriaSelezionata");
            paroleChiave = getIntent().getStringExtra("paroleChiave");
            descrizione = getIntent().getStringExtra("descrizione");
            tipologiaSelezionata = getIntent().getStringExtra("tipologiaSelezionata");
            tipologiaPosition = getIntent().getIntExtra("tipologiaPosition", 0);
            categoriaPosition = getIntent().getIntExtra("categoriaPosition", 0);
            titoloProdottoEditText.setText(titoloProdotto);
            descrizioneEditText.setText(descrizione);
            keywordsEditText.setText(paroleChiave);
        }















        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerTipologia = findViewById(R.id.spinnerTipologiaType);

        // Crea un ArrayAdapter usando un array di valori
        String[] valori = {"asta a tempo fisso", "asta inglese", "asta al ribasso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valori);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerTipologia.setAdapter(adapter);

        spinnerTipologia.setSelection(tipologiaPosition);

        // Recupera il riferimento allo Spinner dal layout
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategory);

        // Crea un ArrayAdapter usando un array di valori
        String[] categorie = {"elettronica", "motori", "animali", "moda e bellezza", "intrattenimento", "immobili", "sport", "arredamento"};
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorie);

        // Specifica il layout da utilizzare quando la lista degli elementi appare
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Applica l'adapter allo Spinner
        spinnerCategoria.setAdapter(adapterCategorie);

        spinnerCategoria.setSelection(categoriaPosition);





        spinnerTipologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipologiaSelezionata = (String) adapterView.getItemAtPosition(i);
                tipologiaPosition = spinnerTipologia.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //caso da gestire se permettiamo di non inserire nessuna tipologia asta
            }
        });




        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSelezionata = (String) adapterView.getItemAtPosition(i);
                categoriaPosition = spinnerCategoria.getSelectedItemPosition();
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
                descrizioneErrorTextView.setText("");
                titoloErrorTextView.setText("");
                keywordsErrorTextView.setText("");
                if(check()){
                    if (tipologiaSelezionata.equals("asta a tempo fisso")) {
                        titoloProdotto = titoloProdottoEditText.getText().toString().trim();
                        descrizione = descrizioneEditText.getText().toString().trim();
                        paroleChiave = keywordsEditText.getText().toString().trim();

                        Intent goToCreateAstaTF = new Intent(CreaAstaPT1Activity.this, CreaAstaTempoFissoActivity.class);
                        goToCreateAstaTF.putExtra("nickname", nickname);
                        goToCreateAstaTF.putExtra("tipo", tipo);
                        goToCreateAstaTF.putExtra("titoloProdotto", titoloProdotto);
                        goToCreateAstaTF.putExtra("imageBase64", base64String);
                        goToCreateAstaTF.putExtra("categoriaSelezionata", categoriaSelezionata);
                        goToCreateAstaTF.putExtra("paroleChiave", paroleChiave);
                        goToCreateAstaTF.putExtra("descrizione", descrizione);
                        goToCreateAstaTF.putExtra("tipologiaSelezionata", tipologiaSelezionata);
                        goToCreateAstaTF.putExtra("tipologiaPosition", tipologiaPosition);
                        goToCreateAstaTF.putExtra("categoriaPosition", categoriaPosition);
                        startActivity(goToCreateAstaTF);
                    }

                    if (tipologiaSelezionata.equals("asta inglese")) {
                        titoloProdotto = titoloProdottoEditText.getText().toString().trim();
                        descrizione = descrizioneEditText.getText().toString().trim();
                        paroleChiave = keywordsEditText.getText().toString().trim();

                        Intent goToCreateAstaInglese = new Intent(CreaAstaPT1Activity.this, CreaAstaIngleseActivity.class);
                        goToCreateAstaInglese.putExtra("nickname", nickname);
                        goToCreateAstaInglese.putExtra("tipo", tipo);
                        goToCreateAstaInglese.putExtra("titoloProdotto", titoloProdotto);
                        goToCreateAstaInglese.putExtra("imageBase64", base64String);
                        goToCreateAstaInglese.putExtra("categoriaSelezionata", categoriaSelezionata);
                        goToCreateAstaInglese.putExtra("paroleChiave", paroleChiave);
                        goToCreateAstaInglese.putExtra("descrizione", descrizione);
                        goToCreateAstaInglese.putExtra("tipologiaSelezionata", tipologiaSelezionata);
                        goToCreateAstaInglese.putExtra("tipologiaPosition", tipologiaPosition);
                        goToCreateAstaInglese.putExtra("categoriaPosition", categoriaPosition);
                        startActivity(goToCreateAstaInglese);
                    }

                    if (tipologiaSelezionata.equals("asta al ribasso")) {
                        titoloProdotto = titoloProdottoEditText.getText().toString().trim();
                        descrizione = descrizioneEditText.getText().toString().trim();
                        paroleChiave = keywordsEditText.getText().toString().trim();

                        Intent goToCreateAstaRibasso = new Intent(CreaAstaPT1Activity.this, CreaAstaRibassoActivity.class);
                        goToCreateAstaRibasso.putExtra("nickname", nickname);
                        goToCreateAstaRibasso.putExtra("tipo", tipo);
                        goToCreateAstaRibasso.putExtra("titoloProdotto", titoloProdotto);
                        goToCreateAstaRibasso.putExtra("imageBase64", base64String);
                        goToCreateAstaRibasso.putExtra("categoriaSelezionata", categoriaSelezionata);
                        goToCreateAstaRibasso.putExtra("paroleChiave", paroleChiave);
                        goToCreateAstaRibasso.putExtra("descrizione", descrizione);
                        goToCreateAstaRibasso.putExtra("tipologiaSelezionata", tipologiaSelezionata);
                        goToCreateAstaRibasso.putExtra("tipologiaPosition", tipologiaPosition);
                        goToCreateAstaRibasso.putExtra("categoriaPosition", categoriaPosition);
                        startActivity(goToCreateAstaRibasso);
                    }
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

    public boolean check(){
        if(titoloProdottoEditText.getText().toString().isEmpty()){
            titoloErrorTextView.setText("Inserisci il nome del prodotto/servizio");
            return false;
        }
        // Controllo lunghezza campi
        if(titoloProdottoEditText.getText().toString().length() > 50) {
            titoloErrorTextView.setText("Nome troppo lungo");
            return false;
        }
        if(descrizioneEditText.getText().toString().length() > 500){
            descrizioneErrorTextView.setText("Descrizione troppo lunga! "+descrizioneEditText.getText().toString().length()+"/500");
            return false;
        }
        if(keywordsEditText.getText().toString().length() > 100) {
            keywordsErrorTextView.setText("Numero massimo di caratteri raggiunto! "+keywordsEditText.getText().toString().length()+"/500");
            return false;
        }
        return true;
    }
}