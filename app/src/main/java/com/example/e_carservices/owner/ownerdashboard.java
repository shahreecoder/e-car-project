package com.example.e_carservices.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_carservices.R;

public class ownerdashboard extends AppCompatActivity {
    CardView crdaddservices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerdashboard);
        crdaddservices=findViewById(R.id.Cardaddservices);
        crdaddservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ownerdashboard.this, "Card is Clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }
}