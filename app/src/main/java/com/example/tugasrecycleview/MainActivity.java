package com.example.tugasrecycleview;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tugasrecycleview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // action bar
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_alarm);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity.this,
                            MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_message) {
                    Intent b = new Intent(MainActivity.this,
                            MainActivity1.class);
                    startActivity(b);
                } else if (id == R.id.nav_chat) {
                    Intent c = new Intent(MainActivity.this,
                            MainActivity2.class);
                    startActivity(c);
                } else if (id == R.id.nav_profile) {
                    Intent d = new Intent(MainActivity.this,
                            MainActivity3.class);
                    startActivity(d);
                }
                else if (id == R.id.nav_sql) {
                    Intent e = new Intent(MainActivity.this,
                            TambahAlbum.class);
                    startActivity(e);
                }
                else if (id == R.id.nav_api) {
                    Intent f = new Intent(MainActivity.this,
                            ApiActivity.class);
                    startActivity(f);
                }
                return true;
            }
        });

        //set alarm
        createNotificationChannel();
        binding.selectedTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        binding.setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });
        binding.cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);
        if (alarmManager == null) {
            alarmManager = (AlarmManager)
                    getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled",
                Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm Set Successfully",
                Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(), "AlarmManager");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picker.getHour() > 12) {
                    binding.selectedTime.setText(
                            String.format("%02d : %02d",
                                    picker.getHour(), picker.getMinute())
                    );
                } else {
                    binding.selectedTime.setText(picker.getHour()
                            + " : " + picker.getMinute() + " ");
                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,
                        picker.getHour());
                calendar.set(Calendar.MINUTE,
                        picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });
    }

    private void createNotificationChannel() {
        CharSequence name = "2018091";
        String description = "Info Terbaru!!!";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new
                NotificationChannel("AlarmManager", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
}