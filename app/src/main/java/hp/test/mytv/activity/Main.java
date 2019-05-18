package hp.test.mytv.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.List;

import hp.test.mytv.adapter.OnAirAdapter;
import hp.test.mytv.R;
import hp.test.mytv.model.on_air.OnAirItem;
import hp.test.mytv.model.on_air.OnAirResult;
import hp.test.mytv.utils.APIClient;
import hp.test.mytv.utils.TMDBInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.res.Configuration;
import java.util.Locale;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar tb;

    OnAirResult onAirResult;
    List<OnAirItem>  onAirItems = new ArrayList<OnAirItem>();

    RecyclerView recyclerView;
    OnAirAdapter mAdapter;
    ProgressBar loadingLayer;

    TMDBInterface tmdbInterface;
    private Locale locale;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NavigationView navView = findViewById(R.id.nav_view);

        //Initialize Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Recycle view

        loadingLayer = findViewById(R.id.progressPanel);

        recyclerView = findViewById(R.id.rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new OnAirAdapter(onAirItems);

        //Initialize drawer

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Api Client

        tmdbInterface = APIClient.getClient().create(TMDBInterface.class);

        recyclerView.setAdapter(mAdapter);

        //Initialize Data

        refreshRv();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastCompletelyVisibleItemPosition()==onAirItems.size()-1){
                    loadingLayer.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    refreshRv();
                }
            }
        });


        //


    }

    private void refreshRv(){
        int nextPage = 0;
        if (this.onAirResult!=null){
            if (this.onAirResult.getTotalPages()>nextPage) {
                nextPage = this.onAirResult.getPage() + 1;
            }

        }else{
            nextPage=1;
        }

        final Call<OnAirResult> onAirResultCall = tmdbInterface.getOnAir(nextPage);

        onAirResultCall.enqueue(new Callback<OnAirResult>() {
            @Override
            public void onResponse(@NonNull Call<OnAirResult> call, @NonNull Response<OnAirResult> response) {
                assert response.body() != null;
                setData(response.body());
                loadingLayer.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<OnAirResult> call, @NonNull Throwable t) {

            }
        });
    }

    private void setData(OnAirResult onAirResult){
        this.onAirResult = onAirResult;
        this.onAirItems.addAll(onAirResult.getResults());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //@Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
   //     getMenuInflater().inflate(R.menu.main, menu);
   //     return true;
   // }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_setting:
                Intent setting = new Intent(Main.this,Setting.class);
                startActivity(setting);
                break;
            case R.id.nav_favorite:
                Intent favorite = new Intent(Main.this,Favorite.class);
                startActivity(favorite);
                break;
            case R.id.nav_info:
                Intent info = new Intent(Main.this,Info.class);
                startActivity(info);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
