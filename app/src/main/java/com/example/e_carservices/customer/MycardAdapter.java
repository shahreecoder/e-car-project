package com.example.e_carservices.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.e_carservices.R;

import java.util.ArrayList;

public class MycardAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<cardmodel> cardmodelArrayList;

    public MycardAdapter(Context context, ArrayList<cardmodel> cardmodelArrayList) {
        this.context = context;
        this.cardmodelArrayList = cardmodelArrayList;
    }

    @Override
    public int getCount() {
        return cardmodelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,container,false);
        ImageView imageIv=view.findViewById(R.id.bannerIV);
        TextView titleTv=view.findViewById(R.id.titleTV);
        TextView priceTV=view.findViewById(R.id.price);
        cardmodel model=cardmodelArrayList.get(position);
        //getdata
        String title= model.getTitle();
        String price= model.getPrice();
        int image= model.getImage();
        //setdata
        imageIv.setImageResource(image);
        titleTv.setText(title);
        priceTV.setText("Price is"+price+" PKR");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view,position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
