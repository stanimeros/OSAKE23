package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class topfive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topfive);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomenu();
            }
        });

        try{
            ArrayList<String> Player12 = MySQL.getTopPlayers("three_points");
            ArrayList<String> Player34 = MySQL.getTopPlayers("assists");
            ArrayList<String> Player5 = MySQL.getTopPlayers("rebounds");

            ArrayList<String> topPlayersInfo = new ArrayList<>();
            for (int i=0;i<12;i++)
            {
                topPlayersInfo.add(Player12.get(i));
            }

            for (int i=0;i<2;i++)
            {
                for (int j=0;j<Player34.size();j=j+6)
                {
                    if (!topPlayersInfo.contains(Player34.get(j)))
                    {
                        for (int k=j;k<j+6;k++)
                        {
                            topPlayersInfo.add(Player34.get(k));
                        }
                        break;
                    }
                }
            }

            for (int i=0;i<Player5.size();i=i+6)
            {
                if (!topPlayersInfo.contains(Player5.get(i)))
                {
                    for (int k=i;k<i+6;k++)
                    {
                        topPlayersInfo.add(Player5.get(k));
                    }
                    break;
                }
            }

            //1
            TextView t1 = findViewById(R.id.player1);
            TextView t2 = findViewById(R.id.pos1);
            TextView t3 = findViewById(R.id.team1);
            TextView t4 = findViewById(R.id.losses);
            TextView t5 = findViewById(R.id.rounds);
            TextView t6 = findViewById(R.id.points1);

            int i=0;
            t1.setText(topPlayersInfo.get(0));
            t2.setText(topPlayersInfo.get(1));
            t3.setText(topPlayersInfo.get(2));
            t4.setText(topPlayersInfo.get(3));
            t5.setText(topPlayersInfo.get(4));
            t6.setText(topPlayersInfo.get(5));

            //2
            t1 = findViewById(R.id.player2);
            t2 = findViewById(R.id.position2);
            t3 = findViewById(R.id.team2);
            t4 = findViewById(R.id.rebaunts2);
            t5 = findViewById(R.id.assists2);
            t6 = findViewById(R.id.Points2);

            i=i+6;
            t1.setText(topPlayersInfo.get(i));
            t2.setText(topPlayersInfo.get(1+i));
            t3.setText(topPlayersInfo.get(2+i));
            t4.setText(topPlayersInfo.get(3+i));
            t5.setText(topPlayersInfo.get(4+i));
            t6.setText(topPlayersInfo.get(5+i));

            //3
            t1 = findViewById(R.id.player3);
            t2 = findViewById(R.id.position3);
            t3 = findViewById(R.id.team3);
            t4 = findViewById(R.id.rebaunts3);
            t5 = findViewById(R.id.assists3);
            t6 = findViewById(R.id.Points3);

            i=i+6;
            t1.setText(topPlayersInfo.get(i));
            t2.setText(topPlayersInfo.get(1+i));
            t3.setText(topPlayersInfo.get(2+i));
            t4.setText(topPlayersInfo.get(3+i));
            t5.setText(topPlayersInfo.get(4+i));
            t6.setText(topPlayersInfo.get(5+i));

            //4
            t1 = findViewById(R.id.player4);
            t2 = findViewById(R.id.position4);
            t3 = findViewById(R.id.team4);
            t4 = findViewById(R.id.rebaunts4);
            t5 = findViewById(R.id.assists4);
            t6 = findViewById(R.id.Points4);

            i=i+6;
            t1.setText(topPlayersInfo.get(i));
            t2.setText(topPlayersInfo.get(1+i));
            t3.setText(topPlayersInfo.get(2+i));
            t4.setText(topPlayersInfo.get(3+i));
            t5.setText(topPlayersInfo.get(4+i));
            t6.setText(topPlayersInfo.get(5+i));

            //5
            t1 = findViewById(R.id.player5);
            t2 = findViewById(R.id.position5);
            t3 = findViewById(R.id.team5);
            t4 = findViewById(R.id.rebaunts5);
            t5 = findViewById(R.id.assists5);
            t6 = findViewById(R.id.Points5);

            i=i+6;
            t1.setText(topPlayersInfo.get(i));
            t2.setText(topPlayersInfo.get(1+i));
            t3.setText(topPlayersInfo.get(2+i));
            t4.setText(topPlayersInfo.get(3+i));
            t5.setText(topPlayersInfo.get(4+i));
            t6.setText(topPlayersInfo.get(5+i));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void gotomenu(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }
}