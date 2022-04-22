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
import com.example.e_carservices.customer.OrderModel;

import java.util.List;

public class ownerorderadapter extends ArrayAdapter<OrderModel> {
    Context context;
    List<OrderModel> orderModelList;

    public ownerorderadapter(@NonNull Context context, List<OrderModel> orderModelList) {
        super(context, R.layout.cartlistitem, orderModelList);
        this.orderModelList=orderModelList;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ownerorderlistitem, null,true);
        TextView title=view.findViewById(R.id.txt_servicename);
        TextView price=view.findViewById(R.id.txt_serviceprice);
        TextView index=view.findViewById(R.id.txt_id);
        ImageView removeitem=view.findViewById(R.id.removeitemcart);
        title.setText("Order ID: "+orderModelList.get(position).getOrderid());
        price.setText(orderModelList.get(position).getTotal_ammount()+" /PKR");
        index.setText(String.valueOf(position+1));
        return view;
    }
}
