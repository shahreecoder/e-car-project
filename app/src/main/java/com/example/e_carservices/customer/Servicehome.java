package com.example.e_carservices.customer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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

public class Servicehome extends AppCompatActivity {

    private GridView gridView;

    //ArrayList for Storing image urls and titles

    private ArrayList<cardmodel> cardmodelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicehome);

        gridView = (GridView) findViewById(R.id.gridView);

        cardmodelArrayList = new ArrayList<>();


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custome_exit_dialog);

        final Button btnyess = dialog.findViewById(R.id.btnyess);
        final Button btnno = dialog.findViewById(R.id.btnno);
        final TextView titletxt=dialog.findViewById(R.id.txtexit);
        titletxt.setText("Please Login To Continue");
        btnyess.setText("Login");
        btnno.setText("Cancel");


        btnyess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                getData();

            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void getData() {




        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "fatchtopservices.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                cardmodelArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String sid=jsonArray.getJSONObject(i).getString("Sid");
                        String stitle = jsonArray.getJSONObject(i).getString("Sname");
                        String sprice = jsonArray.getJSONObject(i).getString("Sprice");
                        String sdisp = jsonArray.getJSONObject(i).getString("Sdisp");
                        String simg = jsonArray.getJSONObject(i).getString("Simage");

                        //modelservice = new modelservice(String.valueOf(i + 1), sname, "j", "j", simg);

                        cardmodelArrayList.add(new cardmodel(stitle,sprice,simg,sid,sdisp));

                    }
                    gridAdapter gridViewAdapter = new gridAdapter(getBaseContext(),cardmodelArrayList);

                    //Adding adapter to gridview
                    gridView.setAdapter(gridViewAdapter);

                    progressDialog.dismiss();


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
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("ownerid","owner");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(request);
    }


}