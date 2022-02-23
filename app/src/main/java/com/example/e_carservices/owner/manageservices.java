package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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

public class manageservices extends AppCompatActivity {
    ListView listView;
    serviceadaptor serviceadaptor;
    modelservice modelservice;
    public static ArrayList<modelservice> modelserviceArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageservices);
        listView=findViewById(R.id.servicelist);
        serviceadaptor=new serviceadaptor(manageservices.this,modelserviceArrayList);
        listView.setAdapter(serviceadaptor);
        ownerSession ownerSession=new ownerSession(this);
        servicedata(ownerSession.owenid());

    }
    public void servicedata(String name) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "factchservice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                modelserviceArrayList.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("data");

//                    modelservice =new modelservice("1",jsonArray.getJSONObject(2).getString("Sname"),"111","sdsd","ssss");
//                    modelserviceArrayList.add(modelservice);
//                    serviceadaptor.notifyDataSetChanged();
                        for(int i=0; i<jsonArray.length();i++){

                            //int id=jsonArray.getJSONObject(2).getInt("Sid");
                            String sname=jsonArray.getJSONObject(i).getString("Sname");
                            String sprice=jsonArray.getJSONObject(i).getString("Sprice");
                            String sdisp=jsonArray.getJSONObject(i).getString("Sdisp");
                            String simg=jsonArray.getJSONObject(i).getString("Simage");

                            modelservice =new modelservice(String.valueOf(i+1),sname,"j","j",simg);
                            modelserviceArrayList.add(modelservice);
                            serviceadaptor.notifyDataSetChanged();

                    }
                    Toast.makeText(getBaseContext(),"test",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
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
                params.put("ownerid", name);

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(manageservices.this);
        requestQueue.add(request);
    }
}