package com.example.tugasrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

    dl.addDrawerListener(abdt);
    abdt.syncState();

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

    nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_alarm) {
                Intent a = new Intent(MainActivity2.this,
                        MainActivity.class);
                startActivity(a);
            } else if (id == R.id.nav_message) {
                Intent b = new Intent(MainActivity2.this,
                        MainActivity1.class);
                startActivity(b);
            } else if (id == R.id.nav_chat) {
                Intent c = new Intent(MainActivity2.this,
                        MainActivity2.class);
                startActivity(c);
            } else if (id == R.id.nav_profile) {
                Intent d = new Intent(MainActivity2.this,
                        MainActivity3.class);
                startActivity(d);
            }
            else if (id == R.id.nav_sql) {
                Intent e = new Intent(MainActivity2.this,
                        TambahAlbum.class);
                startActivity(e);
            }else if (id == R.id.nav_api) {
                Intent f = new Intent(MainActivity2.this,
                        ApiActivity.class);
                startActivity(f);
            }
            return true;
        }
    });
    }

    @Override     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    } }