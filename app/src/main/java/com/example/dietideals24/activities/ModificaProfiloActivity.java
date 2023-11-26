package com.example.dietideals24.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dietideals24.R;
import com.github.dhaval2404.imagepicker.ImagePicker;



public class ModificaProfiloActivity extends AppCompatActivity {

    ImageView profiloImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaprofilo);

        Button annullaBtn = findViewById(R.id.annullaButtonModificaProfilo);
        Button confermaBtn = findViewById(R.id.confermaButtonModificaProfilo);
        profiloImage = findViewById(R.id.modificaProfiloImage);

        profiloImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ModificaProfiloActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        annullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                startActivity(backToProfilo);
            }
        });

        confermaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToProfilo = new Intent(ModificaProfiloActivity.this, ProfiloActivity.class);
                startActivity(backToProfilo);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        profiloImage.setImageURI(uri);
    }
}
