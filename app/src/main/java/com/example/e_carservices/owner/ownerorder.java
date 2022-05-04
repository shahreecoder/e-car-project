package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.e_carservices.customer.CustomerSession;
import com.example.e_carservices.customer.OrderModel;
import com.example.e_carservices.customer.PendingOrderAdapter;
import com.example.e_carservices.customer.order;
import com.example.e_carservices.customer.order_details;
import com.example.e_carservices.database.clsConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ownerorder extends AppCompatActivity {

    public ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();
    public ownerorderadapter pendingOrderAdapter;
    private ListView orderlist;
    private TextView head;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerorder);
        orderlist = findViewById(R.id.listorder);
        head = findViewById(R.id.topordertv);
        Intent intent = getIntent();
        status = intent.getStringExtra("order");
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        if (status.equals("pending")) {
            head.setText("Latest Booking");

        } else {
            head.setText("Complete orders");
        }
        PendingOrderLoad();
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (status.equals("pending")) {
                    AlertDialog.Builder bilder = new AlertDialog.Builder(view.getContext());
                    //ProgressDialog progressDialog=new ProgressDialog(view.getContext());
                    CharSequence[] dialogitem = {"View Order", "Status = Complete"};
                    bilder.setTitle("Order" + orderModelArrayList.get(position).getOrderid());
                    bilder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0: {
                                    Intent intent = new Intent(getBaseContext(), ownerorderdetails.class);
                                    intent.putExtra("orderid", orderModelArrayList.get(position).getOrderid());
                                    startActivity(intent);
                                    break;
                                }
                                case 1: {
                                    statusupdate(orderModelArrayList.get(position).getOrderid());
                                    break;
                                }

                            }

                        }
                    });
                    bilder.create().show();
                } else {
                    Intent intent = new Intent(getBaseContext(), ownerorderdetails.class);
                    intent.putExtra("orderid", orderModelArrayList.get(position).getOrderid());
                    startActivity(intent);

                }

            }
        });
    }


    private void PendingOrderLoad() {
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "ownerorderpending.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String o_id = jsonArray.getJSONObject(i).getString("o_id");
                        String address = jsonArray.getJSONObject(i).getString("address");
                        String pyment_type = jsonArray.getJSONObject(i).getString("pyment_type");
                        String total_amount = jsonArray.getJSONObject(i).getString("total_amount");
                        String order_status = jsonArray.getJSONObject(i).getString("order_status");
                        String add_on = jsonArray.getJSONObject(i).getString("add_on");
                        String phn = jsonArray.getJSONObject(i).getString("phn");
                        addpendingtolist(o_id, address, pyment_type, total_amount, order_status, add_on, phn);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(ownerorder.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (status.equals("pending")) {
                    params.put("status", "pending");
                } else {
                    params.put("status", "done");
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(request);

    }

    private void addpendingtolist(String o_id, String address, String pyment_type, String total_amount, String order_status, String add_on, String phn) {
        orderModelArrayList.add(new OrderModel(o_id, address, pyment_type, total_amount, order_status, add_on, phn));
        pendingOrderAdapter = new ownerorderadapter(getBaseContext(), orderModelArrayList);

        orderlist.setAdapter(pendingOrderAdapter);
    }

    private void statusupdate(String orderid) {
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "updateownerorderstatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PendingOrderLoad();
                Toast.makeText(ownerorder.this, "Done", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(ownerorder.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                    params.put("orderid", orderid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(request);

    }

}
