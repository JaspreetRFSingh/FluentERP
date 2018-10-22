package com.jstech.fluenterp.purchasing;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.jstech.fluenterp.models.PurchaseOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityPurchaseOrderDisplay extends AppCompatActivity {


    Button btnRetrieve;
    Button btnRetrieveAll;
    EditText eTxtPurchaseOrder;
    TextView txtViewPurchaseOrder;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ArrayList<PurchaseOrder> purchaseOrderArrayList;
    ArrayList<String> purchaseOrderNameList;
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView txtResultView;
    ProgressBar progressBar;
    String purchasedn;
    PurchaseOrder po;

    void init(){
        requestQueue = Volley.newRequestQueue(this);
        btnRetrieve = findViewById(R.id.btnRetrievePurchaseOrder);
        btnRetrieveAll = findViewById(R.id.btnAllPO);
        txtResultView = findViewById(R.id.txtResultDetailView);
        eTxtPurchaseOrder = findViewById(R.id.editTextPurchaseOrderNumber);
        txtViewPurchaseOrder = findViewById(R.id.txtPurchaseOrderDetail);
        purchaseOrderArrayList = new ArrayList<>();
        purchaseOrderNameList = new ArrayList<>();
        listView = findViewById(R.id.listViewRetrievePurchaseOrders);
        progressBar = findViewById(R.id.progressBarPOD);
        adapter = new ArrayAdapter<>(ActivityPurchaseOrderDisplay.this, android.R.layout.simple_list_item_1, purchaseOrderNameList);
        listView.setAdapter(adapter);
        po = new PurchaseOrder();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_display);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarPOD);
        setSupportActionBar(toolbar);
        setTitle("Display Purchase Order");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();
        btnRetrieveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResultView.setVisibility(View.VISIBLE);
                txtResultView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
                retrieveAllSO();
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResultView.setVisibility(View.VISIBLE);
                txtResultView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
                purchasedn = eTxtPurchaseOrder.getText().toString();
                retrieveSO();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po = purchaseOrderArrayList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPurchaseOrderDisplay.this);
                builder.setTitle(po.getPurchaseDocNumber()+"\n\n\n\n");
                builder.setMessage(po.toString());
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });

    }


    void retrieveAllSO(){
        purchaseOrderNameList.clear();
        purchaseOrderArrayList.clear();
        listView.setVisibility(View.VISIBLE);
        txtViewPurchaseOrder.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        txtViewPurchaseOrder.setText("");
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrievePurchaseOrders.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            long pdn;
                            int sid;
                            String date;
                            String os;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("purchase_order");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    pdn = jObj.getLong("purchase_doc_no");
                                    sid = jObj.getInt("s_id");
                                    date = jObj.getString("date_of_order");
                                    os = jObj.getString("order_status");

                                    PurchaseOrder purchaseOrder = new PurchaseOrder(pdn, sid, date, os);
                                    purchaseOrderArrayList.add(purchaseOrder);
                                    purchaseOrderNameList.add(pdn+" - "+os);
                                }
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityPurchaseOrderDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityPurchaseOrderDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityPurchaseOrderDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
    void retrieveSO(){
        listView.setVisibility(View.GONE);
        txtViewPurchaseOrder.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrievePurchaseOrderWithDoc.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            long pdn;
                            int sid;
                            String date;
                            String os;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("purchase_order");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    pdn = jObj.getLong("purchase_doc_no");
                                    sid = jObj.getInt("s_id");
                                    date = jObj.getString("date_of_order");
                                    os = jObj.getString("order_status");
                                    PurchaseOrder purchaseOrder = new PurchaseOrder(pdn, sid, date, os);
                                    txtViewPurchaseOrder.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right));
                                    txtViewPurchaseOrder.setText(purchaseOrder.toString());
                                    txtViewPurchaseOrder.clearAnimation();
                                }

                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityPurchaseOrderDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityPurchaseOrderDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityPurchaseOrderDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("purchase_doc_no",purchasedn);
                return map;
            }
        }
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