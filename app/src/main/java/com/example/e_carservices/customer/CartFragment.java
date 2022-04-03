package com.example.e_carservices.customer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;
import com.example.e_carservices.database.clsConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment {

    View view;

    public ArrayList<cardmodel> dealmodelArrayList=new ArrayList<>();
    private  cartAdapter cartAdapter;
    private ListView topdeallv;

    private TextView total;
    private ImageView emptycart;
    private Button btnplaceorder;
    int p;
    int rm,temp;

    public CartFragment() {
        // Required empty public constructor
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_cart, container, false);

        topdeallv=view.findViewById(R.id.cartlist);
        total=view.findViewById(R.id.totalprice);
        emptycart=view.findViewById(R.id.emptycart);
        btnplaceorder=view.findViewById(R.id.placeorder);

        dealmodelArrayList.clear();
        try {
            CustomerSession customerSession=new CustomerSession(getContext());
            addtocart addtocart=new addtocart(getContext());
            Cursor cursor=addtocart.getcartdata(customerSession.customerid());

            if(cursor.moveToFirst()){
                do{

                    loaddeals(cursor.getString(2));

                }while (cursor.moveToNext());
            }

            cursor.close();


            topdeallv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.custome_exit_dialog);

                    final Button btnyess = dialog.findViewById(R.id.btnyess);
                    final Button btnno = dialog.findViewById(R.id.btnno);
                    final TextView titletxt=dialog.findViewById(R.id.txtexit);
                    titletxt.setText("Are you sure to Remove item?");
                    btnyess.setText("Yes");
                    btnno.setText("No");


                    btnyess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            removeitem(position, dealmodelArrayList.get(position).getPrice(),dealmodelArrayList.get(position).getId());
//                    if(addtocart.checkalready("1",cardmodelArrayList.get(position).getId())){
//                        addtocart.insertAddtocart("1",cardmodelArrayList.get(position).getId());
//                    }else{
//                        Toast.makeText(context, "Alread Add to the Cart", Toast.LENGTH_SHORT).show();
//                    }



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


                }
            });
            emptycart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.custome_exit_dialog);

                    final Button btnyess = dialog.findViewById(R.id.btnyess);
                    final Button btnno = dialog.findViewById(R.id.btnno);
                    final TextView titletxt=dialog.findViewById(R.id.txtexit);
                    titletxt.setText("Are you sure to Empty Cart?");
                    btnyess.setText("Yes");
                    btnno.setText("No");


                    btnyess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                            cartempty();

                        }
                    });
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



//        loaddeals("37");
//        loaddeals("40");
//        loaddeals("41");

      //  loaddeals("26");
//        int sum=0;
btnplaceorder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(),checkout.class);
        intent.putExtra("total",p);
        getContext().startActivity(intent);
    }
});







        return view;
    }



    private void loaddeals(String productid) {

//        dealmodelArrayList.add(new cardmodel("d","3","ff","1","fff"));
//        dealmodelArrayList.add(new cardmodel("d","3","ff","1","fff"));
//        dealmodelArrayList.add(new cardmodel("d","3","ff","1","fff"));
//        dealmodelArrayList.add(new cardmodel("d","3","ff","1","fff"));
//        dealmodelArrayList.add(new cardmodel("d","3","ff","1","fff"));
//        cartAdapter=new cartAdapter(getContext(),dealmodelArrayList);
//        topdeallv.setAdapter(cartAdapter);



//        cardmodelArrayList.add(new cardmodel(
//                "title 01",
//                "200",
//                R.drawable.oneslid
//        ));
//        cardmodelArrayList.add(new cardmodel(
//                "title 02",
//                "400",
//                R.drawable.twoslid
//        ));
//        cardmodelArrayList.add(new cardmodel(
//                "title 03",
//                "500",
//                R.drawable.threeslid
//        ));
//        mycardAdapter =new MycardAdapter(getActivity().getBaseContext(), cardmodelArrayList);
//        viewPager.setAdapter(mycardAdapter);
//        viewPager.setPadding(100,0,100,0);
//        ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
//        progressDialog.setTitle("Please Wait...");
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "fatchsingleproduct.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //dealmodelArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

//                    modelservice =new modelservice("1",jsonArray.getJSONObject(2).getString("Sname"),"111","sdsd","ssss");
//                    modelserviceArrayList.add(modelservice);
//                    serviceadaptor.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String sid=jsonArray.getJSONObject(i).getString("Sid");
                        String stitle = jsonArray.getJSONObject(i).getString("Sname");
                        String sprice = jsonArray.getJSONObject(i).getString("Sprice");
                        String sdisp = jsonArray.getJSONObject(i).getString("Sdisp");
                        String simg = jsonArray.getJSONObject(i).getString("Simage");

                        //modelservice = new modelservice(String.valueOf(i + 1), sname, "j", "j", simg);

//                        dealmodelArrayList.add(new cardmodel(stitle,sprice,simg,sid,sdisp));
                        addcartitem(stitle,sprice,simg,sid,sdisp);


                    }

//                    cartAdapter=new cartAdapter(getContext(),dealmodelArrayList);
//
//                    topdeallv.setAdapter(cartAdapter);





                    // mycardAdapter.notifyDataSetChanged();
                    //mycardAdapter =new

                    //viewPager.setPadding(100,0,100,0);
                    //Toast.makeText(getBaseContext(),"test",Toast.LENGTH_LONG).show();

//                    progressDialog.dismiss();
                    // Toast.makeText(getBaseContext(),checkaddress+checkshop,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("proid",productid);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);


    }

    private void addcartitem(String stitle, String sprice, String simg, String sid, String sdisp) {
        dealmodelArrayList.add(new cardmodel(stitle, sprice, simg, sid, sdisp));
        cartAdapter = new cartAdapter(getContext(), dealmodelArrayList);
        senddata( sprice);
        topdeallv.setAdapter(cartAdapter);

    }

    private void removeitem(int position, String price, String productid) {
        addtocart addtocart=new addtocart(getContext());
        CustomerSession customerSession=new CustomerSession(getContext());
        addtocart.removeitem(customerSession.customerid(),productid);
        dealmodelArrayList.remove(position);
        cartAdapter.notifyDataSetChanged();

        p=p-Integer.parseInt(price);

        total.setText(String.valueOf(p)+" /RS");

    }
    private void cartempty() {
        CustomerSession customerSession=new CustomerSession(getContext());
        addtocart addtocart=new addtocart(getContext());
        if(addtocart.clearCart(customerSession.customerid())){
            dealmodelArrayList.clear();
            cartAdapter.notifyDataSetChanged();
            p=0;
            total.setText(String.valueOf(p)+" /RS");
        }

    }


    private void senddata(String sprice) {
        p=p+Integer.parseInt(sprice);
        total.setText(String.valueOf(p)+" /RS");
    }


}