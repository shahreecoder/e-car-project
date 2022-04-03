package com.example.e_carservices.customer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class gridAdapter extends BaseAdapter {
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<cardmodel> cardmodelArrayList;
//    private ArrayList<String> names;

    public gridAdapter(Context context, ArrayList<cardmodel> images) {
        this.context = context;
        this.cardmodelArrayList = images;
        //this.names = names;
    }

    @Override
    public int getCount() {
        return cardmodelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardmodelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledatagrid, null, true);
        ImageView imageView=view.findViewById(R.id.bannerIVG);
        TextView title=view.findViewById(R.id.titleTVG);
        TextView price=view.findViewById(R.id.priceG);
        ImageButton addtocartbtn=view.findViewById(R.id.addtocartimg);
        addtocart addtocart=new addtocart(context);
        CustomerSession customerSession=new CustomerSession(context);
        if(!addtocart.checkalready(customerSession.customerid(),cardmodelArrayList.get(position).getId())){

            addtocartbtn.setImageResource(R.drawable.ic_okcircle);
        }
        title.setText(cardmodelArrayList.get(position).getTitle());
        price.setText(cardmodelArrayList.get(position).getPrice()+" /RS");
        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/" +cardmodelArrayList.get(position).getImage() ).into(imageView);

        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                addtocart addtocart=new addtocart(context);

                //Toast.makeText(context, addtocart.checkalready("1",cardmodelArrayList.get(position).getId()).toString(), Toast.LENGTH_SHORT).show();
                if(addtocart.checkalready(customerSession.customerid(),cardmodelArrayList.get(position).getId())){
                    addtocart.insertAddtocart(customerSession.customerid(),cardmodelArrayList.get(position).getId());
                    addtocartbtn.setImageResource(R.drawable.ic_okcircle);
                }else{
                    Toast.makeText(context, "Alread Add to the Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
