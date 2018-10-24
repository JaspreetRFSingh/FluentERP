package com.jstech.fluenterp.sd;

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
import com.jstech.fluenterp.models.SalesOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivitySalesOrderDisplay extends AppCompatActivity {

    Button btnRetrieve;
    Button btnRetrieveAll;
    EditText eTxtSalesOrder;
    TextView txtViewSalesOrder;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ArrayList<SalesOrder> salesOrderArrayList;
    ArrayList<String> salesOrderNameList;
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView txtResultView;
    ProgressBar progressBar;
    String salesdn;
    SalesOrder so;

    void init(){
        requestQueue = Volley.newRequestQueue(this);
        btnRetrieve = findViewById(R.id.btnRetrieveSalesOrder);
        btnRetrieveAll = findViewById(R.id.btnAllSO);
        txtResultView = findViewById(R.id.txtResultDetailView);
        eTxtSalesOrder = findViewById(R.id.editTextSalesOrderNumber);
        txtViewSalesOrder = findViewById(R.id.txtSalesOrderDetail);
        salesOrderArrayList = new ArrayList<>();
        salesOrderNameList = new ArrayList<>();
        listView = findViewById(R.id.listViewRetrieveSalesOrders);
        progressBar = findViewById(R.id.progressBarSOD);
        adapter = new ArrayAdapter<>(ActivitySalesOrderDisplay.this, android.R.layout.simple_list_item_1, salesOrderNameList);
        listView.setAdapter(adapter);
        so = new SalesOrder();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_display);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarSOD);
        setSupportActionBar(toolbar);
        setTitle("Display Sales Order");
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
                salesdn = eTxtSalesOrder.getText().toString();
                retrieveSO();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                so = salesOrderArrayList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderDisplay.this);
                builder.setTitle(so.getSalesDocNumber()+"\n\n\n\n");
                builder.setMessage(so.toString());
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });
    }

    void retrieveAllSO(){
        listView.setVisibility(View.VISIBLE);
        txtViewSalesOrder.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        txtViewSalesOrder.setText("");
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveSalesOrder.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");

                    long sdn;
                    int cust;
                    String date;
                    double billp;
                    String os;
                    if(success == 1){
                        JSONArray jsonArray = jsonObject.getJSONArray("sales_orders");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jObj = jsonArray.getJSONObject(i);

                            sdn = jObj.getLong("sales_doc_no");
                            cust = jObj.getInt("Customer");
                            date = jObj.getString("date_of_order");
                            billp = jObj.getDouble("bill_price");
                            os = jObj.getString("order_status");

                            SalesOrder salesOrder = new SalesOrder(sdn, cust, date, billp, os);
                            salesOrderArrayList.add(salesOrder);
                            salesOrderNameList.add(sdn+" - "+billp);
                        }
                        listView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ActivitySalesOrderDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ActivitySalesOrderDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivitySalesOrderDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }
    void retrieveSO(){
        listView.setVisibility(View.GONE);
        txtViewSalesOrder.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/filterSalesOrderWithSalesDocument.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            long sdn;
                            int cust;
                            String date;
                            double billp;
                            String os;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("sales_orders_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    sdn = jObj.getLong("sales_doc_no");
                                    cust = jObj.getInt("Customer");
                                    date = jObj.getString("date_of_order");
                                    billp = jObj.getDouble("bill_price");
                                    os = jObj.getString("order_status");
                                    SalesOrder salesOrder = new SalesOrder(sdn, cust, date, billp, os);
                                    txtViewSalesOrder.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right));
                                    txtViewSalesOrder.setText(salesOrder.toString());
                                    txtViewSalesOrder.clearAnimation();
                                }

                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivitySalesOrderDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivitySalesOrderDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivitySalesOrderDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("sales_doc_no",salesdn);
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
