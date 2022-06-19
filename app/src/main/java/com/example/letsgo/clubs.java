package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class clubs extends AppCompatActivity {

//    LinearLayout layoutList;
//    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotomenufan();}
        });

        TextView tv = findViewById(R.id.title);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoclubstats();}
        });

//        layoutList = findViewById(R.id.layoutList);
//        try {
//            ArrayList<String> teams = MySQL.getTeams();
//            for (int i=0;i<teams.size();i++)
//            {
//                addView(teams.get(i));
//            }
//        }catch (Exception e){
//            System.out.println(e);
//        }

    }

//    private void addView(String n) {
//        View buttonsView = getLayoutInflater().inflate(R.layout.match_button, null, false);
//
//        Button b = buttonsView.findViewById(R.id.Match_1);
//        b.setText(n);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name = n;
//                gotoclubstats();
//            }
//        });
//
//        buttonsView.setId(View.generateViewId());
//        layoutList.addView(buttonsView);
//    }

    private void gotomenufan(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void gotoclubstats(){
        Intent intent = new Intent(this, club_stats.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("name",name);
//        intent.putExtras(bundle);
        startActivity(intent);
    }
}