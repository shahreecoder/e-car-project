package com.example.e_carservices.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_carservices.R;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    View view;
    List<Integer> images = new ArrayList<>();
    private ArrayList<cardmodel> cardmodelArrayList;
    private  MycardAdapter mycardAdapter;
    private ViewPager viewPager;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);

        images.add(R.drawable.oneslid);
        images.add(R.drawable.twoslid);

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        viewPager=view.findViewById(R.id.homeviewpager);
        sliderAdapter sliderAdapter=new sliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

//        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        loadcard();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        return view;
    }

    private void loadcard() {
        cardmodelArrayList =new ArrayList<>();
        cardmodelArrayList.add(new cardmodel(
                "title 01",
                "200",
                R.drawable.oneslid
        ));
        cardmodelArrayList.add(new cardmodel(
                "title 02",
                "400",
                R.drawable.twoslid
        ));
        cardmodelArrayList.add(new cardmodel(
                "title 03",
                "500",
                R.drawable.threeslid
        ));
        mycardAdapter =new MycardAdapter(getActivity().getBaseContext(), cardmodelArrayList);
        viewPager.setAdapter(mycardAdapter);
        viewPager.setPadding(100,0,100,0);
    }
}