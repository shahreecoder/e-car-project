package com.example.e_carservices.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;
import com.example.e_carservices.owner.loginowner;
import com.example.e_carservices.owner.ownerdashboard;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        viewGroup = parent;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/" + cardmodelArrayList.get(position).getImage()).into(holder.imageView);
        //holder.imageView.setImageResource(cardmodelArrayList.get(position).getImage());
        holder.title.setText(cardmodelArrayList.get(position).getTitle());
        holder.price.setText("Price: " + cardmodelArrayList.get(position).getPrice() + " /PKR");
//            holder.btncart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addtocart addtocart=new addtocart(context);
//                    addtocart.insertAddtocart("1",cardmodelArrayList.get(position).getId());
//                }
//            });
        addtocart addtocart = new addtocart(context);
        CustomerSession customerSession = new CustomerSession(context);
        for (int i = 0; i < cardmodelArrayList.size(); i++) {
            if (!addtocart.checkalready(customerSession.customerid(), cardmodelArrayList.get(position).getId())) {
                holder.btncart.setText("Already in Cart");
            } else {
                holder.btncart.setText("Add to Cart");
            }
        }
        holder.btncart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                CustomerSession customerSession = new CustomerSession(context);


                if (customerSession.customerid() != null) {
                    addtocart addtocart = new addtocart(context);
                    //Toast.makeText(context, addtocart.checkalready("1",cardmodelArrayList.get(position).getId()).toString(), Toast.LENGTH_SHORT).show();
                    if (addtocart.checkalready(customerSession.customerid(), cardmodelArrayList.get(position).getId())) {
                        addtocart.insertAddtocart(customerSession.customerid(), cardmodelArrayList.get(position).getId());
                        holder.btncart.setText("Added ");
                    } else {
                        Toast.makeText(context, "Alread Add to the Cart", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custome_exit_dialog);

                    final Button btnyess = dialog.findViewById(R.id.btnyess);
                    final Button btnno = dialog.findViewById(R.id.btnno);
                    final TextView titletxt = dialog.findViewById(R.id.txtexit);
                    titletxt.setText("Please Login to Continue");
                    btnyess.setText("Login");
                    btnno.setText("Cancel");
                    btnyess.setBackgroundColor(R.color.master);

                    btnyess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           Intent intent=new Intent(context,customerlogin.class);

                           context.startActivity(intent);



                            dialog.dismiss();

                        }
                    });
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
//                if(checkexit()){
//                    ownerSession.Logoutowner();
//                    Intent login=new Intent(ownerdashboard.this, loginowner.class);
//                    startActivity(login);
//                    finish();
//                }
                }


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loatbottomsheet(cardmodelArrayList.get(position).getTitle(), cardmodelArrayList.get(position).getPrice(), cardmodelArrayList.get(position).getImage(), cardmodelArrayList.get(position).getDisp(), cardmodelArrayList.get(position).getId());


            }
        });

    }

    private void loatbottomsheet(String name, String Price, String image, String Disp, String id) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);

        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, viewGroup, false);
        ImageView img;
        TextView tvname;
        TextView tvdisp;
        TextView tvprice;
        img = view.findViewById(R.id.productimaged);
        tvname = view.findViewById(R.id.productnamed);
        tvdisp = view.findViewById(R.id.productdispd);
        tvprice = view.findViewById(R.id.productpriced);

        tvname.setText(name);
        tvprice.setText("Price is: " + Price + " /PKR");

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
        Button btncart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerIV);
            title = itemView.findViewById(R.id.titleTV);
            price = itemView.findViewById(R.id.price);
            btncart = itemView.findViewById(R.id.addtocarthomecard);


            //  Toast.makeText(context, addtocart.checkalready("1",cardmodelArrayList.get(position).getId()).toString(), Toast.LENGTH_SHORT).show();


        }


    }

}
