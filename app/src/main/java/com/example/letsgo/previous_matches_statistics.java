package com.example.letsgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class previous_matches_statistics extends AppCompatActivity {

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_matches_statistics);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        Match m = MySQL.getMatch(id);
        TextView t = findViewById(R.id.team1name);
        t.setText(m.getHome());

        TextView t1 = findViewById(R.id.team2name);
        t1.setText(m.getAway());

        TextView score = findViewById(R.id.scoreboard);
        String sc = MySQL.getMatchColumn(m.getId(),"homePoints")+"  -  " + MySQL.getMatchColumn(m.getId(),"awayPoints");
        score.setText(sc);

        TextView periodboard = findViewById(R.id.periodboard);
        periodboard.setText("Έληξε");

        TextView timeboard = findViewById(R.id.timeboard);
        timeboard.setText("10:00");

        new LogoDownload((ImageView) findViewById(R.id.team1logo))
                .execute(MySQL.getTeamLogo(m.getHome()));

        new LogoDownload((ImageView) findViewById(R.id.team2logo))
                .execute(MySQL.getTeamLogo(m.getAway()));

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPrevGames();
            }
        });

        BottomNavigationView bottomNavigationView;
        live_team_stats live_team_stats = new live_team_stats();
        live_personal_stats live_personal_stats = new live_personal_stats();

        live_team_stats.setArguments(bundle);
        live_personal_stats.setArguments(bundle);

        bottomNavigationView = findViewById(R.id.bottomNavigationView4);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView4,live_team_stats).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.live_team_stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView4,live_team_stats).commit();
                        return true;
                    case R.id.live_personal_stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView4,live_personal_stats).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void GoToPrevGames() {
        Intent intent = new Intent(this, previous_games.class);
        startActivity(intent);
    }
}