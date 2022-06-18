package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.imageview.ShapeableImageView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class shot_score extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_shot_score);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        team = bundle.getString("team");
        timestamp = bundle.getString("timestamp");

        m = MySQL.getMatch(id);
        players = MySQL.getPlayerNames(team);

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

        spinner = findViewById(R.id.scorerSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.my_spinner_style,players);
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = findViewById(R.id.pointsSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.points, R.layout.my_spinner_style);
        adapter2.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = findViewById(R.id.assistslabel);
                ImageView arrow = findViewById(R.id.arrow3);

                if (spinner2.getSelectedItem().toString().equals("Ελεύθερη βολή"))
                {
                    spinner3.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    arrow.setVisibility(View.GONE);

                }else
                {
                    spinner3.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    arrow.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinner3 = findViewById(R.id.assisterSpinner);

        ShapeableImageView incertbtn = findViewById(R.id.incert);
        incertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name = spinner.getSelectedItem().toString();
                    String action = spinner2.getSelectedItem().toString();
                    String name2 = spinner3.getSelectedItem().toString();

                    if (action.contains("Ελεύθερη βολή"))
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("free_shots",name,team);
                        m.insertPoint(1, Objects.equals(team, m.getHome()));
                    }else if(action.contains("Δίποντο"))
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("two_points",name,team);
                        m.addAction("assists",name2,team);
                        m.insertPoint(2,Objects.equals(team, m.getHome()));
                    }else
                    {
                        m.setTimestamp(timestamp);
                        m.addAction("three_points",name,team);
                        m.addAction("assists",name2,team);
                        m.insertPoint(3,Objects.equals(team, m.getHome()));
                    }
                    displayToast();
                    gotoadmin();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        temp.clear();
        temp.addAll(players);
        int i=0;
        while (temp.get(i).toString()!=text) {i++;}
        temp.remove(i);

        spinner3 = findViewById(R.id.assisterSpinner);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter(this,R.layout.my_spinner_style, temp);
        adapter3.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner3.setAdapter(adapter3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void displayToast(){
        Toast.makeText(shot_score.this,"Successfully applied", Toast.LENGTH_SHORT).show();
    }

    private void gotoadmin() {
        Intent intent = new Intent(this, admin.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}