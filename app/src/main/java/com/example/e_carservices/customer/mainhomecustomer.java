package com.example.e_carservices.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.e_carservices.R;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class mainhomecustomer extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;
    List<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhomecustomer);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        SliderView sliderView = findViewById(R.id.imageSlider);

        images.add(R.drawable.oneslid);
        images.add(R.drawable.twoslid);

        sliderAdapter sliderAdapter = new sliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);


        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_attach_money_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.show(1, true);
    }
}