package com.jstech.fluenterp.sd;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.MainActivity;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.masterdata.ActivityCustomerDisplay;
import com.jstech.fluenterp.models.Customer;
import com.jstech.fluenterp.models.Material;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivitySalesOrderCreate extends AppCompatActivity {


    Spinner spinnerCustomer;
    ArrayAdapter<String> adapter;
    String cust_number = "";
    Customer customer;
    StringRequest stringRequest;
    StringRequest stringRequest1;
    RequestQueue requestQueue;
    EditText eTxtCustomerName;

    Spinner spinnerMaterial1;
    ArrayAdapter<String> adapterSpMat1;
    Material material1;
    Button btnItem1;
    Spinner spinnerMaterial2;
    ArrayAdapter<String> adapterSpMat2;
    Material material2;
    Button btnItem2;
    Spinner spinnerMaterial3;
    ArrayAdapter<String> adapterSpMat3;
    Material material3;
    Button btnItem3;
    Spinner spinnerMaterial4;
    ArrayAdapter<String> adapterSpMat4;
    Material material4;
    Button btnItem4;
    Material material;
    Button btnCreateSalesOrder;

    TextView txtViewCost1;
    TextView txtViewCost2;
    TextView txtViewCost3;
    TextView txtViewCost4;
    TextView txtViewTotalCost;
    EditText eTxtQuantity1;
    EditText eTxtQuantity2;
    EditText eTxtQuantity3;
    EditText eTxtQuantity4;

    TextView dateShow;

    ProgressBar progressBar;

    String Customer_id = "" ;
    String []material_code ;
    String []quantity ;
    String []price;
    String sales_doc_no = "";
    String date;

    void init() {

        material_code = new String[4];
        quantity = new String[4];
        price = new String[4];
        customer = new Customer();
        eTxtCustomerName = findViewById(R.id.editTextCustomerNameForSO);
        eTxtCustomerName.setEnabled(false);
        txtViewCost1 = findViewById(R.id.textViewCost10);
        txtViewCost2 = findViewById(R.id.textViewCost20);
        txtViewCost3 = findViewById(R.id.textViewCost30);
        txtViewCost4 = findViewById(R.id.textViewCost40);
        txtViewTotalCost = findViewById(R.id.txtViewTotalCost);
        eTxtQuantity1 = findViewById(R.id.quantity10);
        eTxtQuantity2 = findViewById(R.id.quantity20);
        eTxtQuantity3 = findViewById(R.id.quantity30);
        eTxtQuantity4 = findViewById(R.id.quantity40);
        btnItem1 = findViewById(R.id.addButton10);
        btnItem2 = findViewById(R.id.addButton20);
        btnItem3 = findViewById(R.id.addButton30);
        btnItem4 = findViewById(R.id.addButton40);
        btnCreateSalesOrder = findViewById(R.id.btnCreateSalesOrder);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        spinnerCustomer = (Spinner)findViewById(R.id.spinnerCustomers);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("-- Select a Customer --");
        retrieveCustomers();
        spinnerCustomer.setAdapter(adapter);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                cust_number = adapter.getItem(pos);
                if(pos!=0){
                    eTxtCustomerName.setText(cust_number);
                    Customer_id = eTxtCustomerName.getText().toString().substring(0,Math.min(eTxtCustomerName.getText().toString().length(),8));
                    sales_doc_no = date.substring(0,12);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eTxtCustomerName.setHint("Customer");
            }
        });
    }
    void initSalesOrder()
    {
        material1 = new Material();
        material2 = new Material();
        material3 = new Material();
        material4 = new Material();
        spinnerMaterial1 = (Spinner)findViewById(R.id.spinnerMaterials10);
        spinnerMaterial2 = (Spinner)findViewById(R.id.spinnerMaterials20);
        spinnerMaterial3 = (Spinner)findViewById(R.id.spinnerMaterials30);
        spinnerMaterial4 = (Spinner)findViewById(R.id.spinnerMaterials40);
        adapterSpMat1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat1.add("-- Select an Item --");
        adapterSpMat2.add("-- Select an Item --");
        adapterSpMat3.add("-- Select an Item --");
        adapterSpMat4.add("-- Select an Item --");
        loadMaterials();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_create);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }

        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCSO);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dateShow = findViewById(R.id.dateShow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        dateShow.setText(date.substring(6,8)+"/"+date.substring(4,6)+"/"+date.substring(0,4));
        init();
        initSalesOrder();
        btnCreateSalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createSalesOrder();
            }
        });
    }

    void retrieveCustomers(){
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveU.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int c = 0;
                            int p = 0;
                            String n="";
                            String a = "";
                            String ci = "";
                            String g="";
                            if(success == 1){
                                //                              customerList = new ArrayList<>();
//                                customerNumberList = new ArrayList<>();

                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    c = jObj.getInt("Customer_id");
                                   n = jObj.getString("Name");
                                    a = jObj.getString("Address");
                                    ci = jObj.getString("City");
                                    p = jObj.getInt("Contact");
                                    g = jObj.getString("GST_Number");

                                  Customer customer1 = new Customer(c,n,a,ci,p,g);
                                  //customerList.add(customer);
                                    // customerNameList.add(n);
                                    adapter.add(Integer.toString(c) +" - "+ n);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"None Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    void loadMaterials(){

        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadMaterials.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int mc = 0;
                            String mt="";
                            String md = "";
                            String du = "";
                            double cost = 0;
                            if(success == 1){

                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                final Material[] matArray = new Material[jsonArray.length()];
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    mc = jObj.getInt("material_code");
                                    mt = jObj.getString("material_type");
                                    md = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    cost = jObj.getDouble("cost_per_du");
                                    material = new Material(mc, mt, md, du, cost);
                                    matArray[i] = material;
                                    adapterSpMat1.add(mt+Integer.toString(mc) +" - "+md);
                                    adapterSpMat2.add(mt+Integer.toString(mc) +" - "+md);
                                    adapterSpMat3.add(mt+Integer.toString(mc) +" - "+md);
                                    adapterSpMat4.add(mt+Integer.toString(mc) +" - "+md);
                                    spinnerMaterial1.setAdapter(adapterSpMat1);
                                    spinnerMaterial2.setAdapter(adapterSpMat2);
                                    spinnerMaterial3.setAdapter(adapterSpMat3);
                                    spinnerMaterial4.setAdapter(adapterSpMat4);

                                    progressBar.setVisibility(View.GONE);

                                    spinnerMaterial1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if(pos!=0){
                                                material1 = matArray[pos-1];
                                                material_code[0] = adapterSpMat1.getItem(pos).substring(2,6);
                                            }
                                            else if (pos == 0)
                                            {
                                                material_code[0] = "None";
                                            }
                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(TextUtils.isEmpty(eTxtQuantity1.getText().toString()))
                                            {
                                                eTxtQuantity1.setError("Enter Quantity!");}
                                            else
                                            {
                                                txtViewCost1.setText(String.valueOf(material1.getCostPerDu()*Integer.parseInt(eTxtQuantity1.getText().toString())));
                                                price[0] = txtViewCost1.getText().toString().trim();
                                                quantity[0] = eTxtQuantity1.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. "+String.valueOf(calculateCost())+"/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if(pos!=0){
                                                material2 = matArray[pos-1];
                                                material_code[1] = adapterSpMat2.getItem(pos).substring(2,6);
                                            }
                                            else if (pos == 0)
                                            {
                                                material_code[1] = "None";
                                            }

                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(TextUtils.isEmpty(eTxtQuantity2.getText().toString()))
                                            {
                                                eTxtQuantity2.setError("Enter Quantity!");}
                                            else
                                            {
                                                txtViewCost2.setText(String.valueOf(truncateTo(material2.getCostPerDu()*Integer.parseInt(eTxtQuantity2.getText().toString()),2)));
                                                price[1] = txtViewCost2.getText().toString().trim();
                                                quantity[1] = eTxtQuantity2.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. "+String.valueOf(calculateCost())+"/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if(pos!=0){
                                                material3 = matArray[pos-1];
                                                material_code[2] = adapterSpMat3.getItem(pos).substring(2,6);
                                            }
                                            else if (pos == 0)
                                            {
                                                material_code[2] = "None";
                                            }

                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(TextUtils.isEmpty(eTxtQuantity3.getText().toString()))
                                            {
                                                eTxtQuantity3.setError("Enter Quantity!");
                                            }
                                            else
                                            {
                                                txtViewCost3.setText(String.valueOf(truncateTo(material3.getCostPerDu()*Integer.parseInt(eTxtQuantity3.getText().toString()),2)));
                                                price[2] = txtViewCost3.getText().toString().trim();
                                                quantity[2] = eTxtQuantity3.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. "+String.valueOf(calculateCost())+"/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                            if(pos!=0){
                                                material4 = matArray[pos-1];
                                                material_code[3] = adapterSpMat4.getItem(pos).substring(2,6);
                                            }
                                            else if (pos == 0)
                                            {
                                                material_code[3] = "None";
                                            }

                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(TextUtils.isEmpty(eTxtQuantity4.getText().toString()))
                                            {
                                                eTxtQuantity4.setError("Enter Quantity!");
                                            }
                                            else
                                            {
                                                txtViewCost4.setText(String.valueOf(truncateTo(material4.getCostPerDu()*Integer.parseInt(eTxtQuantity4.getText().toString()),2)));
                                                price[3] = txtViewCost4.getText().toString().trim();
                                                quantity[3] = eTxtQuantity4.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. "+String.valueOf(calculateCost())+"/-");
                                            }

                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"None Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    double calculateCost()
    {
        return truncateTo(Double.parseDouble(txtViewCost1.getText().toString())+Double.parseDouble(txtViewCost2.getText().toString())+Double.parseDouble(txtViewCost3.getText().toString())+Double.parseDouble(txtViewCost4.getText().toString()),2);
    }

    double truncateTo( double unroundedNumber, int decimalPlaces ){
        int truncatedNumberInt = (int)( unroundedNumber * Math.pow( 10, decimalPlaces ) );
        double truncatedNumber = (double)( truncatedNumberInt / Math.pow( 10, decimalPlaces ) );
        return truncatedNumber;
    }

    void createSalesOrder()
    {
        progressBar.setVisibility(View.VISIBLE);

        final String url = "https://jaspreettechnologies.000webhostapp.com/createSalesDoc.php";
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderCreate.this);
                            builder.setTitle(message);
                            builder.setMessage("Please click on 'Generate' to generate the Sales Order Document!");
                            builder.setPositiveButton("Generate", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stringRequest1 = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/createSalesOrder.php", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(response);
                                                int success1 = jsonObject.getInt("success");
                                                String message1 = jsonObject.getString("message");
                                                Toast.makeText(ActivitySalesOrderCreate.this,message1,Toast.LENGTH_LONG).show();

                                                if(success1 == 1)
                                                {
                                                    clearFields();
                                                    finish();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(ActivitySalesOrderCreate.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                                            error.printStackTrace();
                                        }
                                    })
                                    {
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            HashMap<String,String> map = new HashMap<String, String>();

                                            map.put("sales_doc_no", sales_doc_no);
                                            map.put("Customer_id",Customer_id);

                                            if(!eTxtQuantity1.getText().toString().isEmpty()){
                                            map.put("material_code0",material_code[0]);
                                            map.put("quantity0", quantity[0]);
                                            map.put("price0", price[0]);}

                                            if(!eTxtQuantity2.getText().toString().isEmpty()){
                                            map.put("material_code1",material_code[1]);
                                            map.put("quantity1", quantity[1]);
                                            map.put("price1", price[1]);}

                                            if(!eTxtQuantity3.getText().toString().isEmpty()){
                                            map.put("material_code2",material_code[2]);
                                            map.put("quantity2", quantity[2]);
                                            map.put("price2", price[2]);}

                                            if(!eTxtQuantity4.getText().toString().isEmpty()){
                                            map.put("material_code3",material_code[3]);
                                            map.put("quantity3", quantity[3]);
                                            map.put("price3", price[3]);}

                                            return map;

                                        }
                                    }
                                    ;
                                    requestQueue.add(stringRequest1);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                            dialog.show();
                            Toast.makeText(ActivitySalesOrderCreate.this,message,Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            Toast.makeText(ActivitySalesOrderCreate.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySalesOrderCreate.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map1 = new HashMap<String, String>();
                map1.put("sales_doc_no", sales_doc_no);
                map1.put("Customer",Customer_id);
                map1.put("date_of_order", date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8));
                map1.put("bill_price", String.valueOf(calculateCost()));
                return map1;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }
    void clearFields(){
        eTxtQuantity1.setText("");
        eTxtQuantity2.setText("");
        eTxtQuantity3.setText("");
        eTxtQuantity4.setText("");
        txtViewTotalCost.setText("0");
        txtViewCost1.setText("");
        txtViewCost2.setText("");
        txtViewCost3.setText("");
        txtViewCost4.setText("");
        eTxtCustomerName.setText("");
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            if(!eTxtCustomerName.getText().toString().trim().isEmpty())
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderCreate.this);
                builder.setTitle("BACK!\n\n");
                builder.setMessage("Your bill is currently under progress. Are you sure you want to go back?");
                builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
                    }
                });
                /*builder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });*/
                AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();
            }else
            {
                startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!eTxtCustomerName.getText().toString().trim().isEmpty())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderCreate.this);
            builder.setTitle("BACK!\n\n");
            builder.setMessage("Your bill is currently under progress. Are you sure you want to go back?");
            builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
                }
            });
                /*builder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });*/
            AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.show();
        }else
        {
            startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
        }
    }
}