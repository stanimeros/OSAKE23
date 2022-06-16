package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class clubs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotomenufan();}
        });

        TextView btn = findViewById(R.id.title);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoclubstats();}
        });

    }

    private void gotomenufan(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void gotoclubstats(){
        Intent intent = new Intent(this, club_stats.class);
        startActivity(intent);
    }
}