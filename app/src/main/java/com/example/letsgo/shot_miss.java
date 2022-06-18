package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class shot_miss extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int id;
    String team;
    String timestamp;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner3;

    ArrayList<String> players = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    Match m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_miss);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        team = bundle.getString("team");
        timestamp = bundle.getString("timestamp");

        m = MySQL.getMatch(id);

        TextView t = findViewById(R.id.team1name);
        t.setText(m.getHome());

        TextView t1 = findViewById(R.id.team2name);
        t1.setText(m.getAway());

        TextView score = findViewById(R.id.scoreboard);
        String sc = MySQL.getMatchColumn(m.getId(),"homePoints")+"  -  " + MySQL.getMatchColumn(m.getId(),"awayPoints");
        score.setText(sc);

        TextView periodboard = findViewById(R.id.periodboard);
        periodboard.setText(MySQL.getMatchColumn(m.getId(),"period")+"η Περίοδος");

        if (timestamp.equals("00:00")) {
            if (MySQL.getMatchColumn(m.getId(), "timestamp") != 0) {
                timestamp = "09:59";
            }
        }

        TextView timeboard = findViewById(R.id.timeboard);
        timeboard.setText(timestamp);

        new LogoDownload((ImageView) findViewById(R.id.team1logo))
                .execute(MySQL.getTeamLogo(m.getHome()));

        new LogoDownload((ImageView) findViewById(R.id.team2logo))
                .execute(MySQL.getTeamLogo(m.getAway()));

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoadmin();
            }
        });

        players = MySQL.getPlayerNames(team);

        spinner = findViewById(R.id.shooterSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.my_spinner_style,players);
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = findViewById(R.id.pointsSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.points, R.layout.my_spinner_style);
        adapter2.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner2.setAdapter(adapter2);

        spinner3= findViewById(R.id.rebοunderSpinner);

        ShapeableImageView incertbtn = findViewById(R.id.incert);
        incertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = spinner.getSelectedItem().toString();
                    String name2 = spinner3.getSelectedItem().toString();
                    String action = spinner2.getSelectedItem().toString();
                    if (action.contains("Ελεύθερη βολή"))
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("free_shots_m",name,team);
                        addRebound(name2);
                    }else if(action.contains("Δίποντο"))
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("two_points_m",name,team);
                        addRebound(name2);
                    }else
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("three_points_m",name,team);
                        addRebound(name2);
                    }
                    displayToast();
                    gotoadmin();
                }catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        });
    }

    private void addRebound(String name){
        if (players.contains(name))
        {
            m.setTimestamp(timestamp);
            m.addAction("rebounds",name,team);
        }else
        {
            if (Objects.equals(team, m.getHome())){
                m.setTimestamp(timestamp);
                m.addAction("rebounds",name,m.getAway());
            }else
            {
                m.setTimestamp(timestamp);
                m.addAction("rebounds",name,m.getHome());
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        temp.clear();
        temp.addAll(players);
        int i=0;
        while (temp.get(i).toString()!=text) {i++;}
        temp.remove(i);

        if (Objects.equals(team, m.getHome()))
        {
            temp.addAll(MySQL.getPlayerNames(m.getAway()));
        }else
        {
            temp.addAll(MySQL.getPlayerNames(m.getHome()));
        }

        spinner3 = findViewById(R.id.rebοunderSpinner);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(this,R.layout.my_spinner_style, temp);
        adapter3.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner3.setAdapter(adapter3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void displayToast(){
        Toast.makeText(shot_miss.this,"Successfully applied", Toast.LENGTH_SHORT).show();
    }

    private void gotoadmin() {
        Intent intent = new Intent(this, admin.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}