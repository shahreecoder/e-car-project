package com.example.e_carservices.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.e_carservices.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class sliderAdapter extends SliderViewAdapter<sliderAdapter.MyViewHolder> {

    List<Integer> list;
    sliderAdapter(List<Integer> list){
        this.list=list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliderimageitem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            viewHolder.imgview.setImageResource(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    class MyViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imgview;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgview=itemView.findViewById(R.id.imageViewSlider);
        }
    }
}
