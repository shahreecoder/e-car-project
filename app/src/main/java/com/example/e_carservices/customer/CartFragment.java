package com.example.e_carservices.customer;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;


public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }


    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_cart, container, false);

        addtocart addtocart=new addtocart(getContext());
        Cursor cursor=addtocart.getcartdata("1");

        if(cursor.moveToFirst()){
            do{
//
            }while (cursor.moveToNext());
        }
        cursor.close();





        return view;
    }
}