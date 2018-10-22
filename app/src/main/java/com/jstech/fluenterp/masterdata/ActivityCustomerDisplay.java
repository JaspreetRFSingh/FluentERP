package com.jstech.fluenterp.masterdata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Customer;


public class ActivityCustomerDisplay extends AppCompatActivity{

    Button btnRetrieve;
    Button btnRetrieveAll;
    EditText eTxtCustomer;
    TextView txtViewCustomer;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    Customer cmr;

    ArrayList<Customer> customerList;
    ArrayList<String> customerNameList;
    ArrayAdapter<String> adapter;
    ListView listView;
    Context ctx;

    ProgressBar progressBar;

    boolean checkInternetConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo!=null && networkInfo.isConnected();
    }


    String customerO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));

        progressBar = findViewById(R.id.progressBarCD);
        ctx = this;
        txtViewCustomer = findViewById(R.id.txtCustomerSingle);
        cmr = new Customer();
        requestQueue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.listViewRetrieveCustomers);
        btnRetrieve = findViewById(R.id.btnRetrieveCustomers);
        btnRetrieveAll = findViewById(R.id.btnAllCustomers);
        eTxtCustomer = findViewById(R.id.editTextCustomerNumber);

        Toolbar toolbar = findViewById(R.id.toolbarCD);
        setSupportActionBar(toolbar);
        setTitle("Display Customer Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        requestQueue = Volley.newRequestQueue(this);
        btnRetrieveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternetConnectivity())
                {
                    txtViewCustomer.setText("");
                    retrieveCustomers();
                }
                else
                    Toast.makeText(ctx,"Please Check Internet Connectivity and try Again",Toast.LENGTH_LONG).show();
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternetConnectivity())
                {
                    listView = null;
                    customerO = eTxtCustomer.getText().toString().trim();
                    retrieveCustomersWithCondition();}
                else
                {Toast.makeText(ctx, "Please Check Your Internet Connection and Try Again!", Toast.LENGTH_LONG).show();}
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cmr = customerList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCustomerDisplay.this);
                builder.setTitle(cmr.getName()+"\n\n\n\n");
                builder.setMessage(cmr.toString());
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();
            }
        });

    }


    void retrieveCustomersWithCondition()
    {
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveUWC.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
                            int c;
                            int p;
                            String n;
                            String a;
                            String ci;
                            String g;
                            Toast.makeText(ActivityCustomerDisplay.this,message,Toast.LENGTH_LONG).show();
                            if(success == 1){

                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    c = jObj.getInt("Customer_id");
                                    n = jObj.getString("Name");
                                    a = jObj.getString("Address");
                                    ci = jObj.getString("City");
                                    p = jObj.getInt("Contact");
                                    g = jObj.getString("GST_Number");

                                    Customer customer = new Customer(c,n,a,ci,p,g);
                                    txtViewCustomer.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.slide_right));
                                    txtViewCustomer.setText(customer.toString());
                                    txtViewCustomer.clearAnimation();
                                }
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityCustomerDisplay.this,"No records found!!!",Toast.LENGTH_LONG).show();
                            }
                            eTxtCustomer.setText("");


                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityCustomerDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ActivityCustomerDisplay.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String,String> map = new HashMap<>();

                map.put("Customer_id",String.valueOf(customerO));

                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);

    }
    void retrieveCustomers(){

        progressBar.setVisibility(View.VISIBLE);
        txtViewCustomer.setText("");
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveU.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            int c;
                            int p;
                            String n;
                            String a;
                            String ci;
                            String g;
                            if(success == 1){
                                customerList = new ArrayList<>();
                                customerNameList = new ArrayList<>();

                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    c = jObj.getInt("Customer_id");
                                    n = jObj.getString("Name");
                                    a = jObj.getString("Address");
                                    ci = jObj.getString("City");
                                    p = jObj.getInt("Contact");
                                    g = jObj.getString("GST_Number");

                                    Customer customer = new Customer(c,n,a,ci,p,g);
                                    customerList.add(customer);
                                    customerNameList.add(n);
                                }

                                adapter = new ArrayAdapter<>(ActivityCustomerDisplay.this, android.R.layout.simple_list_item_1, customerNameList);
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityCustomerDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityCustomerDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ActivityCustomerDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}