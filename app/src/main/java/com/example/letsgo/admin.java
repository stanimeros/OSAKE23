package com.example.letsgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class admin extends AppCompatActivity {

    long secs = MySQL.START_TIME;
    boolean stopped = false;

    CountDownTimer timer;
    TextView periodboard;
    TextView timeboard;

    Button start;
    Match m;

    Bundle bundle;

    BottomNavigationView bottomNavigationView;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        m = MySQL.getMatch(id);
        TextView t = findViewById(R.id.team1name);
        t.setText(m.getHome());

        TextView t1 = findViewById(R.id.team2name);
        t1.setText(m.getAway());

        TextView score = findViewById(R.id.scoreboard);
        String sc = MySQL.getMatchColumn(m.getId(),"homePoints")+"  -  " + MySQL.getMatchColumn(m.getId(),"awayPoints");
        score.setText(sc);

        periodboard = findViewById(R.id.periodboard);
        periodboard.setText(MySQL.getMatchColumn(id,"period")+"η Περίοδος");

        start = findViewById(R.id.startbtn);
        start.setVisibility(View.GONE);

        timeboard = findViewById(R.id.timeboard);
        timeboard.setText("00:00");
        bundle.putString("timestamp","00:00");

        gamecard gamecard = new gamecard();
        game_management game_management = new game_management();

        gamecard.setArguments(bundle);
        game_management.setArguments(bundle);

        ImageView imgButton = findViewById(R.id.pause);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start.getVisibility()==View.GONE)
                {
                    if (MySQL.getMatchColumn(id,"break")!=0){
                        startTimer();
                        stopped = false;
                        MySQL.setMatchColumn(id,"timestamp",getCurrentSeconds() -
                                (MySQL.getMatchColumn(id,"break") - MySQL.getMatchColumn(id,"timestamp")) +"");
                        MySQL.setMatchColumn(id,"break",(0+""));
                    }else
                    {
                        pauseTimer();
                        stopped=true;
                        MySQL.setMatchColumn(id,"break",(getCurrentSeconds()+""));
                    }
                }
            }
        });

        new LogoDownload((ImageView) findViewById(R.id.team1logo))
                .execute(MySQL.getTeamLogo(m.getHome()));

        new LogoDownload((ImageView) findViewById(R.id.team2logo))
                .execute(MySQL.getTeamLogo(m.getAway()));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,game_management).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gamecard:
                        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView,gamecard).commit();
                        return true;

                    case R.id.game_management:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,game_management).commit();
                        return true;
                }
                return false;
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomatchesadmin();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MySQL.getMatchColumn(id,"isStarted")==0){
                    MySQL.setMatchColumn(id,"isStarted",1+"");
                    MySQL.setMatchColumn(id,"timestamp",(getCurrentSeconds())+"");
                    MySQLConnection mySQLConnection = new MySQLConnection();
                    mySQLConnection.Insert("INSERT INTO flow (match_id,action) VALUES ("+id+",'Ξεκίνησε')");
                    MySQL.ThreadStart(mySQLConnection);
                    start.setVisibility(View.GONE);
                    startTimer();
                }else if (MySQL.getMatchColumn(id,"period")!=4)
                {
                    MySQL.setMatchColumn(id,"timestamp",(getCurrentSeconds())+"");
                    MySQL.setMatchColumn(id,"period",MySQL.getMatchColumn(id,"period")+1+"");
                    periodboard.setText(MySQL.getMatchColumn(id,"period")+"η Περίοδος");
                    MySQLConnection mySQLConnection = new MySQLConnection();
                    mySQLConnection.Insert("INSERT INTO flow (match_id,action) VALUES ("+id+",'"+periodboard.getText().toString()+"')");
                    MySQL.ThreadStart(mySQLConnection);
                    start.setVisibility(View.GONE);
                    secs = MySQL.START_TIME;
                    startTimer();
                }
            }
        });

        if (MySQL.getMatchColumn(id,"isStarted")==1)
        {
            if (MySQL.getMatchColumn(id,"break")==0)
            {
                int timestamp = MySQL.getMatchColumn(id,"timestamp");
                timestamp = getCurrentSeconds() - timestamp;
                secs = MySQL.START_TIME - timestamp*1000;
                startTimer();
            }else
            {
                int timestamp = MySQL.getMatchColumn(id,"timestamp");
                int br = MySQL.getMatchColumn(id,"break");
                timestamp = br - timestamp;
                secs = MySQL.START_TIME - timestamp*1000;
                updateText();
            }
        }else{
            start.setVisibility(View.VISIBLE);
            timeboard.setText("00:00");
        }
    }

    private void gotomatchesadmin(){
        Intent intent = new Intent(this, admin_matches.class);
        startActivity(intent);
    }

    private void startTimer(){
        timer = new CountDownTimer(secs, 1000) {
            @Override
            public void onTick(long l) {
                secs = l;
                updateText();
            }

            @Override
            public void onFinish() {
                if (MySQL.getMatchColumn(id,"period")==4)
                {
                    timeboard.setText("00:00");
                    periodboard.setText("Έληξε");
                    MySQLConnection mySQLConnection = new MySQLConnection();
                    mySQLConnection.Insert("INSERT INTO flow (match_id,action) VALUES ("+id+",'Έληξε')");
                    MySQL.ThreadStart(mySQLConnection);
                    MySQL.setMatchColumn(id,"isFinished","1");
                    if (Integer.valueOf(MySQL.getMatchColumn(id,"homePoints"))
                            >Integer.valueOf(MySQL.getMatchColumn(id,"awayPoints"))){
                        Team t1 = MySQL.getTeam(m.getHome());
                        t1.addMatchResult(true);

                        Team t2 = MySQL.getTeam(m.getAway());
                        t2.addMatchResult(false);
                    }else{
                        Team t1 = MySQL.getTeam(m.getHome());
                        t1.addMatchResult(false);

                        Team t2 = MySQL.getTeam(m.getAway());
                        t2.addMatchResult(true);
                    }
                }else
                {
                    start.setVisibility(View.VISIBLE);
                    start.setText("ΕΝΑΡΞΗ ΠΕΡΙΟΔΟΥ");
                    timeboard.setText("00:00");
                    secs = MySQL.START_TIME;
                }
            }
        }.start();
    }

    private void pauseTimer()
    {
        timer.cancel();
    }

    private void updateText(){
        int minutes = (int) (secs/1000)/60;
        int seconds = (int) (secs/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",(9-minutes),(59-seconds));
        timeboard.setText(timeFormatted);

        bundle.putString("timestamp",timeboard.getText().toString());
    }

    private int getCurrentSeconds(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");

        int time = Integer.valueOf(simpleDateFormat.format(date));

        int seconds = (int) (time/10000)*60*60;
        seconds+= (int) (time % 10000 / 100)*60;
        seconds+= (int) (time % 100);
        return seconds;
    }
}