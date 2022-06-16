package com.example.letsgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class club_stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_stats);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotomenufan();}
        });

        BottomNavigationView bottomNavigationView;
        club_team_stats club_team_stats = new club_team_stats();
        club_personal_stats club_personal_stats = new club_personal_stats();

        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView5,club_team_stats).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.club_team_stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView5,club_team_stats).commit();
                        return true;
                    case R.id.club_personal_stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView5,club_personal_stats).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void gotomenufan(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }
}