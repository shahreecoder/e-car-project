package com.example.e_carservices.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_carservices.MasterHome;
import com.example.e_carservices.R;
import com.example.e_carservices.database.addtocart;
import com.example.e_carservices.database.clsConnection;
import com.example.e_carservices.owner.addservices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class checkout extends AppCompatActivity {

    FusedLocationProviderClient client;
    Geocoder geocoder;
    List<Address> addresses;
    TextView mainaddress, fatchlocation, finalprice;
    EditText phn;
    SupportMapFragment smf;
    Button btnplace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
//        mainaddress=findViewById(R.id.mainaddress);
        fatchlocation = findViewById(R.id.fatchlocation);
        finalprice = findViewById(R.id.finalprice);
        phn = findViewById(R.id.customerphone);
        btnplace = findViewById(R.id.orderplace);
        Intent intent = getIntent();
        finalprice.setText("Total Price is: " + intent.getStringExtra("total") + " /PKR");
        client = LocationServices.getFusedLocationProviderClient(this);
        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        fatchlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmylocation();
            }
        });
        btnplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phn.getText().toString().isEmpty()) {
                    Toast.makeText(checkout.this, "Please input phone Number", Toast.LENGTH_SHORT).show();
                } else if (phn.length() < 11) {
                    Toast.makeText(checkout.this, "Please input phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    placeorder();
                }

            }
        });


    }

    private void placeorder() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Service is adding");
        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "order.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {


                    CustomerSession customerSession = new CustomerSession(checkout.this);
                    addtocart addtocart = new addtocart(checkout.this);
                    Cursor cursor = addtocart.getcartdata(customerSession.customerid());
                    if (cursor.moveToFirst()) {
                        do {
                            orderitem(cursor.getString(2), response);


                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    addtocart a=new addtocart(getBaseContext());
                    a.clearCart(customerSession.customerid());
                    Intent intent=new Intent(checkout.this,mainhomecustomer.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(checkout.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
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

                CustomerSession customerSession = new CustomerSession(checkout.this);

                params.put("cusid", customerSession.customerid());
                params.put("address", customerSession.getaddress());
                Intent intent = getIntent();
                params.put("total", intent.getStringExtra("total"));
                params.put("phn", phn.getText().toString());
                params.put("type", "insert");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(checkout.this);
        requestQueue.add(request);
    }

    private void orderitem(String proid, String orderid) {
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "order.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(checkout.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("proid", proid);
                params.put("orid", orderid);
                params.put("type", "orderdetail");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(checkout.this);
        requestQueue.add(request);
    }

    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here...!!");


                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                    }
                });
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    CustomerSession customerSession = new CustomerSession(checkout.this);

                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    customerSession.setaddress(addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality());
//                    mainaddress.setText(addresses.get(0).getAddressLine(0));
                    Toast.makeText(checkout.this, customerSession.getaddress(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


}