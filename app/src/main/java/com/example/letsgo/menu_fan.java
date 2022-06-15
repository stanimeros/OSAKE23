package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class menu_fan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fan);

        Button matches = findViewById(R.id.matchesoftheday);
        Button topfive = findViewById(R.id.topfive);
        Button teamScores = findViewById(R.id.teamScores);
        Button statistika = findViewById(R.id.statistika);
        ImageView backbtn = findViewById(R.id.back);

        TextView title = findViewById(R.id.title);
        try {
            title.setText(MySQL.getCurrentRound()+"η Αγωνιστική");
        }catch (Exception e){
            System.out.println(e);
        }

        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomatches();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotologin();
            }
        });

        topfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gototopfive();
            }
        });

        teamScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gototeamScores();
            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPrevMatches();
            }
        });

    }
    private void gotomatches(){
        Intent intent = new Intent(this, matches.class);
        startActivity(intent);
    }

    private void gotologin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void gototopfive(){
        Intent intent = new Intent(this, topfive.class);
        startActivity(intent);
    }

    private void gototeamScores(){
        Intent intent = new Intent(this, teamScores.class);
        startActivity(intent);
    }

    private void gotoPrevMatches(){
        Intent intent = new Intent(this, previous_games.class);
        startActivity(intent);
    }
}