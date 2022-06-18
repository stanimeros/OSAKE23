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
import java.util.ArrayList;
import java.util.Locale;

public class action extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int id;
    String team;
    String action;
    String timestamp;
    Spinner spinner;

    ArrayList<String> players = new ArrayList<>();
    Match m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        team = bundle.getString("team");
        action = bundle.getString("action");
        timestamp = bundle.getString("timestamp");

        TextView text = findViewById(R.id.TextAction);
        text.setText(action+" made by:");

        m = MySQL.getMatch(id);
        players = MySQL.getPlayerNames(team);

        if (timestamp.equals("00:00")) {
            if (MySQL.getMatchColumn(m.getId(), "timestamp") != 0) {
                timestamp = "09:59";
            }
        }

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
                    m.setTimestamp(timestamp);
                    m.addAction(action,spinner.getSelectedItem().toString(),team);
                    displayToast();
                    gotoadmin();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        spinner = findViewById(R.id.actionSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.my_spinner_style,players);
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void displayToast(){
        Toast.makeText(action.this,"Successfully applied", Toast.LENGTH_SHORT).show();
    }

    private void gotoadmin() {
        Intent intent = new Intent(this, admin.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}