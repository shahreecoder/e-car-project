package com.example.e_carservices.customer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import com.example.e_carservices.owner.loginowner;
import com.example.e_carservices.owner.ownerSession;
import com.example.e_carservices.owner.ownerdashboard;
import com.example.e_carservices.owner.singupowner;

import java.util.HashMap;
import java.util.Map;

public class customerlogin extends AppCompatActivity {
    private Button ownersignup;
    private  Button loginbtn;

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerlogin);
        ownersignup=(Button) findViewById(R.id.ownersignup);
        username=findViewById(R.id.usernameloginowner);
        password=findViewById(R.id.paswordloginowner);
        loginbtn=findViewById(R.id.ownerloginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname;
                Editable pwd;
                uname=username.getText().toString();
                pwd=password.getText();
                if(uname.isEmpty() || pwd.toString().isEmpty()){
                    username.setError("User or pasword must be filled");
                    username.setText("");
                    password.setText("");
                    username.requestFocus();

                }
                login(uname,pwd.toString().trim());
            }
        });
        ownersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent osignup=new Intent(customerlogin.this, customersignup.class);
                startActivity(osignup);
                finish();

            }
        });

    }
    private void login(String name, String pwd) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Login....");
        progressDialog.show();
        clsConnection con=new clsConnection();
        con.getConn();
        StringRequest request= new StringRequest(Request.Method.POST, con.getConn()+"owneruser.php?type=customerlogin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int check=Integer.parseInt(response);
                if(check>0){
                    Toast.makeText(getBaseContext(),"Login Success",Toast.LENGTH_LONG).show();
                    Intent dashboard=new Intent(customerlogin.this, mainhomecustomer.class);
                    CustomerSession customerSession=new CustomerSession(getBaseContext());
                    customerSession.CreateLogin(name,response);
                  //  Toast.makeText(customerlogin.this, customerSession.cutomername()+customerSession.customerid(), Toast.LENGTH_SHORT).show();
                    startActivity(dashboard);
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"Login Failed" ,Toast.LENGTH_LONG).show();
                    username.setError("Please insert correct user name");
                    username.requestFocus();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                username.setError("Please insert correct user name");
                username.setText("");
                password.setText("");
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("username",name);
                params.put("password",pwd);

                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(customerlogin.this);
        requestQueue.add(request);
    };
}