package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.customs.CustomBaseAdapterNotifications;
import com.example.dietideals24.R;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.customs.SingleRowListNotifications;
import com.example.dietideals24.models.Notifica;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    int checkOrXIcons[] = {R.mipmap.ic_check_2_foreground, R.mipmap.ic_cross_foreground};
    ListView listView;
    CustomBaseAdapterNotifications customBaseAdapterNotifications;
    private MyApiService apiService;
    ArrayList<Notifica> notifiche = new ArrayList<Notifica>();
    private String nickname;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");

        Button backBtn = findViewById(R.id.backButtonNotifiche);
        listView = (ListView) findViewById(R.id.customListView);

        setNotificheALette();
        recuperaNotifiche();

        /*
        customBaseAdapterNotifications = new CustomBaseAdapterNotifications(getApplicationContext(), notifiche, checkOrXIcons);
        listView.setAdapter(customBaseAdapterNotifications);
        SingleRowListNotifications.setListViewHeightBasedOnChildren(listView);
        customBaseAdapterNotifications.notifyDataSetChanged();

         */


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("compratore")){
                    Intent backToHomeCompratore = new Intent(NotificationsActivity.this, HomepageCompratoreActivity.class);
                    backToHomeCompratore.putExtra("nickname", nickname);
                    backToHomeCompratore.putExtra("tipo", tipo);
                    startActivity(backToHomeCompratore);
                } else if (tipo.equals("venditore")) {
                    Intent backToHomeVenditore = new Intent(NotificationsActivity.this, HomepageVenditoreActivity.class);
                    backToHomeVenditore.putExtra("nickname", nickname);
                    backToHomeVenditore.putExtra("tipo", tipo);
                    startActivity(backToHomeVenditore);
                }


            }
        });
    }

    public void setNotificheALette(){
        Call<Void> call = apiService.setNotificheALette(nickname);
        call.enqueue(new Callback<Void>(){
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

    public void recuperaNotifiche(){
        Call<ArrayList<Notifica>> call = apiService.getNotifiche(nickname);
        call.enqueue(new Callback<ArrayList<Notifica>>() {
            @Override
            public void onResponse(Call<ArrayList<Notifica>> call, Response<ArrayList<Notifica>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    notifiche.clear();
                    notifiche.addAll(response.body());
                    // Aggiorna la ListView con i nuovi dati custom
                    customBaseAdapterNotifications = new CustomBaseAdapterNotifications(getApplicationContext(), notifiche, checkOrXIcons);
                    listView.setAdapter(customBaseAdapterNotifications);
                    //SingleRowListNotifications.setListViewHeightBasedOnChildren(listView);
                    customBaseAdapterNotifications.notifyDataSetChanged();

                } else {
                    Toast.makeText(NotificationsActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notifica>> call, Throwable t) {
                Toast.makeText(NotificationsActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(tipo.equals("compratore")){
            Intent backToHomeCompratore = new Intent(NotificationsActivity.this, HomepageCompratoreActivity.class);
            backToHomeCompratore.putExtra("nickname", nickname);
            backToHomeCompratore.putExtra("tipo", tipo);
            startActivity(backToHomeCompratore);
        } else if (tipo.equals("venditore")) {
            Intent backToHomeVenditore = new Intent(NotificationsActivity.this, HomepageVenditoreActivity.class);
            backToHomeVenditore.putExtra("nickname", nickname);
            backToHomeVenditore.putExtra("tipo", tipo);
            startActivity(backToHomeVenditore);
        }
    }
}