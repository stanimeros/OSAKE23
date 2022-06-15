package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class menu_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        ImageView backbtn = findViewById(R.id.back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotologin();
            }
        });

        Button gamesbtn = findViewById(R.id.gamesbtn);

        gamesbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                gotomyAdminMatches();
            }
        });

        Button cat = findViewById(R.id.createAteam);

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCreateATeam();
            }
        });

        Button cap = findViewById(R.id.createAplayer);

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCreateAPlayer();
            }
        });

        Button insert = findViewById(R.id.incert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Championship c = new Championship();
                displayToast();
            }
        });
    }

    private void gotoCreateATeam(){
        Intent intent = new Intent(this, create_a_team.class);
        startActivity(intent);
    }

    private void gotoCreateAPlayer(){
        Intent intent = new Intent(this, create_a_player.class);
        startActivity(intent);
    }

    private void gotomyAdminMatches(){
        Intent intent = new Intent(this, admin_matches.class);
        startActivity(intent);
    }

    private void gotologin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void displayToast(){
        Toast.makeText(menu_admin.this,"The league is been successfully created", Toast.LENGTH_SHORT).show();
    }
}