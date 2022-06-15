package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class teamScores extends AppCompatActivity {

    ArrayList<String> scoreboard;
    LinearLayout layoutList;
    int tableColumns = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_scores);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomenu();
            }
        });

        layoutList = findViewById(R.id.layoutList);

        scoreboard = MySQL.getScoreboard();
        for(int i=0;i<scoreboard.size();i=i+tableColumns){
            addView(i);
        }
    }

    private void gotomenu(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void addView(int i){

        View teamsView = getLayoutInflater().inflate(R.layout.team_scores_view, null, false);

        TextView t0 = teamsView.findViewById(R.id.number1);
        t0.setText(""+(i+tableColumns)/5);

        TextView t1 = teamsView.findViewById(R.id.team1);
        t1.setText(scoreboard.get(i));

        TextView t2= teamsView.findViewById(R.id.minutes1);
        t2.setText(scoreboard.get(i+1));

        TextView t3 = teamsView.findViewById(R.id.rebaunts1);
        t3.setText(scoreboard.get(i+2));

        TextView t4 = teamsView.findViewById(R.id.assists1);
        t4.setText(scoreboard.get(i+3));

        TextView t5 = teamsView.findViewById(R.id.Points1);
        t5.setText(scoreboard.get(i+4));

        teamsView.setId(View.generateViewId());
        layoutList.addView(teamsView);
    }
}