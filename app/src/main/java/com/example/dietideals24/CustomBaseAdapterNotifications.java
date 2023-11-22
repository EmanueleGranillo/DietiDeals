package com.example.dietideals24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapterNotifications extends BaseAdapter {

     Context context;
     String notificationsTitleList[];
     int checkOrXIcons[];
     LayoutInflater inflater;

     public CustomBaseAdapterNotifications(Context ctx, String[] notificationsTitleList, int [] checkOrXIcons) {
         this.context = ctx;
         this.notificationsTitleList = notificationsTitleList;
         this.checkOrXIcons = checkOrXIcons;
         inflater = LayoutInflater.from(ctx);
     }


    @Override
    public int getCount() {
        return notificationsTitleList.length;
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
         convertView = inflater.inflate(R.layout.activity_single_row_list_notifications, null);
         TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
         ImageView checkOrXImages = (ImageView) convertView.findViewById(R.id.imageIconCheck);
         textViewTitle.setText(notificationsTitleList[position]);
         checkOrXImages.setImageResource(checkOrXIcons[position]);
         return convertView;
    }
}
