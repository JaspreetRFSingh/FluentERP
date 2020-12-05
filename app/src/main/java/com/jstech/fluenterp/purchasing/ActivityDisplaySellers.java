package com.jstech.fluenterp.purchasing;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.adapters.AdapterDisplaySellers;
import com.jstech.fluenterp.models.Seller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDisplaySellers extends BaseActivity {

    ProgressBar progressBar;
    StringRequest stringRequest;
    AdapterDisplaySellers adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Seller> data;
    TextView txtSearchSeller;
    TextView txtTitleHead;
    EditText eTxtSearchSeller;

    void init(){
        progressBar = findViewById(R.id.progressBarDS);
        progressBar.setVisibility(View.GONE);
        txtTitleHead = findViewById(R.id.txtViewTitleHeadSellersList);
        txtSearchSeller = findViewById(R.id.txtSearchSeller);
        eTxtSearchSeller = findViewById(R.id.editTextSearchSeller);
        recyclerView = findViewById(R.id.recyclerViewSellersList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sellers);
        setupToolbar(R.id.toolbarDS, "List of Sellers");
        init();
        txtTitleHead.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        txtSearchSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchSeller.setVisibility(View.GONE);
                eTxtSearchSeller.setVisibility(View.VISIBLE);
            }
        });
        retrieveRecords();
        eTxtSearchSeller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void retrieveRecords(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_SELLERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            long sid;
                            String n;
                            String ad;
                            String c;
                            String gst;
                            String p;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("sellers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sid = jObj.getLong("s_id");
                                    n = jObj.getString("s_name");
                                    ad = jObj.getString("s_address");
                                    c = jObj.getString("s_city");
                                    gst = jObj.getString("s_gst");
                                    p = jObj.getString("s_phone");
                                    Seller so = new Seller(sid, n, ad, c, gst, p);
                                    data.add(so);
                                }
                                progressBar.setVisibility(View.GONE);
                                adapter = new AdapterDisplaySellers(data);
                                recyclerView.setAdapter(adapter);
                            }else{
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        )
        ;
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}