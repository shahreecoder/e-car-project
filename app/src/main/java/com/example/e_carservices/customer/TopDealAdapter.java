package com.example.e_carservices.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_carservices.R;
import com.example.e_carservices.owner.modelservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopDealAdapter extends ArrayAdapter<cardmodel> {
    Context context;
    List<cardmodel> arraylistservice;

    public TopDealAdapter(@NonNull Context context, List<cardmodel> arraylistservice) {
        super(context, R.layout.deal_list_item, arraylistservice);
        this.arraylistservice = arraylistservice;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_list_item, null, true);
        ImageView imageIv = view.findViewById(R.id.bannerIV);
        TextView titleTv = view.findViewById(R.id.titleTV);
        TextView priceTV = view.findViewById(R.id.price);
        Button btnaddtocart=view.findViewById(R.id.addtocartdeal);
        //Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/"+arraylistservice.get(position).getSimage().toString()).into(imgservice);
        cardmodel model = arraylistservice.get(position);
        String title = model.getTitle();
        String price = model.getPrice();
        String image = model.getImage();

        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/" + image).into(imageIv);
        titleTv.setText(title);
        priceTV.setText("Price is: " + price + " /PKR");
        //container.addView(view,position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Working on it", Toast.LENGTH_SHORT).show();

            }
        });
        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cart is not enable yet", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
