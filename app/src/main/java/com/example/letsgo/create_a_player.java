package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class create_a_player extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_player);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenuAdmin();
            }
        });

        TextView nameView = findViewById(R.id.player_name);
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameView.setText("");
            }
        });

        TextView pathView = findViewById(R.id.pathView1);
        pathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathView.setText("");
            }
        });

        Spinner spinner = findViewById(R.id.positionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.positions, R.layout.my_spinner_style);
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
        spinner.setAdapter(adapter);

        try {
            ArrayList<String> teams = MySQL.getTeams();

            Spinner spinner2 = findViewById(R.id.teamSpinner);
            ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.my_spinner_style,teams);
            adapter2.setDropDownViewResource(R.layout.my_spinner_dropdown_style);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(this);

            Button incert = findViewById(R.id.incert);
            if (teams.size()==0){
                incert.setClickable(false);
            }

            incert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nameView.getText().toString();
                    String path = pathView.getText().toString();

                    Player p = new Player(MySQL.getTeamId(spinner2.getSelectedItem().toString()),name,spinner.getSelectedItem().toString(),path);
                    p.createSQL();
                    displayToast();
                    gotoMenuAdmin();
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void gotoMenuAdmin(){
        Intent intent = new Intent(this, menu_admin.class);
        startActivity(intent);
    }

    public void displayToast(){
        Toast.makeText(create_a_player.this,"The player has been successfully created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}