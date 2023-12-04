package com.example.dietideals24.customs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietideals24.activities.AstaIngleseActivity;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomBaseAdapterProducts extends BaseAdapter {
    Context context;
    ArrayList<Asta> aste;
    LayoutInflater inflater;

    ImageView productImage;

    String imageBase64, dateString;
    Date date = null;
    private TextView titoloTextView, scadenzaDataTextView, prezzoAttualeTextView;

    // timeAttuale, timeScadenza,

    public CustomBaseAdapterProducts (Context ctx, ArrayList<Asta> aste){
        this.context = ctx;
        this.aste = aste;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return aste.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(aste.get(position).getTipologia().equals("asta a tempo fisso")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_t_f, null);
            titoloTextView = (TextView) convertView.findViewById(R.id.titoloTFTextView);
            scadenzaDataTextView = (TextView) convertView.findViewById(R.id.dataScadenzaTextView);
            prezzoAttualeTextView = (TextView) convertView.findViewById(R.id.offertaAttualeTFTextView);
            productImage = (ImageView) convertView.findViewById(R.id.productTFImageView);
            TextView conclusaTFTextView = convertView.findViewById(R.id.conclusaTFTextView);
            conclusaTFTextView.setVisibility(View.INVISIBLE);
            titoloTextView.setText(aste.get(position).getNomeProdotto());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                date = inputFormat.parse(aste.get(position).getDataScadenzaTF());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            Date dataOraAttuale = calendar.getTime();
            scadenzaDataTextView.setText(outputFormat.format(date));
            if (date.getTime() < dataOraAttuale.getTime()) {
                scadenzaDataTextView.setVisibility(View.INVISIBLE);
                conclusaTFTextView.setVisibility(View.VISIBLE);
            }
            // prezzoAttualeTextView.setText(aste.get(position).getOffertaAttuale().toString() + "€"); nuovo

            // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
            if (aste.get(position).getFotoProdotto() != null && !aste.get(position).getFotoProdotto().isEmpty()) {
                imageBase64 = aste.get(position).getFotoProdotto();
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                productImage.setImageBitmap(decodedByte);
            } else {
                // Immagine di fallback o gestisci la situazione come desideri
                productImage.setImageResource(R.drawable.shopping_bag);
            }
            productImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }






        if(aste.get(position).getTipologia().equals("asta inglese")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_english, null);
            TextView titoloTextView = (TextView) convertView.findViewById(R.id.titoloIngleseTextView);
            TextView conclusaIngleseTextView = convertView.findViewById(R.id.conclusaIngleseTextView);
            conclusaIngleseTextView.setVisibility(View.INVISIBLE);
            TextView offertaAttualeIngleseTextView = convertView.findViewById(R.id.offertaAttualeIngleseTextView);
            productImage = (ImageView) convertView.findViewById(R.id.productIngleseImageView);
            titoloTextView.setText(aste.get(position).getNomeProdotto());

            Calendar calendar = Calendar.getInstance();
            Date dataOraAttuale = calendar.getTime();
            calendar.setTime(dataOraAttuale);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            try {
                date = inputFormat.parse(aste.get(position).getDataScadenzaTF());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long timer = date.getTime() - dataOraAttuale.getTime();

            // GESTIONE TIMER
            Chronometer chronometer = convertView.findViewById(R.id.chronometerInglese);
            long elapsedTime = SystemClock.elapsedRealtime() + timer;
            chronometer.setBase(elapsedTime);
            chronometer.start();

            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if(chronometer.getBase() < SystemClock.elapsedRealtime() + 10000) {
                        chronometer.setTextColor(Color.RED);
                    }
                    if(chronometer.getBase() < SystemClock.elapsedRealtime() + 1) {
                        chronometer.stop();
                        conclusaIngleseTextView.setVisibility(View.VISIBLE);
                        chronometer.setVisibility(View.INVISIBLE);
                        //manda notifiche

                    }
                }
            });

            // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
            if (aste.get(position).getFotoProdotto() != null && !aste.get(position).getFotoProdotto().isEmpty()) {
                imageBase64 = aste.get(position).getFotoProdotto();
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                productImage.setImageBitmap(decodedByte);
            } else {
                // Immagine di fallback o gestisci la situazione come desideri
                productImage.setImageResource(R.mipmap.ic_no_icon_foreground);
            }
            productImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }













        if(aste.get(position).getTipologia().equals("asta al ribasso")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_ribasso, null);
            TextView titoloTextView = (TextView) convertView.findViewById(R.id.titoloRibassoTextView);
            TextView conclusaRibassoTextView = (TextView) convertView.findViewById(R.id.conclusaRibassoTextView);
            productImage = (ImageView) convertView.findViewById(R.id.productRibassoImageView);
            titoloTextView.setText(aste.get(position).getNomeProdotto());
            conclusaRibassoTextView.setVisibility(View.INVISIBLE);

            Calendar calendar = Calendar.getInstance();
            Date dataOraAttuale = calendar.getTime();
            calendar.setTime(dataOraAttuale);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            try {
                date = inputFormat.parse(aste.get(position).getDataScadenzaTF());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long timer = date.getTime() - dataOraAttuale.getTime();

            // GESTIONE TIMER
            Chronometer chronometer = convertView.findViewById(R.id.chronometerRibasso);
            long elapsedTime = SystemClock.elapsedRealtime() + timer;
            chronometer.setBase(elapsedTime);
            chronometer.start();

            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if(chronometer.getBase() < SystemClock.elapsedRealtime() + 10000) {
                        chronometer.setTextColor(Color.RED);
                    }
                    if(chronometer.getBase() < SystemClock.elapsedRealtime() + 1) {
                        chronometer.stop();
                        conclusaRibassoTextView.setVisibility(View.VISIBLE);
                        chronometer.setVisibility(View.INVISIBLE);
                        //manda notifiche

                    }
                }
            });


            // Decodifica la stringa Base64 e imposta l'immagine solo se la stringa non è vuota o nulla
            if (aste.get(position).getFotoProdotto() != null && !aste.get(position).getFotoProdotto().isEmpty()) {
                imageBase64 = aste.get(position).getFotoProdotto();
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                productImage.setImageBitmap(decodedByte);
            } else {
                // Immagine di fallback o gestisci la situazione come desideri
                productImage.setImageResource(R.drawable.shopping_bag);
            }

            productImage.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToInformazioniAsta = new Intent(context, AstaIngleseActivity.class);
                goToInformazioniAsta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line
                context.startActivity(goToInformazioniAsta);
            }
        });

        return convertView;
    }
}
