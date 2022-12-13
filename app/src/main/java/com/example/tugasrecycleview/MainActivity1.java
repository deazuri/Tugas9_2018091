package com.example.tugasrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.tugasrecycleview.databinding.ActivityMain1Binding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity1 extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityMain1Binding binding;

    RecyclerView recylerView;

    String s1[], s2[], s3[];
    int images[] =
            {R.drawable.perfectvelvet, R.drawable.tempo, R.drawable.universe, R.drawable.psycho};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final OneTimeWorkRequest request = new
                OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork(
                        "Notifikasi", ExistingWorkPolicy.REPLACE,
                        request);
            }
        });
//        setContentView(R.layout.activity_main1);
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity1.this,
                            MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_message) {
                    Intent b = new Intent(MainActivity1.this,
                            MainActivity1.class);
                    startActivity(b);
                } else if (id == R.id.nav_chat) {
                    Intent c = new Intent(MainActivity1.this,
                            MainActivity2.class);
                    startActivity(c);
                } else if (id == R.id.nav_profile) {
                    Intent d = new Intent(MainActivity1.this,
                            MainActivity3.class);
                    startActivity(d);
                }
                else if (id == R.id.nav_sql) {
                    Intent e = new Intent(MainActivity1.this,
                            TambahAlbum.class);
                    startActivity(e);
                }else if (id == R.id.nav_api) {
                    Intent f = new Intent(MainActivity1.this,
                            ApiActivity.class);
                    startActivity(f);
                }
                return true;
            }
        });
        recylerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.album);
        s2 = getResources().getStringArray(R.array.deskripsi);
        s3 = getResources().getStringArray(R.array.star);
        AlbumAdapter appAdapter = new AlbumAdapter(this, s1, s2, s3, images);
        recylerView.setAdapter(appAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this), RecyclerView.HORIZONTAL, false);
        recylerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

    }
//}