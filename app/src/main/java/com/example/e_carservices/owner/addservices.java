package com.example.e_carservices.owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class addservices extends AppCompatActivity {
    Button btnaddservice;
    EditText Sprice, Sname, Sdisp;
    ImageView Simage;
    Bitmap bitmap;
    String encodedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addservices);
        btnaddservice=findViewById(R.id.btnaddservice);
        Sname=findViewById(R.id.ServiceName);
        Sprice=findViewById(R.id.ServicePrice);
        Sdisp=findViewById(R.id.ServiceDisp);
        Simage=findViewById(R.id.adserviceimage);
        ownerSession ownerSession=new ownerSession(this);


        Simage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(addservices.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Service Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        btnaddservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(encodedimage==null||Sname.getText().toString().isEmpty() || Sprice.getText().toString().isEmpty()||Sdisp.getText().toString().isEmpty()){
                    Toast.makeText(addservices.this,"Please Provide Require information", Toast.LENGTH_LONG).show();
                }else{
                    //Toast.makeText(addservices.this,Sname.getText().toString()+ Sdisp.getText().toString()+Sprice.getText().toString()+ ownerSession.owenid(), Toast.LENGTH_LONG).show();
                    insertservice(Sname.getText().toString(),Sdisp.getText().toString(),Sprice.getText().toString(),encodedimage, ownerSession.owenid());
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && data!=null && resultCode==RESULT_OK){
            Uri filepath=data.getData();
            try {
                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                Simage.setImageBitmap(bitmap);
                storeimage(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void storeimage(Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte [] imageBytes=stream.toByteArray();
        encodedimage=android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void insertservice(String sn, String disp, String price, String image, String ownerid){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Service is adding");
        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "addownerservice.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("inserted")) {
                    Toast.makeText(getBaseContext(), "Service has been Added", Toast.LENGTH_LONG).show();
                    Sname.setText("");
                    Sprice.setText("");
                    Sdisp.setText("");
                    Simage.setImageResource(R.drawable.uploadimageicon);
                    Sname.requestFocus();
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

                params.put("sname", sn);
                params.put("sdisp", disp);
                params.put("sprice", price);
                params.put("simage", image);
                params.put("ownerid", ownerid);

                params.put("type", "insert");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(addservices.this);
        requestQueue.add(request);

    }
}