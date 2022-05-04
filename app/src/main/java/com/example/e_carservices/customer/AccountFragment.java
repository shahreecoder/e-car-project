package com.example.e_carservices.customer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_carservices.MasterHome;
import com.example.e_carservices.R;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    View view;

    Button vpo, vco,  logout;
    TextView tvusername;

    // CustomerSession customerSession=new CustomerSession(getContext());
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_account, container, false);

        vpo = view.findViewById(R.id.viewpending);
        vco = view.findViewById(R.id.viewdone);
       // as = view.findViewById(R.id.accountsetting);
        logout = view.findViewById(R.id.logoutuser);
        tvusername = view.findViewById(R.id.usernametext);

        CustomerSession customerSession = new CustomerSession(getContext());

        if (customerSession.customerid() == null) {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.custome_exit_dialog);

            final Button btnyess = dialog.findViewById(R.id.btnyess);
            final Button btnno = dialog.findViewById(R.id.btnno);
            final TextView titletxt = dialog.findViewById(R.id.txtexit);
            titletxt.setText("Please Login to Continue");
            btnyess.setText("Login");
            btnno.setText("Cancel");


            btnyess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), customerlogin.class);

                    startActivity(intent);
                    getActivity().finish();
                    dialog.dismiss();

                }
            });
            btnno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), customerlogin.class);
                    getActivity().finish();
                    startActivity(intent);
                    dialog.dismiss();

                }
            });
            dialog.show();
        } else {
            tvusername.setText(customerSession.cutomername());
            //view pending order
            vpo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getContext(),order.class);
                    intent.putExtra("order","pending");
                    startActivity(intent);
                }
            });

            //view complete order
            vco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),order.class);
                    intent.putExtra("order","complete");
                    startActivity(intent);
                }
            });



            //logout user
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutuser();
                }
            });
        }

        return view;
    }

    private void logoutuser() {
        CustomerSession customerSession = new CustomerSession(getContext());
        customerSession.Logoutcustomer();
        Toast.makeText(getContext(), "Logut Done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MasterHome.class);
        startActivity(intent);
        getActivity().finish();
    }
}