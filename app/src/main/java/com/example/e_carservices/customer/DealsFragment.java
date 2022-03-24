package com.example.e_carservices.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.e_carservices.R;

import java.util.ArrayList;


public class DealsFragment extends Fragment {
    View view;
    private ArrayList<cardmodel> dealmodelArrayList;
    private  TopDealAdapter dealAdapter;
    private ListView topdeallv;

    public DealsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_deals, container, false);
        topdeallv=view.findViewById(R.id.topdeallist);
        loaddeals();



        return view;

    }

    private void loaddeals() {
        dealmodelArrayList =new ArrayList<>();
        dealmodelArrayList.add(new cardmodel(
                "title 01",
                "200",
                R.drawable.oneslid
        ));
        dealmodelArrayList.add(new cardmodel(
                "title 02",
                "400",
                R.drawable.twoslid
        ));
        dealmodelArrayList.add(new cardmodel(
                "title 03",
                "500",
                R.drawable.threeslid
        ));
        dealAdapter =new TopDealAdapter(getActivity().getBaseContext(), dealmodelArrayList);
        topdeallv.setAdapter(dealAdapter);

    }
}