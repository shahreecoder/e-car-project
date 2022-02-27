package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class servicedetails extends AppCompatActivity {
    TextView tvsname, tvsprice, tvdisp;
    ImageView imgservice;
    Button btnedit, btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetails);
        tvsname=findViewById(R.id.SnameD);
        tvsprice=findViewById(R.id.SpriceD);
        tvdisp=findViewById(R.id.SdispD);
        imgservice=findViewById(R.id.Simage);
        btnedit=findViewById(R.id.btneditownerservice);
        btndelete=findViewById(R.id.btndeleteservice);

        tvsname.setText(getIntent().getStringExtra("Sname"));
        tvsprice.setText(getIntent().getStringExtra("Sprice"));
        tvdisp.setText(getIntent().getStringExtra("Sdisp"));
        String imgurl=getIntent().getStringExtra("Simage");
        Picasso.get().load("https://ecar.shahreecoder.com/api/uploads/"+imgurl).into(imgservice);
        //Toast.makeText(servicedetails.this,sname,Toast.LENGTH_LONG).show();
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteownerservice(getIntent().getStringExtra("Sid"));
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateservice=new Intent(servicedetails.this,addservices.class);
                updateservice.putExtra("Sid",getIntent().getStringExtra("Sid"));
                updateservice.putExtra("Sname",getIntent().getStringExtra("Sname"));
                updateservice.putExtra("Sprice",getIntent().getStringExtra("Sprice"));
                updateservice.putExtra("Sdisp",getIntent().getStringExtra("Sdisp"));
                updateservice.putExtra("Simage",getIntent().getStringExtra("Simage"));
                startActivity(updateservice);
            }
        });
    }
    public void  deleteownerservice( String Sid){
        ownerSession ownerSession = new ownerSession(this);

        clsConnection con=new clsConnection();
        con.getConn();
        StringRequest request=new StringRequest(Request.Method.POST, con.getConn() + "deleteservice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("deleted")){
                    Toast.makeText(getBaseContext(),"Service has been Deleted",Toast.LENGTH_LONG).show();
                    finish();
                }
                //addressid=response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("Sid",Sid);
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(servicedetails.this);
        requestQueue.add(request);
    }
}