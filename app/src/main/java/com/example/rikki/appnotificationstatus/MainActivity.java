package com.example.rikki.appnotificationstatus;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button, navButton;
    TextView text;
    Intent notifyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        navButton = findViewById(R.id.navButton);
        notifyIntent = new Intent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled;
                if (Build.VERSION.SDK_INT >= 24) {
                    isEnabled = NotificationManagerCompat.from(MainActivity.this).areNotificationsEnabled();
                } else
                    isEnabled = NotificationsUtils.isNotificationEnabled(MainActivity.this);
                if (isEnabled) {
                    text.setText("ON");
                } else {
                    text.setText("OFF");
                }
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notifyIntent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notifyIntent.putExtra("app_package", getPackageName());
                    notifyIntent.putExtra("app_uid", getApplicationInfo().uid);
                }
                startActivity(notifyIntent);
            }
        });
    }
}
