package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dietideals24.connection.NicknameRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.customs.CustomBaseAdapterNotifications;
import com.example.dietideals24.R;
import com.example.dietideals24.customs.SingleRowListNotifications;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.models.Notifica;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    String notificationsTitleList[] = {"Hai vinto", "Hai perso"};
    int checkOrXIcons[] = {R.drawable.ic_check_foreground, R.drawable.ic_x_foreground};
    ListView listView;
    CustomBaseAdapterNotifications customBaseAdapter;
    private MyApiService apiService;
    ArrayList<Notifica> notifiche = new ArrayList<Notifica>();

    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        nickname = getIntent().getStringExtra("nickname");

        Button backBtn = findViewById(R.id.backButtonNotifiche);
        listView = (ListView) findViewById(R.id.customListView);
        customBaseAdapter = new CustomBaseAdapterNotifications(getApplicationContext(), notificationsTitleList, checkOrXIcons);
        listView.setAdapter(customBaseAdapter);
        SingleRowListNotifications.setListViewHeightBasedOnChildren(listView);

        setNotificheALette();
        recuperaNotifiche();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(NotificationsActivity.this, HomepageCompratoreActivity.class);
                backToHome.putExtra("nickname", nickname);
                startActivity(backToHome);
            }
        });
    }

    public void setNotificheALette(){
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<Void> call = apiService.setNotificheALette(nicknameRequest);
        call.enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NotificationsActivity.this, "appoooo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NotificationsActivity.this, "male male", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void recuperaNotifiche(){
        NicknameRequest nicknameRequest = new NicknameRequest(nickname);
        Call<ArrayList<Notifica>> call = apiService.getNotifiche(nicknameRequest);
        call.enqueue(new Callback<ArrayList<Notifica>>() {
            @Override
            public void onResponse(Call<ArrayList<Notifica>> call, Response<ArrayList<Notifica>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    notifiche.clear();
                    notifiche.addAll(response.body());
                    // Aggiorna la ListView con i nuovi dati custom
                    customBaseAdapter.notifyDataSetChanged();

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

}