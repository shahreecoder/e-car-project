package com.example.e_carservices.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.e_carservices.R;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

   View view;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                view=inflater.inflate(R.layout.fragment_account, container, false);




        return view;
    }
}