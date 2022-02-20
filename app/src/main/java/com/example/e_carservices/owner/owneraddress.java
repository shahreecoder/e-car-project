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

public class owneraddress extends AppCompatActivity {

    EditText shopno,streetno,adblock,adtown,adcity;
    Button btnadd;
    //String addressid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owneraddress);
        shopno=findViewById(R.id.HouseNo);
        streetno=findViewById(R.id.streeNo);
        adblock=findViewById(R.id.Block);
        adtown=findViewById(R.id.Town);
        adcity=findViewById(R.id.citylahore);
        btnadd=findViewById(R.id.btnaddaddress);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopno.getText()!=null && streetno.getText()!=null && adblock.getText()!=null && adtown.getText()!=null){
                    addaddress(shopno.getText().toString(),streetno.getText().toString(),adblock.getText().toString(), adtown.getText().toString());
                }

            }
        });



    }
    public void addaddress(String shopno, String streetno, String adblock, String adtown){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Address is adding");
        progressDialog.show();
        clsConnection con=new clsConnection();
        con.getConn();
        StringRequest request=new StringRequest(Request.Method.POST, con.getConn() + "addowneraddress.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int check=Integer.parseInt(response);
                if(check>0){
                    updateuseraddress(response);
                    progressDialog.dismiss();
                }
                //addressid=response;




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("shopno",shopno);
                params.put("streetno",streetno);
                params.put("block",adblock);
                params.put("town",adtown);
                params.put("city","Lahore");
                params.put("type","insert");

                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(owneraddress.this);
        requestQueue.add(request);
    }
    public void updateuseraddress(String addressid){
        ownerSession ownerSession = new ownerSession(this);

        clsConnection con=new clsConnection();
        con.getConn();
        StringRequest request=new StringRequest(Request.Method.POST, con.getConn() + "addowneraddress.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Updated")){
                    Toast.makeText(getBaseContext(),"Address has been updated",Toast.LENGTH_LONG).show();
                    Intent address=new Intent(owneraddress.this,ownerdashboard.class);
                    startActivity(address);
                    finish();
                }
                //addressid=response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("adid",addressid);
                params.put("username",ownerSession.ownername().toString());
                params.put("type","update");
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(owneraddress.this);
        requestQueue.add(request);
    }
}