package com.example.e_carservices.owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_carservices.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class serviceadaptor extends ArrayAdapter<modelservice> {

    Context context;
    List<modelservice> arraylistservice;


    public serviceadaptor(@NonNull Context context, List<modelservice> arraylistservice) {
        super(context, R.layout.custome_service_listitem, arraylistservice);
        this.arraylistservice=arraylistservice;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_service_listitem, null,true);
        TextView tvid=view.findViewById(R.id.txt_id);
        TextView tvname=view.findViewById(R.id.txt_servicename);
        ImageView imgservice=view.findViewById(R.id.imageviewservice);
        //Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/"+arraylistservice.get(position).getSimage().toString()).into(imgservice);
        tvid.setText(arraylistservice.get(position).getIndex());
        tvname.setText(arraylistservice.get(position).getSname());
        return view;
    }
}
