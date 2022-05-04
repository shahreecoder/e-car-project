package com.example.e_carservices.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class mainhomecustomer extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhomecustomer);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_attach_money_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.show(1, true);
        replace(new HomeFragment());
        addtocart cart=new addtocart(getBaseContext());
        CustomerSession customerSession=new CustomerSession(this);
        //Toast.makeText(this, cart.getcountitem(customerSession.customerid()), Toast.LENGTH_SHORT).show();
//        bottomNavigation.setCount(4,cart.getcountitem(customerSession.customerid()));
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new DealsFragment());
                        break;

                    case 3:
                        replace(new AccountFragment());
                        break;

                    case 4:
                        replace(new CartFragment());
                        break;

                }
                return null;
            }
        });



    }



    public void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }


}
