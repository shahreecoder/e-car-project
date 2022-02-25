package com.example.e_carservices.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_carservices.R;
import com.squareup.picasso.Picasso;

public class servicedetails extends AppCompatActivity {
    TextView tvsname, tvsprice, tvdisp;
    ImageView imgservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetails);
        tvsname=findViewById(R.id.SnameD);
        tvsprice=findViewById(R.id.SpriceD);
        tvdisp=findViewById(R.id.SdispD);
        imgservice=findViewById(R.id.Simage);

        tvsname.setText(getIntent().getStringExtra("Sname"));
        tvsprice.setText(getIntent().getStringExtra("Sprice"));
        tvdisp.setText(getIntent().getStringExtra("Sdisp"));
        String imgurl=getIntent().getStringExtra("Simage");
        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/"+imgurl).into(imgservice);
        //Toast.makeText(servicedetails.this,sname,Toast.LENGTH_LONG).show();
    }
}