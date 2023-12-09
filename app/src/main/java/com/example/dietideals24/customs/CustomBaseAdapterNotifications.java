package com.example.dietideals24.customs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietideals24.R;
import com.example.dietideals24.models.Notifica;

import java.util.ArrayList;

public class CustomBaseAdapterNotifications extends BaseAdapter {

     Context context;
     ArrayList<Notifica> notifiche;
     int checkOrXIcons[];
     LayoutInflater inflater;

     public CustomBaseAdapterNotifications(Context ctx, ArrayList<Notifica> notifiche, int [] checkOrXIcons) {
         this.context = ctx;
         this.notifiche = notifiche;
         this.checkOrXIcons = checkOrXIcons;
         inflater = LayoutInflater.from(ctx);
     }


    @Override
    public int getCount() {
        return notifiche.size();
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

        if(notifiche.get(position).getIntestazione().equals("Hai vinto!")){
            convertView = inflater.inflate(R.layout.activity_single_row_list_notifications, null);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            TextView textViewDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
            ImageView checkOrXImages = (ImageView) convertView.findViewById(R.id.imageIconCheck);

            textViewTitle.setText(notifiche.get(position).getIntestazione());
            textViewDescription.setText(notifiche.get(position).getDescrizione());
            checkOrXImages.setImageResource(checkOrXIcons[0]);
        }

        if(notifiche.get(position).getIntestazione().equals("Hai perso!")){
            convertView = inflater.inflate(R.layout.activity_single_row_list_notifications, null);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            TextView textViewDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
            ImageView checkOrXImages = (ImageView) convertView.findViewById(R.id.imageIconCheck);

            textViewTitle.setText(notifiche.get(position).getIntestazione());
            textViewDescription.setText(notifiche.get(position).getDescrizione());
            checkOrXImages.setImageResource(checkOrXIcons[1]);
        }


        if(notifiche.get(position).getDescrizione().equals("Prodotto venduto!")){
            convertView = inflater.inflate(R.layout.activity_single_row_list_notifications, null);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            TextView textViewDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
            ImageView checkOrXImages = (ImageView) convertView.findViewById(R.id.imageIconCheck);

            textViewTitle.setText(notifiche.get(position).getIntestazione());
            textViewDescription.setText(notifiche.get(position).getDescrizione());
            checkOrXImages.setImageResource(checkOrXIcons[0]);
        }

        if(notifiche.get(position).getDescrizione().equals("Prodotto non venduto!")){
            convertView = inflater.inflate(R.layout.activity_single_row_list_notifications, null);
            TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            TextView textViewDescription = (TextView) convertView.findViewById(R.id.textViewDescription);
            ImageView checkOrXImages = (ImageView) convertView.findViewById(R.id.imageIconCheck);

            textViewTitle.setText(notifiche.get(position).getIntestazione());
            textViewDescription.setText(notifiche.get(position).getDescrizione());
            checkOrXImages.setImageResource(checkOrXIcons[1]);
        }


        return convertView;
    }
}
