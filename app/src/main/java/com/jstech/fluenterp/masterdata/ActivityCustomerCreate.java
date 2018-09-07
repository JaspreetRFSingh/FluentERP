package com.jstech.fluenterp.masterdata;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityCustomerCreate extends AppCompatActivity {

    EditText eTxtCustomerName;
    EditText eTxtCustomerAddress;
    EditText eTxtCustomerCity;
    EditText eTxtCustomerPhone;
    EditText eTxtCustomerGST;
    TextView txtDate;
    String date;
    Button btnCreateCustomer;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String custNumber;
    int tc;
    void initViews(){
        eTxtCustomerName = findViewById(R.id.CreateCustomerName);
        eTxtCustomerAddress = findViewById(R.id.CreateCustomerAddress);
        eTxtCustomerCity = findViewById(R.id.CreateCustomerCity);
        eTxtCustomerPhone = findViewById(R.id.CreateCustomerPhone);
        eTxtCustomerGST = findViewById(R.id.CreateCustomerGSTNumber);
        txtDate = findViewById(R.id.dateShowCC);
        btnCreateCustomer = findViewById(R.id.btnCreateCustomer);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        custNumber = "";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        tc = Integer.parseInt(date.substring(8,10))+Integer.parseInt(date.substring(10,12));
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCC);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        txtDate.setText(date.substring(6,8)+"/"+date.substring(4,6)+"/"+date.substring(0,4));
        progressBar.setVisibility(View.GONE);
        btnCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custNumber = date.substring(2,4)+00+date.substring(4,6)+date.substring(6,8)+tc;
                createCustomer();
            }
        });
    }

    void clearFields()
    {
        eTxtCustomerGST.setText("");
        eTxtCustomerName.setText("");
        eTxtCustomerPhone.setText("");
        eTxtCustomerCity.setText("");
        eTxtCustomerAddress.setText("");
    }

    boolean checkRecords(){
        boolean ch = true;
        if(TextUtils.isEmpty(eTxtCustomerName.getText().toString())){
            eTxtCustomerName.setError("Name is required!");
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
        if(TextUtils.isEmpty(eTxtCustomerPhone.getText().toString())){
            eTxtCustomerPhone.setError("Phone is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtCustomerGST.getText().toString())){
            eTxtCustomerGST.setError("GST Number is required!");
            ch = false;
        }
        return ch;
    }

    public void createCustomer(){

        if (checkRecords() == true){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/createCustomer.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                //int success = jsonObject.getInt("success");
                                //String message = jsonObject.getString("message");
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCustomerCreate.this);
                                builder.setTitle(eTxtCustomerName.getText().toString());
                                builder.setMessage("Successfully created "+eTxtCustomerName.getText().toString()+" with number: "+custNumber);
                                builder.setPositiveButton("Add Another Customer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityCustomerCreate.this, ActivityCustomerCreate.class));
                                    }
                                });
                                builder.setNegativeButton("View Customer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                                dialog.show();
                                clearFields();
                                //Toast.makeText(ActivityCustomerCreate.this,message,Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                Toast.makeText(ActivityCustomerCreate.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ActivityCustomerCreate.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
            )

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("Customer_id", custNumber);
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

        else{
            return;
        }
    }
}
