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

public class matches extends AppCompatActivity  {

    LinearLayout layoutList;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        TextView title = findViewById(R.id.title);
        try{
            title.setText(MySQL.getCurrentRound()+"η Αγωνιστική");
        }catch (Exception e){
            System.out.println(e);
        }

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomenu();
            }
        });

        Button ovbtn = findViewById(R.id.override);
        ovbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotogame();
            }
        });

        layoutList = findViewById(R.id.layoutList);

        try {
            ArrayList<String> matches = MySQL.getMatchIdsCurrentRound();
            for (int i=0;i<matches.size();i++)
            {
                Match m = MySQL.getMatch(Integer.valueOf(matches.get(i)));
                addView(m);
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
                id = m.getId();
                gotogame();
            }
        });

        buttonsView.setId(View.generateViewId());
        layoutList.addView(buttonsView);
    }

    private void gotomenu(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void gotogame(){
        Intent intent1 = new Intent(this, ingame.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }
}