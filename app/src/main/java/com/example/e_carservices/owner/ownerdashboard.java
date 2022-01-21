package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ownerdashboard extends AppCompatActivity {
    CardView crdaddservices, cardaddress, cardshop;
    TextView ownername;
    LinearLayout l1,l2,l3;
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerdashboard);
        crdaddservices = findViewById(R.id.Cardaddservices);
        cardaddress=findViewById(R.id.Cardaddaddress);
        cardshop=findViewById(R.id.cardaddworkshop);
        ownername = findViewById(R.id.userownername);
        btnlogout=findViewById(R.id.logout);
        l1=findViewById(R.id.linearLayout);
        l2=findViewById(R.id.linearlayout1);
        l3=findViewById(R.id.linearLayout2);
        ownerSession ownerSession = new ownerSession(this);

        ownername.setText(ownerSession.ownername());
        checkaddress(ownerSession.ownername());
        crdaddservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ownerdashboard.this, "Card is Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownerSession.Logoutowner();
                Intent login=new Intent(ownerdashboard.this, loginowner.class);
                startActivity(login);
                finish();
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
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "ownercheckshopandaddress.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             progressDialog.dismiss();
             Toast.makeText(getBaseContext(),response,Toast.LENGTH_LONG).show();
             try {
                 JSONObject jsonObject=new JSONObject(response);
                 String checkaddress=jsonObject.getString("data");
                 String checkshop=jsonObject.getString("shop");
                 if(checkaddress.equals("addressok")&&checkshop.equals("shopok")){
                     l1.setVisibility(View.VISIBLE);
                     l2.setVisibility(View.VISIBLE);
                 }else if(checkaddress.equals("addressfaild")&&checkshop.equals("shopfailed")){
                     l3.setVisibility(View.VISIBLE);
                     cardaddress.setVisibility(View.VISIBLE);
                     cardshop.setVisibility(View.VISIBLE);
                 }else if(checkaddress.equals("addressok")){
                     l3.setVisibility(View.VISIBLE);
                     cardaddress.setVisibility(View.INVISIBLE);
                     cardshop.setVisibility(View.VISIBLE);
                 }else if(checkshop.equals("shopok")){
                     l3.setVisibility(View.VISIBLE);
                     cardaddress.setVisibility(View.VISIBLE);
                     cardshop.setVisibility(View.INVISIBLE);
                 }

                // Toast.makeText(getBaseContext(),checkaddress+checkshop,Toast.LENGTH_LONG).show();
             }catch (JSONException e){
                 e.printStackTrace();
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