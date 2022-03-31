package com.example.e_carservices.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_carservices.R;
import com.example.e_carservices.owner.modelservice;

import java.util.List;

public class cartAdapter extends ArrayAdapter<cardmodel> {

    Context context;
    List<cardmodel> arraylistcard;

    public int total;
    public cartAdapter(@NonNull Context context, List<cardmodel> arraylistcard) {
        super(context, R.layout.cartlistitem, arraylistcard);
        this.arraylistcard=arraylistcard;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistitem, null,true);

        TextView title=view.findViewById(R.id.txt_servicename);
        TextView price=view.findViewById(R.id.txt_serviceprice);
        TextView index=view.findViewById(R.id.txt_id);
        ImageView removeitem=view.findViewById(R.id.removeitemcart);
        title.setText(arraylistcard.get(position).getTitle());
        price.setText(arraylistcard.get(position).getPrice()+" /PKR");
        index.setText(String.valueOf(position+1));

       // total= Integer.parseInt(total+String.valueOf(arraylistcard.get(position).getPrice()));




        return view;
    }


}
