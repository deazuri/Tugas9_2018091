package com.example.tugasrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.example.tugasrecycleview.databinding.ActivityApiBinding;
import com.example.tugasrecycleview.databinding.ActivityTambahAlbumBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class ApiActivity extends AppCompatActivity implements View.OnClickListener {
    //declaration variable
    private ActivityApiBinding binding;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    String index;

    //onclik button fetch
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fetch_button) {
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://wikimedia.org/api/rest_v1/metrics/pageviews/per-article/en.wikipedia/all-access/all-agents/Tiger_King/daily/20210901/20210930")
                .buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }

    class DOTask extends AsyncTask<URL, Void, String> {
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //get data json
        public void parseJson(String data) throws JSONException {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            JSONObject innerObj =
//                    jsonObject.getJSONObject("data");
            JSONArray cityArray = jsonObject.getJSONArray("items");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("project").toString();
                if (Sobj.equals(index)){
                    String ID = obj.get("project").toString();
                    String name = obj.get("article").toString();
                    String description = obj.get("granularity").toString();
                    String created_at = obj.get("timestamp").toString();
                    String updated_at = obj.get("access").toString();
                    String qty = obj.get("agent").toString();
                    String price = obj.get("views").toString();
                    binding.resultId.setText(ID);
                    binding.resultName.setText(name);
                    binding.resultDescription.setText(description);
                    binding.resultCreated.setText(created_at);
                    binding.resultUpdated.setText(updated_at);
                    binding.resultQty.setText(qty);
                    binding.resultPrice.setText(price);
                    break;
                }
                else{
                    binding.resultName.setText("Not Found");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);

        // action bar
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_alarm);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_alarm) {
                    Intent a = new Intent(ApiActivity.this,
                            MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_message) {
                    Intent b = new Intent(ApiActivity.this,
                            MainActivity1.class);
                    startActivity(b);
                } else if (id == R.id.nav_chat) {
                    Intent c = new Intent(ApiActivity.this,
                            MainActivity2.class);
                    startActivity(c);
                } else if (id == R.id.nav_profile) {
                    Intent d = new Intent(ApiActivity.this,
                            MainActivity3.class);
                    startActivity(d);
                } else if (id == R.id.nav_sql) {
                    Intent e = new Intent(ApiActivity.this,
                            TambahAlbum.class);
                    startActivity(e);
                } else if (id == R.id.nav_api) {
                    Intent f = new Intent(ApiActivity.this,
                            ApiActivity.class);
                    startActivity(f);
                }
                return true;
            }
        });
    }
}

