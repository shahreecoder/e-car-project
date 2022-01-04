package com.example.e_carservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homescreen extends AppCompatActivity {
    private Button btnowner, btncustmr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        btncustmr=(Button) findViewById(R.id.btncstmr);
        btnowner=(Button) findViewById(R.id.btnowner);
        btnowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent owner=new Intent(homescreen.this,loginowner.class);
                startActivity(owner);
            }
        });
        btncustmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent owner=new Intent(homescreen.this,singupowner.class);
                startActivity(owner);

            }
        });
    }

}