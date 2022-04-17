package com.example.e_carservices.customer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.e_carservices.database.clsConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class order extends AppCompatActivity {
    public ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();
    public PendingOrderAdapter pendingOrderAdapter;
    private ListView orderlist;
    private TextView head;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderlist = findViewById(R.id.listorder);
        head = findViewById(R.id.topordertv);
        Intent intent=getIntent();
        status=intent.getStringExtra("order");
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        if(status.equals("pending")){
            head.setText("Pending orders");

        }else{
            head.setText("Complete orders");
        }
        PendingOrderLoad();
        orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getBaseContext(),order_details.class);
                intent.putExtra("orderid",orderModelArrayList.get(position).getOrderid());
                startActivity(intent);
                Toast.makeText(order.this, orderModelArrayList.get(position).getOrderid(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PendingOrderLoad() {
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "fatchpendingorder.php", new Response.Listener<String>() {
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
                Toast.makeText(order.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                CustomerSession customerSession = new CustomerSession(order.this);


                params.put("cusid", customerSession.customerid());
                if(status.equals("pending")){
                    params.put("status","pending" );
                }else{
                    params.put("status","done" );
                }


                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(request);

    }

    private void addpendingtolist(String o_id, String address, String pyment_type, String total_amount, String order_status, String add_on, String phn) {
        orderModelArrayList.add(new OrderModel(o_id, address, pyment_type, total_amount, order_status, add_on, phn));
        pendingOrderAdapter = new PendingOrderAdapter(getBaseContext(), orderModelArrayList);

        orderlist.setAdapter(pendingOrderAdapter);
    }
}