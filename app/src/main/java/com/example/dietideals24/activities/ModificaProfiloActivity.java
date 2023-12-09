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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietideals24.R;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.UserModifiedRequest;
import com.example.dietideals24.models.Profilo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import android.util.Base64;
import com.example.dietideals24.customs.ImageUtils;

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
    TextView nomeErrorTextView;
    TextView cognomeErrorTextView;
    TextView biografiaErrorTextView;
    TextView linkWebErrorTextView;
    TextView linkInstaErrorTextView;
    TextView posizioneErrorTextView;
    TextView numeroTelefonoErrorTextView;
    TextView immagineErrorTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaprofilo);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        uploadImage = findViewById(R.id.modificaProfiloImage);
        nomeEditText = findViewById(R.id.nomeEditText);
        nomeErrorTextView = findViewById(R.id.nomeErrorTextView);
        cognomeEditText = findViewById(R.id.cognomeEditText);
        cognomeErrorTextView = findViewById(R.id.cognomeErrorTextView);
        biografiaEditText = findViewById(R.id.biografiaEditText);
        biografiaErrorTextView = findViewById(R.id.biografiaErrorTextView);
        linkWebEditText = findViewById(R.id.linksitowebEditText);
        linkWebErrorTextView = findViewById(R.id.linksitowebErrorTextView);
        linkInstaEditText = findViewById(R.id.linkinstagramEditText);
        linkInstaErrorTextView = findViewById(R.id.linkinstagramErrorTextView);
        posizioneEditText = findViewById(R.id.posizioneEditText);
        posizioneErrorTextView = findViewById(R.id.posizioneErrorTextView);
        numeroTelefonoEditText = findViewById(R.id.numeroTelefonoEditText);
        numeroTelefonoErrorTextView = findViewById(R.id.numeroTelefonoErrorTextView);
        immagineErrorTextView = findViewById(R.id.immagineErrorTextView);

        Button annullaBtn = findViewById(R.id.annullaButtonModificaProfilo);
        Button confermaBtn = findViewById(R.id.confermaButtonModificaProfilo);

        getProfiloDaModificare();

        annullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                backToProfilo.putExtra("nickname", nickname);
                backToProfilo.putExtra("tipo", tipo);
                backToProfilo.putExtra("checkActivity", "mine");
                startActivity(backToProfilo);
            }
        });

        confermaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeErrorTextView.setText("");
                cognomeErrorTextView.setText("");
                biografiaErrorTextView.setText("");
                linkWebErrorTextView.setText("");
                linkInstaErrorTextView.setText("");
                posizioneErrorTextView.setText("");
                numeroTelefonoErrorTextView.setText("");

                if(check()){
                    aggiornaProfilo(nomeEditText.getText().toString(), cognomeEditText.getText().toString(), biografiaEditText.getText().toString(), linkWebEditText.getText().toString(), linkInstaEditText.getText().toString(), posizioneEditText.getText().toString(), numeroTelefonoEditText.getText().toString(), base64String);
                    Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                    backToProfilo.putExtra("nickname", nickname);
                    backToProfilo.putExtra("tipo", tipo);
                    backToProfilo.putExtra("checkActivity", "mine");
                    startActivity(backToProfilo);
                } else {

                }
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

    public void aggiornaProfilo(String nome, String cognome, String biografia, String link_web, String link_insta, String posizione, String numero_telefono, String foto_profilo){
        numero_telefono = "+39 " + numero_telefono;
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
                            base64String = profilo.getFotoProfilo();
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

    public boolean check(){
        if(nomeEditText.getText().toString().length() > 30){
            nomeErrorTextView.setText("Nome troppo lungo!");
            return false;
        }
        if(cognomeEditText.getText().toString().length() > 30){
            cognomeErrorTextView.setText("Cognome troppo lungo!");
            return false;
        }
        if(biografiaEditText.getText().toString().length() > 500){
            biografiaErrorTextView.setText("Biografia troppo lunga! "+biografiaEditText.getText().toString().length()+"/500");
            return false;
        }
        if(linkWebEditText.getText().toString().length() > 255){
            linkWebErrorTextView.setText("Link troppo lungo! "+linkWebEditText.getText().toString().length()+"/255" );
            return false;
        }
        if(linkInstaEditText.getText().toString().length() > 255){
            linkInstaErrorTextView.setText("Link troppo lungo! "+linkInstaEditText.getText().toString().length()+"/255");
            return false;
        }
        if(posizioneEditText.getText().toString().length() > 255){
            posizioneErrorTextView.setText("Posizione troppo lunga!");
            return false;
        }
        if(numeroTelefonoEditText.getText().toString().length() > 20){
            numeroTelefonoErrorTextView.setText("Numero di telefono troppo lungo!");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
        backToProfilo.putExtra("nickname", nickname);
        backToProfilo.putExtra("tipo", tipo);
        backToProfilo.putExtra("checkActivity", "mine");
        startActivity(backToProfilo);
        super.onBackPressed();
    }
}
