package com.example.letsgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ingame extends AppCompatActivity {

    long secs = MySQL.START_TIME;
    CountDownTimer timer;
    TextView periodboard;
    TextView timeboard;
    TextView score;
    boolean running = false;
    boolean finished = false;
    Match m;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        m = MySQL.getMatch(id);
        TextView t = findViewById(R.id.team1name);
        t.setText(m.getHome());

        TextView t1 = findViewById(R.id.team2name);
        t1.setText(m.getAway());

        score = findViewById(R.id.scoreboard);
        String sc = MySQL.getMatchColumn(m.getId(), "homePoints") + "  -  " + MySQL.getMatchColumn(m.getId(), "awayPoints");
        score.setText(sc);

        TextView periodboard = findViewById(R.id.periodboard);
        periodboard.setText(MySQL.getMatchColumn(m.getId(), "period") + "η Περίοδος");

        timeboard = findViewById(R.id.timeboard);
        startTimerWorking();

            new LogoDownload((ImageView) findViewById(R.id.team1logo))
                    .execute(MySQL.getTeamLogo(m.getHome()));

            new LogoDownload((ImageView) findViewById(R.id.team2logo))
                    .execute(MySQL.getTeamLogo(m.getAway()));

            ImageView back = findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotomatches();
                }
            });

            BottomNavigationView bottomNavigationView2;
            live_flow live_flow = new live_flow();
            live_team_stats live_team_stats = new live_team_stats();
            live_personal_stats live_personal_stats = new live_personal_stats();

            live_flow.setArguments(bundle);
            live_team_stats.setArguments(bundle);
            live_personal_stats.setArguments(bundle);

            bottomNavigationView2 = findViewById(R.id.bottomNavigationView2);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, live_flow).commit();

            bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.live_flow:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, live_flow).commit();
                            return true;

                        case R.id.live_team_stats:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, live_team_stats).commit();
                            return true;

                        case R.id.live_personal_stats:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, live_personal_stats).commit();
                            return true;
                    }
                    return false;
                }
            });
    }

    private void gotomatches(){
        Intent intent = new Intent(this, matches.class);
        startActivity(intent);
    }

    private void startTimerWorking()
    {
        int timestamp = MySQL.getMatchColumn(m.getId(), "timestamp");
        if (timestamp < 0) {
            secs = 599000;
            updateText();
        } else {
            timestamp = getCurrentSeconds() - timestamp;
            timestamp = MySQL.START_TIME - timestamp * 1000;
            secs = timestamp;

            if (MySQL.getMatchColumn(m.getId(), "break") == 0) {
                running = true;
                startTimer();
            } else {
                running = false;
                int br = MySQL.getMatchColumn(m.getId(), "break");
                timestamp = MySQL.getMatchColumn(m.getId(), "timestamp");
                timestamp = br - timestamp;
                secs = MySQL.START_TIME - timestamp * 1000;
                updateText();
            }
        }
    }

    private void startTimer(){
        timer = new CountDownTimer(secs, 1000) {
            @Override
            public void onTick(long l) {
                running = true;
                secs = l;
                updateText();
            }

            @Override
            public void onFinish() {
                finished = true;
                if (MySQL.getMatchColumn(m.getId(),"period")==4)
                {
                    try {
                        timeboard.setText("00:00");
                        periodboard.setText("Έληξε");
                    }catch (Exception e){
                        System.out.println(e);
                    }

                }else
                {
                    timeboard.setText("10:00");
                    secs = MySQL.START_TIME;
                }
            }
        }.start();
    }

    private void updateText(){
        int minutes = (int) (secs/1000)/60;
        int seconds = (int) (secs/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",(9-minutes),(59-seconds));
        timeboard.setText(timeFormatted);
    }

    private void updateTimer(){
        if (MySQL.getMatchColumn(m.getId(), "break") != 0) {
            try {
                timer.cancel();
                running = false;
            }catch (Exception e){}
        }else
        {
            if (!running)
            {
                startTimerWorking();
            }else if (finished)
            {
                startTimerWorking();
            }
        }
    }

    private void updatePeriod(){
        periodboard = findViewById(R.id.periodboard);
        if (MySQL.getMatchColumn(m.getId(),"isFinished")==1)
        {
            periodboard.setText("Έληξε");
        }else
        {
            periodboard.setText(MySQL.getMatchColumn(m.getId(),"period")+"η Περίοδος");
        }

        String sc = MySQL.getMatchColumn(m.getId(), "homePoints") + "  -  " + MySQL.getMatchColumn(m.getId(), "awayPoints");
        score.setText(sc);
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

    //REFRESH ACTIVITY
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;

    @Override
    protected void onResume() {
        try{
            handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, delay);
                    updateTimer();
                    updatePeriod();
                }
            }, delay);
            super.onResume();
        }catch (Exception e){}}
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
}