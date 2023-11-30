package com.example.dietideals24.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NicknameRequest;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.UserModifiedRequest;
import com.example.dietideals24.models.Profilo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import android.util.Base64;
import com.example.dietideals24.customs.ImageUtils;
import android.graphics.Bitmap;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificaProfiloActivity extends AppCompatActivity {

    private MyApiService apiService;
    ImageView uploadImage;
    private String base64String;
    private String nickname;
    private String tipo;
    EditText nomeEditText;
    EditText cognomeEditText;
    EditText biografiaEditText;
    EditText linkWebEditText;
    EditText linkInstaEditText;
    EditText posizioneEditText;
    EditText numeroTelefonoEditText;
    Profilo profilo = new Profilo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaprofilo);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        uploadImage = findViewById(R.id.modificaProfiloImage);
        nomeEditText = findViewById(R.id.nomeEditText);
        cognomeEditText = findViewById(R.id.cognomeEditText);
        biografiaEditText = findViewById(R.id.biografiaEditText);
        linkWebEditText = findViewById(R.id.linksitowebEditText);
        linkInstaEditText = findViewById(R.id.linkinstagramEditText);
        posizioneEditText = findViewById(R.id.posizioneEditText);
        numeroTelefonoEditText = findViewById(R.id.numeroTelefonoEditText);

        Button annullaBtn = findViewById(R.id.annullaButtonModificaProfilo);
        Button confermaBtn = findViewById(R.id.confermaButtonModificaProfilo);

        getProfiloDaModificare();

        annullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                backToProfilo.putExtra("nickname", nickname);
                backToProfilo.putExtra("tipo", tipo);
                startActivity(backToProfilo);
            }
        });

        confermaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeEditText.getText().toString().trim();
                String cognome = cognomeEditText.getText().toString().trim();
                String biografia = biografiaEditText.getText().toString().trim();
                String link_web = linkWebEditText.getText().toString().trim();
                String link_insta = linkInstaEditText.getText().toString().trim();
                String posizione = posizioneEditText.getText().toString().trim();
                String numero_telefono = numeroTelefonoEditText.getText().toString().trim();

                //effettuare controlli e poi

                aggiornaProfilo(nome, cognome, biografia, link_web, link_insta, posizione, numero_telefono, base64String);

                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                backToProfilo.putExtra("nickname", nickname);
                backToProfilo.putExtra("tipo", tipo);
                startActivity(backToProfilo);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ModificaProfiloActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(240, 240)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        uploadImage.setImageURI(uri);
        Bitmap imageBitmap = BitmapFactory.decodeFile(uri.getPath());
        base64String = ImageUtils.bitmapToBase64(imageBitmap);
    }

    //public void getProfiloDaModificare() {
        //NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        //Call<> call = apiService.getProfilo(nicknameRequest);
    //}
    public void aggiornaProfilo(String nome, String cognome, String biografia, String link_web, String link_insta, String posizione, String numero_telefono, String foto_profilo){
        UserModifiedRequest userModifiedRequest = new UserModifiedRequest(nickname, nome, cognome, biografia, link_web, link_insta, posizione, numero_telefono, foto_profilo);
        Call<Void> call = apiService.editUser(userModifiedRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                } else {
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void getProfiloDaModificare() {
        Call<Profilo> call = apiService.getUser(nickname);
        call.enqueue(new Callback<Profilo>() {
            @Override
            public void onResponse(Call<Profilo> call, Response<Profilo> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    profilo = response.body();
                    if (profilo != null) {

                        if (profilo.getNome() != null) {
                            nomeEditText.setText(profilo.getNome());
                        }

                        if (profilo.getCognome() != null) {
                            cognomeEditText.setText(profilo.getCognome());
                        }

                        if (profilo.getBiografia() != null) {
                            biografiaEditText.setText(profilo.getBiografia());
                        }

                        if (profilo.getNumeroTelefono() != null) {
                            numeroTelefonoEditText.setText(profilo.getNumeroTelefono());
                        }

                        if (profilo.getPosizione() != null) {
                            posizioneEditText.setText(profilo.getPosizione());
                        }

                        if (profilo.getLinkWeb() != null) {
                            linkWebEditText.setText(profilo.getLinkWeb());
                        }

                        if (profilo.getLinkInsta() != null) {
                            linkInstaEditText.setText(profilo.getLinkInsta());
                        }
                        if (profilo.getFotoProfilo() != null) {
                            byte[] decodedString = Base64.decode(profilo.getFotoProfilo(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            uploadImage.setImageBitmap(decodedByte);
                        }
                    }
                } else {
                    Toast.makeText(ModificaProfiloActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Profilo> call, Throwable t) {
                Toast.makeText(ModificaProfiloActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
