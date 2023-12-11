package com.example.dietideals24.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private String titoloProdotto;
    private String tipologiaSelezionata;
    private int tipologiaPosition = 0;
    private String categoriaSelezionata;
    private int categoriaPosition = 0;
    private String paroleChiave;
    private String nickname;
    private String tipo;
    private String imageString = "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";
    private String descrizione;
    private EditText titoloProdottoEditText;
    private EditText descrizioneEditText;
    private EditText keywordsEditText;
    private TextView titoloErrorTextView;
    private TextView descrizioneErrorTextView;
    private TextView keywordsErrorTextView;
    private ImageButton infoButton;
    private PopupWindow popupWindow;
    private TextView popupText;
    private View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asta_pt1);
        titoloProdottoEditText = findViewById(R.id.editTextTitle);
        descrizioneEditText = findViewById(R.id.editTextDescrizione);
        keywordsEditText = findViewById(R.id.editTextParoleChiave);
        Button createAstaPT1 = findViewById(R.id.forwardButtonCreateAsta);
        Button backButtonHPVenditore = findViewById(R.id.backButtonHomePageVenditore);
        uploadImage = findViewById(R.id.uploadImageIcon);
        titoloErrorTextView = findViewById(R.id.titoloAstaErrorTextView);
        descrizioneErrorTextView = findViewById(R.id.descrizioneErrorTextView);
        keywordsErrorTextView = findViewById(R.id.keywordsErrorEditText);
        infoButton = findViewById(R.id.infoCreaAstaButton);
        mainLayout = findViewById(R.id.creaAstaLayout);


        activity = getIntent().getStringExtra("activity");
        if(activity.equals("homepage")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
        } else if (activity.equals("creainglese")){
            nickname = getIntent().getStringExtra("nickname");
            tipo = getIntent().getStringExtra("tipo");
            titoloProdotto = getIntent().getStringExtra("titoloProdotto");

            imageString = getIntent().getStringExtra("imageString");
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            // Mostra l'immagine nel client tagliata 1:1
            displayImage(decodedByte);

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

            imageString = getIntent().getStringExtra("imageString");
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            // Mostra l'immagine nel client tagliata 1:1
            displayImage(decodedByte);

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

            imageString = getIntent().getStringExtra("imageString");
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            // Mostra l'immagine nel client tagliata 1:1
            displayImage(decodedByte);

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



        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    showPopup(tipologiaSelezionata);
                }


            }
        });

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Nascondi il popup quando si tocca fuori da esso
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true; // Indica che l'evento è stato gestito
                }
                return false; // Lascia l'evento di tocco inalterato
            }
        });
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
                        .maxResultSize(480, 480)    //Final image resolution will be less than 1080 x 1080(Optional)
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
                        goToCreateAstaTF.putExtra("imageString", imageString);
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
                        goToCreateAstaInglese.putExtra("imageString", imageString);
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
                        goToCreateAstaRibasso.putExtra("imageString", imageString);
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
        //uploadImage.setImageURI(uri);
        //uploadImage.setScaleType(ImageView.ScaleType.FIT_XY);
        // Ottieni un'immagine Bitmap da qualche fonte (ad esempio, dalla fotocamera o dalla galleria)
        Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
        // Converti l'immagine Bitmap in una stringa Base64
        imageString = ImageUtils.bitmapToBase64(imageBitmap);

        // Mostra l'immagine nel client tagliata 1:1
        displayImage(imageBitmap);
    }


    private void displayImage(Bitmap imageBitmap) {
        // Imposta l'immagine nell'ImageView
        uploadImage.setImageBitmap(getSquareBitmap(imageBitmap));
        uploadImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private Bitmap getSquareBitmap(Bitmap bitmap) {
        // Taglia l'immagine in un quadrato
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = Math.min(width, height);

        int x = (width - size) / 2;
        int y = (height - size) / 2;

        return Bitmap.createBitmap(bitmap, x, y, size, size);
    }


    public boolean check(){
        if(titoloProdottoEditText.getText().toString().isEmpty()){
            titoloErrorTextView.setText("Inserisci il nome del prodotto/servizio");
            return false;
        }
        // Controllo lunghezza campi
        if(titoloProdottoEditText.getText().toString().length() > 40) {
            titoloErrorTextView.setText("Nome troppo lungo");
            return false;
        }
        if(descrizioneEditText.getText().toString().length() > 300){
            descrizioneErrorTextView.setText("Descrizione troppo lunga! "+descrizioneEditText.getText().toString().length()+"/300");
            return false;
        }
        if(keywordsEditText.getText().toString().length() > 100) {
            keywordsErrorTextView.setText("Numero massimo di caratteri raggiunto! "+keywordsEditText.getText().toString().length()+"/100");
            return false;
        }
        return true;
    }

    private void showPopup(String tipologia){
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        popupText = popupView.findViewById(R.id.popupText);
        if(tipologia.equals("asta a tempo fisso")){
            popupText.setText("Asta a tempo fisso: scegli una data di scadenza. Specifica una soglia minima di prezzo al quale vendere il prodotto (la soglia minima rimarrà segreta).");
        } else if (tipologia.equals("asta inglese")){
            popupText.setText("Asta all'inglese: scegli una base d'asta iniziale, un intervallo di tempo fisso per presentare nuove offerte e una soglia di rialzo del prezzo. Quando viene presentata un'offerta, il timer viene resettato.");
        } else {
            popupText.setText("Asta al ribasso: scegli un prezzo iniziale, un intervallo di tempo per il decremento del prezzo, la quantità da sottrarre al prezzo ad ogni intervallo di tempo e una soglia minima (segreta) a cui vendere il prodotto. Quando viene raggiunto il timer, il prezzo verrà decrementato dell'importo previsto.");
        }

        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(300);
        popupView.startAnimation(scaleAnimation);

        int offsetX = -750;
        int offsetY = -350; // Offset verso l'alto rispetto al bottone

        // Mostra il pop-up nella posizione desiderata
        popupWindow.showAsDropDown(infoButton, offsetX, offsetY);

    }

    @Override
    public void onBackPressed() {
        Intent goToHPVenditore = new Intent(CreaAstaPT1Activity.this, HomepageVenditoreActivity.class);
        goToHPVenditore.putExtra("tipo", tipo);
        goToHPVenditore.putExtra("nickname", nickname);
        startActivity(goToHPVenditore);
        super.onBackPressed();
    }



}