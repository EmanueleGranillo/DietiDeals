package com.example.dietideals24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CustomBaseAdapterProducts extends BaseAdapter {
    Context context;
    Asta listaAsta[];
    LayoutInflater inflater;

    public CustomBaseAdapterProducts (Context ctx, Asta[] asta){
        this.context = ctx;
        listaAsta = asta;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listaAsta.length;
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
        return null;
    }
}
