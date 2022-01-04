package com.example.e_carservices;

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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class singupowner extends AppCompatActivity {

    Button btnsignup, btnlogin;
    EditText name,uname,owneremail,ownerpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singupowner);

        btnsignup=findViewById(R.id.btnowensignup);
        name=findViewById(R.id.fullownername);
        uname=findViewById(R.id.usernameowner);
        owneremail=findViewById(R.id.emailowner);
        ownerpassword=findViewById(R.id.paswordowner);
        btnlogin=findViewById(R.id.btnownerlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(singupowner.this, loginowner.class);
                startActivity(in);
                finish();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationsignup();

            }
        });

    }

    private void validationsignup() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Record in inserted");
        boolean check = true;
        String fullname=name.getText().toString();
        String username=uname.getText().toString();
        String email=owneremail.getText().toString().trim();
        Editable password=ownerpassword.getText();
        Pattern pattern = Pattern.compile("[^a-zA-Z_ ]");
        Matcher matcher = pattern.matcher(fullname);
        boolean isanyspicalcharacterinfullname=matcher.find();
        Pattern isEmail=Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher EmailMatcher =isEmail.matcher(email);
        boolean isemailmatch=EmailMatcher.find();
        if (isanyspicalcharacterinfullname || fullname.isEmpty()){
            name.setError("Please input Correct Name");
            check=false;
        } if(username.isEmpty()|| username.length()<=6){
            uname.setError("Username must more then 6");
            check=false;
        }

        if(email.length()>=0){

            int count = 0;
            for(int i=0; i<email.length();i++){
                if(email.charAt(i)=='@'){
                    count++;
                }
            }
            if(count >1){
                owneremail.setError("Email Formate is incorrect");
                check=false;
            }  else if(isemailmatch!=true){
                owneremail.setError("Email Formate is incorrect");
                check=false;
            }

        }else{
            owneremail.setError("Email  is Empty");
        }


        if(password.length()<=6 || password.toString().isEmpty()){
            ownerpassword.setError("Password is too Weak");
            check=false;
        }
        else if(check){
            progressDialog.show();
            clsOwnerSignup clsownersignup=new clsOwnerSignup();
            clsownersignup.setEmail(email);
            clsownersignup.setFullname(fullname);
            clsownersignup.setPassword(password.toString());
            clsownersignup.setUsername(username);


                clsConnection con=new clsConnection();
                con.getConn();
                StringRequest request=new StringRequest(Request.Method.POST, con.getConn() + "owneruser.php?type=ownersignup", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        name.setText("");
                        uname.setText("");
                        owneremail.setText("");
                        ownerpassword.setText("");
                        name.requestFocus();
                        Toast.makeText(getBaseContext(),response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String,String>();
                        params.put("fullname",clsownersignup.getFullname());
                        params.put("email",clsownersignup.getEmail());
                        params.put("username",clsownersignup.getUsername());
                        params.put("password",clsownersignup.getPassword());
                        return params;

                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(singupowner.this);
                requestQueue.add(request);

            //Toast.makeText(getBaseContext(),clsownersignup.signupowner()+" "+clsownersignup.getUsername()+" "+clsownersignup.getPassword(),Toast.LENGTH_SHORT).show();
        }
    }

}