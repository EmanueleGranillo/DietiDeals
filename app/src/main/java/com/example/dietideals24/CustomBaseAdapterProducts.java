package com.example.dietideals24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapterProducts extends BaseAdapter {
    Context context;
    ArrayList<Asta> aste;
    int productsImages[];
    LayoutInflater inflater;

    public CustomBaseAdapterProducts (Context ctx, ArrayList<Asta> aste, int [] productsImages){
        this.context = ctx;
        this.aste = aste;
        this.productsImages = productsImages;
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

        if(aste.get(position).getTipologia().equals("Asta a tempo fisso")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_t_f, null);
            TextView titolo = (TextView) convertView.findViewById(R.id.titleTF);
            TextView tipologia = (TextView) convertView.findViewById(R.id.tipologiaAstaValueTF);
            TextView data = (TextView) convertView.findViewById(R.id.expirationTextViewValueTF);
            TextView prezzoAttuale = (TextView) convertView.findViewById(R.id.valoreCorrenteTF);
            ImageView productImage = (ImageView) convertView.findViewById(R.id.imageProduct);
            titolo.setText(aste.get(position).getTitoloAsta());
            tipologia.setText(aste.get(position).getTipologia());
            // data.setText(aste.get(position).getDataFineAstaTempoFisso()); problemi con il parse, poi si vede
            prezzoAttuale.setText(aste.get(position).getOffertaAttuale().toString() + "$");
            productImage.setImageResource(productsImages[position]);
        }
        if(aste.get(position).getTipologia().equals("Asta all'inglese")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_english, null);
            TextView titolo = (TextView) convertView.findViewById(R.id.titleIng);
            TextView tipologia = (TextView) convertView.findViewById(R.id.tipologiaAstaValueIng);
            TextView prezzoAttuale = (TextView) convertView.findViewById(R.id.valoreCorrenteIng);
            TextView tempoRimanente = (TextView) convertView.findViewById(R.id.timeLeftValueIng);
            TextView sogliaRialzo = (TextView) convertView.findViewById(R.id.sogliaRialzoValueIng);
            ImageView productImage = (ImageView) convertView.findViewById(R.id.imageProductIng);
            titolo.setText(aste.get(position).getTitoloAsta());
            tipologia.setText(aste.get(position).getTipologia());
            prezzoAttuale.setText(aste.get(position).getOffertaAttuale().toString() + "$");
            tempoRimanente.setText(aste.get(position).getTimer().toString());
            sogliaRialzo.setText(aste.get(position).getSogliaRialzoMinima().toString() + "$");
            productImage.setImageResource(productsImages[position]);
        }
        if(aste.get(position).getTipologia().equals("Asta al ribasso")){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_product_ribasso, null);
            TextView titolo = (TextView) convertView.findViewById(R.id.titleRibasso);
            TextView tipologia = (TextView) convertView.findViewById(R.id.tipologiaAstaValueRibasso);
            TextView prezzoAttuale = (TextView) convertView.findViewById(R.id.valoreCorrenteRibasso);
            TextView tempoRimanente = (TextView) convertView.findViewById(R.id.decrementoTimerValueRibasso);
            TextView sogliaDecremento = (TextView) convertView.findViewById(R.id.sogliaDecrementoValueRibasso);
            ImageView productImage = (ImageView) convertView.findViewById(R.id.imageProductRibasso);
            titolo.setText(aste.get(position).getTitoloAsta());
            tipologia.setText(aste.get(position).getTipologia());
            prezzoAttuale.setText(aste.get(position).getPrezzoVendita().toString() + "$");
            tempoRimanente.setText(aste.get(position).getTimer().toString());
            sogliaDecremento.setText(aste.get(position).getImportoDecremento().toString() + "$");
            productImage.setImageResource(productsImages[position]);
        }

//        convertView = inflater.inflate(R.layout.activity_custom_list_view_product_english, null);
        return convertView;
    }
}
