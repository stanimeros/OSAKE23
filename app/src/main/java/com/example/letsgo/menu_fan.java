package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class menu_fan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fan);

        Button clubstats = findViewById(R.id.clubstats);
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

        clubstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoclubs();
            }
        });

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
                if (MySQL.getCurrentRound()>1) {
                    gototopfive();
                }else
                {
                    Toast.makeText(menu_fan.this,"No data yet!", Toast.LENGTH_SHORT).show();
                }
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

    private void gotoclubs(){
        Intent intent = new Intent(this, clubs.class);
        startActivity(intent);
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