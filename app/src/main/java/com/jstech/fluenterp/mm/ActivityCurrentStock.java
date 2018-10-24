package com.jstech.fluenterp.mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityCurrentStock extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtResultShow;
    ListView lvCurrStock;
    ArrayList<String> currentStockList;
    ArrayAdapter<String> adapter;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    void init(){
        requestQueue = Volley.newRequestQueue(this);
        progressBar = findViewById(R.id.progressBarCurrentStock);
        txtResultShow = findViewById(R.id.txtResultDetailView);
        lvCurrStock = findViewById(R.id.listViewMaterialStock);
        currentStockList = new ArrayList<>();
        adapter = new ArrayAdapter<>(ActivityCurrentStock.this, android.R.layout.simple_list_item_1,currentStockList);
        lvCurrStock.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarCS);
        setSupportActionBar(toolbar);
        setTitle("Activity Current Stock");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        loadCurrentStock();
    }

    void loadCurrentStock(){
        progressBar.setVisibility(View.VISIBLE);
        lvCurrStock.setVisibility(View.VISIBLE);
        txtResultShow.setVisibility(View.VISIBLE);
        txtResultShow.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveCurrentStock.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            String mdsc;
                            int cs;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("current_stock");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    mdsc = jObj.getString("material_description");
                                    cs = jObj.getInt("stock");
                                    currentStockList.add(mdsc+" - "+cs);
                                }
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityCurrentStock.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityCurrentStock.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityCurrentStock.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
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
