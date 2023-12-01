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
    private String base64String = "/9j/4AAQSkZJRgABAQAAAQABAAD/4gHYSUNDX1BST0ZJTEUAAQEAAAHIAAAAAAQwAABtbnRyUkdCIFhZWiAH4AABAAEAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAAAADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlkZXNjAAAA8AAAACRyWFlaAAABFAAAABRnWFlaAAABKAAAABRiWFlaAAABPAAAABR3dHB0AAABUAAAABRyVFJDAAABZAAAAChnVFJDAAABZAAAAChiVFJDAAABZAAAAChjcHJ0AAABjAAAADxtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAHMAUgBHAEJYWVogAAAAAAAAb6IAADj1AAADkFhZWiAAAAAAAABimQAAt4UAABjaWFlaIAAAAAAAACSgAAAPhAAAts9YWVogAAAAAAAA9tYAAQAAAADTLXBhcmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABtbHVjAAAAAAAAAAEAAAAMZW5VUwAAACAAAAAcAEcAbwBvAGcAbABlACAASQBuAGMALgAgADIAMAAxADb/2wBDAP//////////////////////////////////////////////////////////////////////////////////////2wBDAf//////////////////////////////////////////////////////////////////////////////////////wAARCAB4APADASIAAhEBAxEB/8QAFwABAQEBAAAAAAAAAAAAAAAAAAECA//EACcQAQACAAQGAgMBAQAAAAAAAAABESExUfASQWFxgZECobHR4cHx/8QAFgEBAQEAAAAAAAAAAAAAAAAAAAEC/8QAGREBAQEAAwAAAAAAAAAAAAAAAAERIVFh/9oADAMBAAIRAxEAPwDAAAAAAAAAAAAKgIACgACgACKgigAhSgCCgKIIAoAAAAAAKgACggttREyDA6x8YhQcq6T9lTpPqXSywc6nSfSOtlg5Dd3yPAMDVR1j7K0xBAopBBZRQWqQsAaqCo6gyNYaL4gGCp0bxlanUGKnQqW66yTERMAxXWCurVTovDrPoGagpvhjWd+Co3h+AIit4R2VK6/gqdfFAqGPQx0AQ8SkyBMoi48hfDETFRM6EyxawMxZGsJzTh0lkFwqYLWJmZUZZZnNuo0KBBQA7jWUx9AR6Dv+JLjcSKol6HeQW0tOLSL+i+vr9z+hFx3/AEZn5Mixu4W3MsXI3xHExahwtylyYFgFSWloKWgBmqAEymKwuapafFpj4tiCSoCAARnjLblJEzGQOqUxxzzXjBZ0i5nvkmHPGU4kFW0AXIAiAuCL4x3/ABWSYSGt+GeYRUUpGkFoAEA0AVNWF3419ekhfuf8EX4wqWWCgAgAFXO8Y5nDpgicUgn/AAWZtKkAAUARQVIVKsLPTNMbyW5mZmO2/rMQj+X31Z5tZRv32jkwClhQulhSiIUqgzRTQCUKAKigAAAAJSgMUNoDI1RQMo1RQIjVKCRNLekb+igEzxkU3vUEUoAkAAUBBQBFAAAAAVAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB/9k=";
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