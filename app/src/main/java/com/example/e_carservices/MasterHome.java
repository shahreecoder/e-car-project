package com.example.e_carservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_carservices.customer.mainhomecustomer;

public class MasterHome extends AppCompatActivity {

    Button dolater, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_home);
        dolater=findViewById(R.id.masterdolater);
        login=findViewById(R.id.masterlogin);


        dolater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), mainhomecustomer.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), homescreen.class);
                startActivity(intent);
            }
        });

    }
}