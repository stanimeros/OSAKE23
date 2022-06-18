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

public class substitution extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int id; //Find the match
    String team; //Which team in the match
    Spinner spinner;
    Spinner spinner2;

    ArrayList<String> players = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    Match m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitution);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        team = bundle.getString("team");
        String timestamp = bundle.getString("timestamp");

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

        ShapeableImageView incertbtn = findViewById(R.id.incert);
        incertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name1 = spinner.getSelectedItem().toString();
                    String name2 = spinner2.getSelectedItem().toString();

                    Player p1 = MySQL.getPlayer(name1,team);
                    Player p2 = MySQL.getPlayer(name2,team);

                    MySQL.UpdatePlayer(p2,p1.getId());
                    MySQL.UpdatePlayer(p1,p2.getId());

                    displayToast();
                    gotoadmin();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        spinner = findViewById(R.id.playerinSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.my_spinner_style,players);
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = findViewById(R.id.playeroutSpinner);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        temp.clear();
        temp.addAll(players);
        int i=0;
        while (temp.get(i).toString()!=text) {i++;}
        temp.remove(i);

        spinner2 = findViewById(R.id.playeroutSpinner);
        ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.my_spinner_style,temp);
        adapter2.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner2.setAdapter(adapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void displayToast(){
        Toast.makeText(substitution.this,"Changes successfully applied", Toast.LENGTH_SHORT).show();
    }

    private void gotoadmin() {
        Intent intent = new Intent(this, admin.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}