package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class create_a_team extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_team);

        ImageView backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenuAdmin();
            }
        });

        TextView nameView = findViewById(R.id.editTextTextPersonName);
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameView.setText("");
            }
        });

        TextView cityView = findViewById(R.id.editTextTextPersonName2);
        cityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityView.setText("");
            }
        });

        TextView pathView = findViewById(R.id.pathVIew);
        pathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathView.setText("");
            }
        });

        Button incert = findViewById(R.id.incert);
        incert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenuAdmin();

                String name = nameView.getText().toString();

                String city = cityView.getText().toString();

                String path = pathView.getText().toString();

                Team t = new Team(name,city,path);
                t.createSQL();
            }
        });
    }

    public void displayToast(){
        Toast.makeText(create_a_team.this,"The team has been successfully created", Toast.LENGTH_SHORT).show();
    }

    private void gotoMenuAdmin(){
        Intent intent = new Intent(this, menu_admin.class);
        startActivity(intent);
        displayToast();
    }
}