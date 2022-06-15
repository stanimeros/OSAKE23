package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class previous_games extends AppCompatActivity {

    LinearLayout layoutList;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_games);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotomenufan();}
        });

        Button matchBtn = findViewById(R.id.override);
        matchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotomatches();}
        });

        layoutList = findViewById(R.id.layoutList);

        try {
            for (int i=MySQL.getCurrentRound();i>=0;i--)
            {
                ArrayList<String> matches = MySQL.getMatchIdsFinished(i);
                for (int j=0;j<matches.size();j++)
                {
                    Match m = MySQL.getMatch(Integer.valueOf(matches.get(j)));
                    addView(m);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void addView(Match m){
        View buttonsView = getLayoutInflater().inflate(R.layout.match_button, null, false);

        Button b = buttonsView.findViewById(R.id.Match_1);
        b.setText(m.getHome()+"  -  "+m.getAway());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = m.getRound();
                gotomatches();
            }
        });

        buttonsView.setId(View.generateViewId());
        layoutList.addView(buttonsView);
    }

    private void gotomenufan(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void gotomatches(){
        Intent intent = new Intent(this, previous_matches_statistics.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}