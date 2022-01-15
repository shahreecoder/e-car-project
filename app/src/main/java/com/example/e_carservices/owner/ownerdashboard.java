package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class ownerdashboard extends AppCompatActivity {
    CardView crdaddservices;
    TextView ownername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerdashboard);
        crdaddservices = findViewById(R.id.Cardaddservices);
        ownername = findViewById(R.id.userownername);
        ownerSession ownerSession = new ownerSession(this);
        ownername.setText(ownerSession.ownername());
        checkaddress(ownerSession.ownername());
        crdaddservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ownerdashboard.this, "Card is Clicked", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void checkaddress(String name) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Login....");
        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "owneruser.php?type=checkaddress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             progressDialog.dismiss();
                if (response.equals("1")) {

                    Toast.makeText(getBaseContext(), "Address updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Address not updated kindly update addresss", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name);

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ownerdashboard.this);
        requestQueue.add(request);
    }
}