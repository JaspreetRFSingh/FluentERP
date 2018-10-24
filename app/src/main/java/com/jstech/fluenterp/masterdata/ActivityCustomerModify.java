package com.jstech.fluenterp.masterdata;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityCustomerModify extends AppCompatActivity {

    EditText eTxtCustomerName;
    EditText eTxtCustomerAddress;
    EditText eTxtCustomerPhone;
    EditText eTxtCustomerCity;
    EditText eTxtCustomerGST;
    Spinner spCustChoice;
    String choiceStr;
    Button btnModifyCustomer;
    TextView txtViewDate;
    String date;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    ArrayAdapter<String> adapterChoice;
    StringRequest stringRequest;


    @SuppressLint("SetTextI18n")
    void initViews(){
        eTxtCustomerName = findViewById(R.id.customerNameMC);
        eTxtCustomerAddress = findViewById(R.id.customerAddressMC);
        eTxtCustomerCity = findViewById(R.id.customerCityMC);
        eTxtCustomerPhone = findViewById(R.id.customerPhoneMC);
        eTxtCustomerGST = findViewById(R.id.customerGSTMC);
        spCustChoice = findViewById(R.id.spinnerCustomerChoice);
        choiceStr = "";
        btnModifyCustomer = findViewById(R.id.btnModifyCustomer);
        txtViewDate = findViewById(R.id.dateShowCM);
        progressBar = findViewById(R.id.progressBarCM);
        adapterChoice = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterChoice.add("Choose a Customer");
        requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        txtViewDate.setText(date.substring(6,8)+"/"+date.substring(4,6)+"/"+date.substring(0,4));
        fillCustomerSpinner();
        spCustChoice.setAdapter(adapterChoice);
    }

    void fillCustomerSpinner(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveU.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int c;
                            String n;
                            if(success == 1){

                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    c = jObj.getInt("Customer_id");
                                    n = jObj.getString("Name");
                                    adapterChoice.add(Integer.toString(c) +" - "+ n);
                                }
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),"None Found",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
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
        );
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_modify);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarCM);
        setSupportActionBar(toolbar);
        setTitle("Modify Customer Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        spCustChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceStr = adapterChoice.getItem(position);
                if(position !=0){
                int ind = Objects.requireNonNull(choiceStr).indexOf("-") - 1;
                fillOutFields(choiceStr.substring(0,ind));}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnModifyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer();
            }
        });
    }

    boolean checkRecords(){
        boolean ch = true;
        if(TextUtils.isEmpty(eTxtCustomerName.getText().toString())){
            eTxtCustomerName.setError("Name is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtCustomerPhone.getText().toString())){
            eTxtCustomerPhone.setError("Phone is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtCustomerAddress.getText().toString())){
            eTxtCustomerAddress.setError("Address is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtCustomerCity.getText().toString())){
            eTxtCustomerCity.setError("City is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtCustomerGST.getText().toString())){
            eTxtCustomerGST.setError("GST Number is required!");
            ch = false;
        }
        return ch;
    }

    void updateCustomer(){

        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/modifyCustomer.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                progressBar.setVisibility(View.GONE);
                                clearFields();
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCustomerModify.this);
                                builder.setTitle(eTxtCustomerName.getText().toString());
                                builder.setMessage("Successfully modified "+eTxtCustomerName.getText().toString());
                                builder.setPositiveButton("Modify Another Customer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(ActivityCustomerModify.this, ActivityCustomerModify.class));
                                    }
                                });
                                builder.setNegativeButton("View Customer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityCustomerModify.this, ActivityCustomerDisplay.class));
                                    }
                                });
                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                    }
                                });
                                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                                dialog.show();

                            }catch (Exception e){
                                Toast.makeText(ActivityCustomerModify.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ActivityCustomerModify.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    int in = choiceStr.indexOf("-") - 1;
                    map.put("Customer_id", choiceStr.substring(0,in));
                    map.put("Name", eTxtCustomerName.getText().toString());
                    map.put("Address", eTxtCustomerAddress.getText().toString());
                    map.put("City", eTxtCustomerCity.getText().toString());
                    map.put("Contact", eTxtCustomerPhone.getText().toString());
                    map.put("GST_Number", eTxtCustomerGST.getText().toString());
                    return map;
                }
            }
            ;
            requestQueue.add(stringRequest);
        }

    }

    void clearFields(){
        eTxtCustomerName.setText("");
        eTxtCustomerAddress.setText("");
        eTxtCustomerCity.setText("");
        eTxtCustomerPhone.setText("");
        eTxtCustomerGST.setText("");
    }

    void fillOutFields(final String strCustId){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveCustomerById.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String cName;
                            String cAddress;
                            String cCity;
                            long cPhone;
                            String cGST;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    cName = jObj.getString("Name");
                                    cAddress = jObj.getString("Address");
                                    cCity = jObj.getString("City");
                                    cPhone = jObj.getLong("Contact");
                                    cGST = jObj.getString("GST_Number");
                                    eTxtCustomerName.setText(cName);
                                    eTxtCustomerAddress.setText(cAddress);
                                    eTxtCustomerCity.setText(cCity);
                                    eTxtCustomerPhone.setText(String.valueOf(cPhone));
                                    eTxtCustomerGST.setText(cGST);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                // Toast.makeText(getApplicationContext(),"FillOutFields None Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("cust_id", strCustId);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }





    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            if(!eTxtCustomerName.getText().toString().trim().isEmpty() || !eTxtCustomerAddress.getText().toString().trim().isEmpty() || !eTxtCustomerCity.getText().toString().trim().isEmpty() || !eTxtCustomerPhone.getText().toString().trim().isEmpty()  || !eTxtCustomerGST.getText().toString().trim().isEmpty())
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCustomerModify.this);
                builder.setTitle("BACK!\n\n");
                builder.setMessage("Your form is currently under progress. Are you sure you want to go back?");
                builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }else
            {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!eTxtCustomerName.getText().toString().trim().isEmpty() || !eTxtCustomerAddress.getText().toString().trim().isEmpty() || !eTxtCustomerCity.getText().toString().trim().isEmpty() || !eTxtCustomerPhone.getText().toString().trim().isEmpty()  || !eTxtCustomerGST.getText().toString().trim().isEmpty())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCustomerModify.this);
            builder.setTitle("BACK!\n\n");
            builder.setMessage("Your form is currently under progress. Are you sure you want to go back?");
            builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
            dialog.show();
        }else
        {
            finish();
        }
    }






}
