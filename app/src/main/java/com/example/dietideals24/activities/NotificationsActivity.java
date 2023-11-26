package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dietideals24.customs.CustomBaseAdapterNotifications;
import com.example.dietideals24.R;
import com.example.dietideals24.customs.SingleRowListNotifications;

public class NotificationsActivity extends AppCompatActivity {
    String notificationsTitleList[] = {"Hai vinto", "Hai perso"};
    int checkOrXIcons[] = {R.drawable.ic_check_foreground, R.drawable.ic_x_foreground};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        listView = (ListView) findViewById(R.id.customListView);
        CustomBaseAdapterNotifications customBaseAdapter = new CustomBaseAdapterNotifications(getApplicationContext(), notificationsTitleList, checkOrXIcons);
        listView.setAdapter(customBaseAdapter);
        SingleRowListNotifications.setListViewHeightBasedOnChildren(listView);


        Button backBtn = findViewById(R.id.backButtonNotifiche);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(NotificationsActivity.this, HomepageCompratoreActivity.class);
                startActivity(backToHome);
            }
        });
    }
}