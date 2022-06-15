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

public class admin_matches extends AppCompatActivity {

    int id;
    LinearLayout layoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        TextView title = findViewById(R.id.title);
        try {
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
                gotomanagement();
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

            if (matches.size()==0 && !MySQL.AllFinished())
            {
                MySQL.setCurrentRound(MySQL.getCurrentRound()+1);
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
                gotomanagement();
            }
        });

        buttonsView.setId(View.generateViewId());
        layoutList.addView(buttonsView);
    }

    private void gotomanagement(){
        Intent intent = new Intent(this, admin.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void gotomenu(){
        Intent intent = new Intent(this, menu_admin.class);
        startActivity(intent);
    }
}