package com.example.e_carservices.customer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DealsFragment extends Fragment {
    View view;
    private ArrayList<cardmodel> dealmodelArrayList;
    private  TopDealAdapter dealAdapter;
    private ListView topdeallv;

    public DealsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_deals, container, false);
        topdeallv=view.findViewById(R.id.topdeallist);


        loaddeals();




        return view;

    }




    private void loaddeals() {
        dealmodelArrayList =new ArrayList<>();

//        cardmodelArrayList.add(new cardmodel(
//                "title 01",
//                "200",
//                R.drawable.oneslid
//        ));
//        cardmodelArrayList.add(new cardmodel(
//                "title 02",
//                "400",
//                R.drawable.twoslid
//        ));
//        cardmodelArrayList.add(new cardmodel(
//                "title 03",
//                "500",
//                R.drawable.threeslid
//        ));
//        mycardAdapter =new MycardAdapter(getActivity().getBaseContext(), cardmodelArrayList);
//        viewPager.setAdapter(mycardAdapter);
//        viewPager.setPadding(100,0,100,0);
//        ProgressDialog progressDialog = new ProgressDialog(getActivity().getApplicationContext());
//        progressDialog.setTitle("Please Wait...");
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        clsConnection con = new clsConnection();
        con.getConn();
        StringRequest request = new StringRequest(Request.Method.POST, con.getConn() + "fatchtopservices.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                dealmodelArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

//                    modelservice =new modelservice("1",jsonArray.getJSONObject(2).getString("Sname"),"111","sdsd","ssss");
//                    modelserviceArrayList.add(modelservice);
//                    serviceadaptor.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        String sid=jsonArray.getJSONObject(i).getString("Sid");
                        String stitle = jsonArray.getJSONObject(i).getString("Sname");
                        String sprice = jsonArray.getJSONObject(i).getString("Sprice");
                        String sdisp = jsonArray.getJSONObject(i).getString("Sdisp");
                        String simg = jsonArray.getJSONObject(i).getString("Simage");

                        //modelservice = new modelservice(String.valueOf(i + 1), sname, "j", "j", simg);

                        dealmodelArrayList.add(new cardmodel(stitle,sprice,simg,sid,sdisp));

                    }
                    dealAdapter= new TopDealAdapter(getActivity().getBaseContext(), dealmodelArrayList);
                    topdeallv.setAdapter(dealAdapter);

                    // mycardAdapter.notifyDataSetChanged();
                    //mycardAdapter =new

                    //viewPager.setPadding(100,0,100,0);
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
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);


    }
}