package com.example.e_carservices.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.e_carservices.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MycardAdapter extends RecyclerView.Adapter<MycardAdapter.ViewHolder> {

    ArrayList<cardmodel> cardmodelArrayList;
    Context context;
    ViewGroup viewGroup;
    public MycardAdapter(ArrayList<cardmodel> cardmodelArrayList, Context context) {
        this.cardmodelArrayList = cardmodelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext())
               .inflate(R.layout.card_item,parent,false);
                viewGroup=parent;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/" + cardmodelArrayList.get(position).getImage()).into(holder.imageView);
            //holder.imageView.setImageResource(cardmodelArrayList.get(position).getImage());
            holder.title.setText(cardmodelArrayList.get(position).getTitle());
            holder.price.setText("Price: "+cardmodelArrayList.get(position).getPrice()+" /PKR");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loatbottomsheet(cardmodelArrayList.get(position).getTitle(),cardmodelArrayList.get(position).getPrice(),cardmodelArrayList.get(position).getImage(),cardmodelArrayList.get(position).getDisp(),cardmodelArrayList.get(position).getId());



                }
            });
    }

    private void loatbottomsheet(String name, String Price, String image, String Disp, String id) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetStyle);

        View view=LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog,viewGroup,false);
        ImageView img;
        TextView tvname;
        TextView tvdisp;
        TextView tvprice;
        img=view.findViewById(R.id.productimaged);
        tvname=view.findViewById(R.id.productnamed);
        tvdisp=view.findViewById(R.id.productdispd);
        tvprice=view.findViewById(R.id.productpriced);

        tvname.setText(name);
        tvprice.setText("Price is: "+Price+" /PKR");

        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/" + image).into(img);
        tvdisp.setText(Disp);


        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    @Override
    public int getItemCount() {
        return cardmodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.bannerIV);
            title=itemView.findViewById(R.id.titleTV);
            price=itemView.findViewById(R.id.price);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, title.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}
