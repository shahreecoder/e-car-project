package com.example.e_carservices.customer;

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

import java.util.List;

public class orderdetailsadapter extends ArrayAdapter<orderdetailsmodel> {
    Context context;
    List<orderdetailsmodel> orderdetailsmodels;

    public orderdetailsadapter(@NonNull Context context, List<orderdetailsmodel> orderModelList) {
        super(context, R.layout.cartlistitem, orderModelList);
        this.orderdetailsmodels=orderModelList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistitem, null,true);
        TextView title=view.findViewById(R.id.txt_servicename);
        TextView price=view.findViewById(R.id.txt_serviceprice);
        TextView index=view.findViewById(R.id.txt_id);
        ImageView imgview=view.findViewById(R.id.removeitemcart);
        imgview.setVisibility(View.GONE);
        title.setText(orderdetailsmodels.get(position).getName());

       // title.setText("Order ID: "+orderdetailsmodels.get(position).getName());
        price.setText(orderdetailsmodels.get(position).getPrice()+" /PKR");
        index.setText(String.valueOf(position+1));
        return view;
    }
}
