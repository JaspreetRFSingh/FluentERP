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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.adapters.AdapterOrderStatusPurchase;
import com.jstech.fluenterp.models.PurchaseOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChangePurchaseOrderStatus extends AppCompatActivity {

    ProgressBar progressBar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    AdapterOrderStatusPurchase adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    static ArrayList<PurchaseOrder> data;
    TextView txtSearchOrderStatus;
    EditText eTxtSearchOrderStatus;

    void initViews(){
        requestQueue = Volley.newRequestQueue(this);
        progressBar = findViewById(R.id.progressBarCPOS);
        progressBar.setVisibility(View.VISIBLE);
        txtSearchOrderStatus = findViewById(R.id.txtSearchOrderStatus);
        eTxtSearchOrderStatus = findViewById(R.id.editTextSearchPurchaseDocNo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_purchase_order_status);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarCPOS);
        setSupportActionBar(toolbar);
        setTitle("Check Order Status");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        initRecyclerView();
        txtSearchOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchOrderStatus.setVisibility(View.GONE);
                eTxtSearchOrderStatus.setVisibility(View.VISIBLE);
            }
        });
        retrieveRecords();
        eTxtSearchOrderStatus.addTextChangedListener(new TextWatcher() {
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


    void initRecyclerView(){

        recyclerView = findViewById(R.id.recyclerViewSalesOrderStatus);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
    }

    void retrieveRecords(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveOrderStatusPurchase.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            long pdn;
                            String os;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("purchase_orders_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    pdn = jObj.getLong("purchase_doc_no");
                                    os = jObj.getString("order_status");
                                    PurchaseOrder po = new PurchaseOrder(pdn, 0, "0", os);
                                    data.add(po);
                                }
                                progressBar.setVisibility(View.GONE);
                                adapter = new AdapterOrderStatusPurchase(data);
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
