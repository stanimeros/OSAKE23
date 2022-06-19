package com.example.letsgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnlogin = findViewById(R.id.btnlogin);
        Button btnloginadmin = findViewById(R.id.btnloginadmin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomenu();
            }
        });

        btnloginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotomenuadmin();
            }
        });
    }

    private void gotomenu(){
        Intent intent = new Intent(this, menu_fan.class);
        startActivity(intent);
    }

    private void gotomenuadmin(){
        Intent intent = new Intent(this, menu_admin.class);
        startActivity(intent);
    }
}