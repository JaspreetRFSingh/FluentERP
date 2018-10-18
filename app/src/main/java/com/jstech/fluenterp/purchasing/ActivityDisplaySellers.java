package com.jstech.fluenterp.purchasing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.adapters.AdapterDisplaySellers;
import com.jstech.fluenterp.models.Seller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDisplaySellers extends AppCompatActivity {

    ProgressBar progressBar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    AdapterDisplaySellers adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Seller> data;
    TextView txtSearchSeller;
    TextView txtTitleHead;
    EditText eTxtSearchSeller;

    void init(){
        requestQueue = Volley.newRequestQueue(this);
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
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarDS);
        setSupportActionBar(toolbar);
        setTitle("List of Sellers");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveSellers.php",
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
        requestQueue.add(stringRequest);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}