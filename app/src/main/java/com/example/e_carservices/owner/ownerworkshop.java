package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class ownerworkshop extends AppCompatActivity {
    Button addservice;

    EditText shopname, shopspec, shopno, streetno, block, town;
    String ownerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerworkshop);
        ownerSession ownerSession = new ownerSession(this);
        ownerid= ownerSession.owenid();
        addservice = findViewById(R.id.btnaddshop);
        shopname = findViewById(R.id.ShopName);
        shopspec = findViewById(R.id.ShopSpec);
        shopno = findViewById(R.id.ShopNo);
        streetno = findViewById(R.id.StreetNo);
        block = findViewById(R.id.Block);
        town = findViewById(R.id.Town);
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopname.getText().toString().isEmpty()) {
                    shopname.setError("Please input name");
                    shopname.setText("");
                }
                else if (shopspec.getText().toString().isEmpty()) {
                    shopspec.setError("Please input Spec");
                    shopspec.setText("");
                } else if (shopno.getText().toString().isEmpty()) {
                    shopno.setError("Please input Number");
                    shopno.setText("");
                } else if (streetno.getText().toString().isEmpty()) {
                    streetno.setError("Please input Street Number");
                    streetno.setText("");
                } else if (block.getText().toString().isEmpty()) {
                    block.setError("Please input Block");
                    block.setText("");
                } else if (town.getText().toString().isEmpty()) {
                    town.setError("Please input town");
                    town.setText("");
                }
                else {
                    Toast.makeText(ownerworkshop.this,"Notthig",Toast.LENGTH_SHORT).show();
                    updateshop(shopname.getText().toString(), shopspec.getText().toString(), shopno.getText().toString(), streetno.getText().toString(), block.getText().toString(), town.getText().toString());
                }


            }
        });
    }

    public void updateshop(String ShopName, String ShopSpec, String ShopNo, String Streetno, String Block, String Town) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Address is adding");
        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "addownerworkshop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("update")) {
                    Toast.makeText(getBaseContext(), "Shop has been updated", Toast.LENGTH_LONG).show();
                    Intent address = new Intent(ownerworkshop.this, ownerdashboard.class);
                    startActivity(address);
                    finish();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("shopno", ShopNo);
                params.put("streetno", Streetno);
                params.put("block", Block);
                params.put("town", Town);
                params.put("shopname", ShopName);
                params.put("shopspec", ShopSpec);
                params.put("ownerid",ownerid);
                params.put("city", "Lahore");
                params.put("type", "insert");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ownerworkshop.this);
        requestQueue.add(request);

    }
}