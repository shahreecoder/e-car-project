package com.example.e_carservices.customer;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.e_carservices.owner.manageservices;
import com.example.e_carservices.owner.modelservice;
import com.example.e_carservices.owner.serviceadaptor;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    View view;
    List<Integer> images = new ArrayList<>();
    RecyclerView recyclerView;
    private ArrayList<cardmodel> cardmodelArrayList;
    private MycardAdapter mycardAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        images.add(R.drawable.oneslid);
        images.add(R.drawable.twoslid);

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        recyclerView=view.findViewById(R.id.topdealRV);
        sliderAdapter sliderAdapter = new sliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        loadcard();


//        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);




        return view;
    }

    private void loadcard() {
    cardmodelArrayList =new ArrayList<>();

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

                        cardmodelArrayList.add(new cardmodel(stitle,sprice,simg));

                    }


                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(
                            getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false
                    );
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    mycardAdapter=new MycardAdapter(cardmodelArrayList,getContext());
                    recyclerView.setAdapter(mycardAdapter);

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