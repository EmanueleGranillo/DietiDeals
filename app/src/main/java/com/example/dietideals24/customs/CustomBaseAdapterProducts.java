package com.example.dietideals24.customs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietideals24.activities.AstaIngleseActivity;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.R;

import java.util.ArrayList;
import java.util.Date;

public class CustomBaseAdapterProducts extends BaseAdapter {
    Context context;
    ArrayList<Asta> aste;
    LayoutInflater inflater;

    ImageView productImage;

    String imageBase64, dateString;
    Date date;
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
            scadenzaDataTextView = (TextView) convertView.findViewById(R.id.dataScadenzaValueTextView);
            prezzoAttualeTextView = (TextView) convertView.findViewById(R.id.valoreCorrenteTFTextView);
            productImage = (ImageView) convertView.findViewById(R.id.productTFImageView);
            titoloTextView.setText(aste.get(position).getNomeProdotto());
            scadenzaDataTextView.setText(aste.get(position).getDataScadenzaTF());
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
            TextView prezzoAttualeTextView = (TextView) convertView.findViewById(R.id.baseAstaIngleseValueTextView);
            TextView tempoRimanente = (TextView) convertView.findViewById(R.id.scadenzaIngleseValueTextView);
            TextView sogliaRialzo = (TextView) convertView.findViewById(R.id.sogliaRialzoValueTextView);
            productImage = (ImageView) convertView.findViewById(R.id.productIngleseImageView);
            titoloTextView.setText(aste.get(position).getNomeProdotto());
            //prezzoAttualeTextView.setText(aste.get(position).getOffertaAttuale().toString() + "€");
            //tempoRimanente.setText(aste.get(position).getTimer().toString());
            sogliaRialzo.setText(aste.get(position).getSogliaRialzoMinima().toString() + "€");

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
            TextView prezzoAttualeTextView = (TextView) convertView.findViewById(R.id.valoreCorrenteRibasso);
            //TextView tempoRimanente = (TextView) convertView.findViewById(R.id.decrementoTimerValueRibasso);
            TextView sogliaDecremento = (TextView) convertView.findViewById(R.id.sogliaDecrementoValueRibasso);
            productImage = (ImageView) convertView.findViewById(R.id.productRibassoImageView);
            titoloTextView.setText(aste.get(position).getNomeProdotto());
            /*if(!aste.get(position).getOffertaAttuale().toString().isEmpty()){
            //              prezzoAttuale.setText(aste.get(position).getOffertaAttuale().toString() + "€");
            //}
            //else {
              //  prezzoAttuale.setText("Nessun offerta");
            //}
            //tempoRimanente.setText(aste.get(position).getTimer().toString());
            sogliaDecremento.setText(aste.get(position).getImportoDecremento().toString() + "€");

             */
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
