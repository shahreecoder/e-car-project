package com.example.e_carservices.customer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class order_details extends AppCompatActivity {

    public ArrayList<orderdetailsmodel> orderModelArrayList = new ArrayList<>();
    public orderdetailsadapter orderdetailsadapter;
    private ListView orderdetailslist;
    TextView orderid, total,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        orderdetailslist=findViewById(R.id.orderdetailslist);
        orderid=findViewById(R.id.orderid);
        total=findViewById(R.id.total);
        status=findViewById(R.id.status);

        orderdetailsload();
    }

    private void orderdetailsload() {
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "fatchorderdetails.php", new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    total.setText(jsonObject.getString("total")+" /PKR");
                    if(jsonObject.getString("status").equals("done")){
                        status.setText(jsonObject.getString("status"));
                        status.setTextColor(R.color.teal_700);
                    }
                    status.setText(jsonObject.getString("status"));


                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String sname = jsonArray.getJSONObject(i).getString("sname");
                        String sprice = jsonArray.getJSONObject(i).getString("sprice");
                        addorderitem(sname, sprice);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(order_details.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                Intent intent=getIntent();
                orderid.setText(intent.getStringExtra("orderid"));
                params.put("orderid",intent.getStringExtra("orderid"));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(request);

    }
    public void addorderitem(String sname, String sprice){

        orderModelArrayList.add(new orderdetailsmodel(sprice,sname));
        orderdetailsadapter = new orderdetailsadapter(getBaseContext(), orderModelArrayList);
        orderdetailslist.setAdapter(orderdetailsadapter);


    }
}