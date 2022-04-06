package com.example.e_carservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.e_carservices.customer.CustomerSession;
import com.example.e_carservices.customer.mainhomecustomer;

public class MainActivity extends AppCompatActivity {
    private static int FLASH_SCREEN=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomerSession customerSession=new CustomerSession(getBaseContext());
                if(customerSession.customerid()==null){
                    Intent intent=new Intent(MainActivity.this,MasterHome.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(MainActivity.this, mainhomecustomer.class);
                    startActivity(intent);
                    finish();
                }

            }
        },FLASH_SCREEN);
    }
}