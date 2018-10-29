package com.jstech.fluenterp.sd;
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
import com.jstech.fluenterp.WebViewActivity;
import com.jstech.fluenterp.models.Customer;
import com.jstech.fluenterp.models.Material;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivitySalesOrderModify extends AppCompatActivity {

    Spinner spSON;
    ArrayAdapter<String> adapterSO;
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
    Button btnModifySalesOrder;
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
    String Customer_id = "";
    String[] material_code;
    String[] quantity;
    String[] price;
    String sales_doc_no = "";
    String date;
    EditText eTxtSDN;


    TextView txtViewMat1;
    TextView txtViewMat2;
    TextView txtViewMat3;
    TextView txtViewMat4;

    //PostVariables
    String m1,p1,q1;
    String m2,p2,q2;
    String m3,p3,q3;
    String m4,p4,q4;
    String passCN;
    String passSON;

    void setPostVariables(){

        if(!txtViewMat1.getText().toString().isEmpty()){
            m1 = txtViewMat1.getText().toString().substring(15,19);
        }
        else{
            m1 = "nil";
        }
        if(!txtViewMat2.getText().toString().isEmpty()){
            m2 = txtViewMat2.getText().toString().substring(15,19);
        }
        else{
            m2="nil";
        }
        if(!txtViewMat3.getText().toString().isEmpty()){
            m3 = txtViewMat3.getText().toString().substring(15,19);
        }
        else {
            m3 = "nil";
        }
        if(!txtViewMat4.getText().toString().isEmpty()){
            m4 = txtViewMat4.getText().toString().substring(15,19);
        }
        else{
            m4 = "nil";
        }
        if(txtViewCost1.getText().toString().isEmpty() || txtViewCost1.getText().toString().equals("0")){
            p1="nil";
        }
        else{
            p1 = txtViewCost1.getText().toString();
        }
        if(txtViewCost2.getText().toString().isEmpty() || txtViewCost2.getText().toString().equals("0")){
            p2="nil";
        }
        else{
            p2 = txtViewCost2.getText().toString();
        }
        if(txtViewCost3.getText().toString().isEmpty() || txtViewCost3.getText().toString().equals("0")){
            p3="nil";
        }
        else{
            p3 = txtViewCost3.getText().toString();
        }
        if(txtViewCost4.getText().toString().isEmpty() || txtViewCost4.getText().toString().equals("0")){
            p4="nil";
        }
        else{
            p4 = txtViewCost4.getText().toString();
        }
        if(!eTxtQuantity1.getText().toString().isEmpty()){
            q1 = eTxtQuantity1.getText().toString();
        }
        else{
            q1="nil";
        }
        if(!eTxtQuantity2.getText().toString().isEmpty()){
            q2 = eTxtQuantity2.getText().toString();
        }

        else{
            q2="nil";
        }
        if(!eTxtQuantity3.getText().toString().isEmpty()){
            q3 = eTxtQuantity3.getText().toString();
        }
        else{
            q3="nil";
        }
        if(!eTxtQuantity4.getText().toString().isEmpty()){
            q4 = eTxtQuantity4.getText().toString();
        }
        else{
            q4="nil";
        }
        passCN = eTxtCustomerName.getText().toString();
        sales_doc_no = passSON;

    }


    void init() {

        material_code = new String[4];
        quantity = new String[4];
        price = new String[4];
        customer = new Customer();
        eTxtCustomerName = findViewById(R.id.editTextCustomerNameForSOM);
        eTxtCustomerName.setEnabled(false);
        eTxtSDN = findViewById(R.id.editTextSONumber);
        eTxtSDN.setEnabled(false);
        txtViewMat1 = findViewById(R.id.textMatCode1);
        txtViewMat2 = findViewById(R.id.textMatCode2);
        txtViewMat3 = findViewById(R.id.textMatCode3);
        txtViewMat4 = findViewById(R.id.textMatCode4);
        txtViewCost1 = findViewById(R.id.textViewCost10SOM);
        txtViewCost2 = findViewById(R.id.textViewCost20SOM);
        txtViewCost3 = findViewById(R.id.textViewCost30SOM);
        txtViewCost4 = findViewById(R.id.textViewCost40SOM);
        txtViewTotalCost = findViewById(R.id.txtViewTotalCostSOM);
        eTxtQuantity1 = findViewById(R.id.quantity10SOM);
        eTxtQuantity2 = findViewById(R.id.quantity20SOM);
        eTxtQuantity3 = findViewById(R.id.quantity30SOM);
        eTxtQuantity4 = findViewById(R.id.quantity40SOM);
        btnItem1 = findViewById(R.id.addButton10SOM);
        btnItem2 = findViewById(R.id.addButton20SOM);
        btnItem3 = findViewById(R.id.addButton30SOM);
        btnItem4 = findViewById(R.id.addButton40SOM);
        btnModifySalesOrder = findViewById(R.id.btnUpdateSalesOrder);
        progressBar = findViewById(R.id.progressBarSOM);
        progressBar.setVisibility(View.VISIBLE);
        spinnerCustomer =  findViewById(R.id.spinnerCustomersSOM);
        spSON = findViewById(R.id.spinnerSalesOrderSOM);
        adapterSO = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("~~ Select a Customer to Modify ~~");
        adapterSO.add("~~ Select a Sales Document Number ~~");
        retrieveCustomers();
        spinnerCustomer.setAdapter(adapter);
        spSON.setAdapter(adapterSO);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                cust_number = adapter.getItem(pos);
                int ind;
                if (pos != 0) {
                    eTxtCustomerName.setText(cust_number);
                    ind = cust_number.indexOf("-") - 1;
                    Customer_id = eTxtCustomerName.getText().toString().substring(0, Math.min(eTxtCustomerName.getText().toString().length(), ind));
                    //sales_doc_no = date.substring(0, 12);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eTxtCustomerName.setHint("Customer");
            }
        });
    }
    void initSalesOrder() {
        material1 = new Material();
        material2 = new Material();
        material3 = new Material();
        material4 = new Material();
        spinnerMaterial1 = findViewById(R.id.spinnerMaterials10SOM);
        spinnerMaterial2 = findViewById(R.id.spinnerMaterials20SOM);
        spinnerMaterial3 = findViewById(R.id.spinnerMaterials30SOM);
        spinnerMaterial4 = findViewById(R.id.spinnerMaterials40SOM);
        adapterSpMat1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat1.add("-- Select an Item to modify --");
        adapterSpMat2.add("-- Select an Item to modify --");
        adapterSpMat3.add("-- Select an Item to modify --");
        adapterSpMat4.add("-- Select an Item to modify --");
        loadMaterials();
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_modify);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarSOM);
        setSupportActionBar(toolbar);
        setTitle("Modify Sales Order");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        requestQueue = Volley.newRequestQueue(this);
        dateShow = findViewById(R.id.dateShowSOM);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        dateShow.setText(date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4));
        init();
        initSalesOrder();
        loadSalesOrders();
        spSON.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    passSON = adapterSO.getItem(position);
                    eTxtSDN.setText("SDN - "+passSON);
                    fillOutSO(passSON);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnModifySalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPostVariables();
                updateSalesOrder();
            }
        });
    }


    void updateSalesOrder(){

        progressBar.setVisibility(View.VISIBLE);
        final String url = "https://jaspreettechnologies.000webhostapp.com/modifySalesDoc.php";
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if(success == 1){
                                stringRequest1 = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/modifySalesOrder.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            int success1 = jsonObject1.getInt("success");
                                            String message1 = jsonObject1.getString("message");
                                            if (success1 == 1) {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderModify.this);
                                                builder.setTitle("SUCCESS\n\n");
                                                builder.setMessage(message1);
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("Generate Bill Document", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        clearFields();
                                                        generatePDF(sales_doc_no);
                                                    }
                                                });
                                                builder.setNegativeButton("Return to Home", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        clearFields();
                                                        finish();
                                                    }
                                                });
                                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                    @Override
                                                    public void onDismiss(DialogInterface dialog) {
                                                        clearFields();
                                                        finish();
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                                                dialog.show();
                                                Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                                bNeg.setTextColor(getResources().getColor(R.color.splashback));
                                                Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                                bPos.setTextColor(getResources().getColor(R.color.splashback));

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Sales Order Modification Failed", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ActivitySalesOrderModify.this, "Some Internal Error: " + error, Toast.LENGTH_LONG).show();
                                        error.printStackTrace();
                                    }
                                }) {
                                    protected Map<String, String> getParams(){

                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("sales_doc_no", sales_doc_no);
                                        map.put("customer_id", passCN);
                                        map.put("material_code0", m1);
                                        map.put("quantity0", q1);
                                        map.put("price0", p1);
                                        map.put("material_code1", m2);
                                        map.put("quantity1", q2);
                                        map.put("price1", p2);
                                        map.put("material_code2", m3);
                                        map.put("quantity2", q3);
                                        map.put("price2", p3);
                                        map.put("material_code3", m4);
                                        map.put("quantity3", q4);
                                        map.put("price3", p4);
                                        return map;
                                    }
                                }
                                ;
                                requestQueue.add(stringRequest1);
                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Sales Order Document Modification Failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(ActivitySalesOrderModify.this, "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySalesOrderModify.this, "Some External Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("sales_doc_no", sales_doc_no);
                map1.put("customer_id", passCN);
                map1.put("date_of_order", date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                map1.put("price", String.valueOf(calculateCost()));
                return map1;
            }
        }
        ;
        requestQueue.add(stringRequest);

    }

    void clearFields() {
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
    void generatePDF(final String sdn) {
        String urlString="http://docs.google.com/gview?embedded=true&url=http://jaspreettechnologies.000webhostapp.com/createInvoice.php?sales_doc_no="+sdn;
        Intent intent=new Intent(ActivitySalesOrderModify.this, WebViewActivity.class);
        intent.putExtra("url", urlString);
        startActivity(intent);

    }

    void fillOutSO(final String strSON){

        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveSalesOrderWithDoc.php",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            int cid;
                            int mc;
                            int quantity;
                            double price;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("sales_order");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    cid = jObj.getInt("Customer_id");
                                    mc = jObj.getInt("material_code");
                                    quantity = jObj.getInt("quantity");
                                    price = jObj.getDouble("price");
                                    eTxtCustomerName.setText(String.valueOf(cid));
                                    if(i==0){
                                        txtViewMat1.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity1.setText(String.valueOf(quantity));
                                        txtViewCost1.setText(String.valueOf(price));
                                    }
                                    else if(i==1){
                                        txtViewMat2.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity2.setText(String.valueOf(quantity));
                                        txtViewCost2.setText(String.valueOf(price));
                                    }
                                    if(i==2){
                                        txtViewMat3.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity3.setText(String.valueOf(quantity));
                                        txtViewCost3.setText(String.valueOf(price));
                                    }
                                    if(i==3){
                                        txtViewMat4.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity4.setText(String.valueOf(quantity));
                                        txtViewCost4.setText(String.valueOf(price));
                                    }
                                }
                                txtViewTotalCost.setText("Rs. " + String.valueOf(calculateCost()) + "/-");
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams(){
                HashMap<String,String> map = new HashMap<>();
                map.put("sales_doc_no", strSON);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    void retrieveCustomers() {
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveU.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int c;
                            String n;
                            if (success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    c = jObj.getInt("Customer_id");
                                    n = jObj.getString("Name");

                                    adapter.add(Integer.toString(c) + " - " + n);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "None Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Volley Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    void loadMaterials() {

        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadMaterials.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int mc;
                            String mt;
                            String md;
                            String du;
                            double cost;
                            if (success == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                final Material[] matArray = new Material[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    mc = jObj.getInt("material_code");
                                    mt = jObj.getString("material_type");
                                    md = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    cost = jObj.getDouble("cost_per_du");
                                    material = new Material(mc, mt, md, du, cost);
                                    matArray[i] = material;
                                    adapterSpMat1.add(mt + Integer.toString(mc) + " - " + md);
                                    adapterSpMat2.add(mt + Integer.toString(mc) + " - " + md);
                                    adapterSpMat3.add(mt + Integer.toString(mc) + " - " + md);
                                    adapterSpMat4.add(mt + Integer.toString(mc) + " - " + md);
                                    spinnerMaterial1.setAdapter(adapterSpMat1);
                                    spinnerMaterial2.setAdapter(adapterSpMat2);
                                    spinnerMaterial3.setAdapter(adapterSpMat3);
                                    spinnerMaterial4.setAdapter(adapterSpMat4);

                                    progressBar.setVisibility(View.GONE);

                                    spinnerMaterial1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if (pos != 0) {
                                                material1 = matArray[pos - 1];
                                                txtViewMat1.setText("Material Code: "+Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(2, 6));
                                                material_code[0] = Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(2, 6);
                                            } else {
                                                material_code[0] = "None";
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem1.setOnClickListener(new View.OnClickListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onClick(View v) {
                                            if (TextUtils.isEmpty(eTxtQuantity1.getText().toString())) {
                                                eTxtQuantity1.setError("Enter Quantity!");
                                            } else {
                                                txtViewCost1.setText(String.valueOf(material1.getCostPerDu() * Integer.parseInt(eTxtQuantity1.getText().toString())));
                                                price[0] = txtViewCost1.getText().toString().trim();
                                                quantity[0] = eTxtQuantity1.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. " + String.valueOf(calculateCost()) + "/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if (pos != 0) {
                                                txtViewMat2.setText("Material Code: "+Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(2, 6));
                                                material2 = matArray[pos - 1];
                                                material_code[1] = Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(2, 6);
                                            } else {
                                                material_code[1] = "None";
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem2.setOnClickListener(new View.OnClickListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onClick(View v) {
                                            if (TextUtils.isEmpty(eTxtQuantity2.getText().toString())) {
                                                eTxtQuantity2.setError("Enter Quantity!");
                                            } else {
                                                txtViewCost2.setText(String.valueOf(truncateTo(material2.getCostPerDu() * Integer.parseInt(eTxtQuantity2.getText().toString()))));
                                                price[1] = txtViewCost2.getText().toString().trim();
                                                quantity[1] = eTxtQuantity2.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. " + String.valueOf(calculateCost()) + "/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                            if (pos != 0) {
                                                txtViewMat3.setText("Material Code: "+Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(2, 6));
                                                material3 = matArray[pos - 1];
                                                material_code[2] = Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(2, 6);
                                            } else  {
                                                material_code[2] = "None";
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem3.setOnClickListener(new View.OnClickListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onClick(View v) {
                                            if (TextUtils.isEmpty(eTxtQuantity3.getText().toString())) {
                                                eTxtQuantity3.setError("Enter Quantity!");
                                            } else {
                                                txtViewCost3.setText(String.valueOf(truncateTo(material3.getCostPerDu() * Integer.parseInt(eTxtQuantity3.getText().toString()))));
                                                price[2] = txtViewCost3.getText().toString().trim();
                                                quantity[2] = eTxtQuantity3.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. " + String.valueOf(calculateCost()) + "/-");
                                            }

                                        }
                                    });
                                    spinnerMaterial4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                            if (pos != 0) {
                                                txtViewMat4.setText("Material Code: "+Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(2, 6));
                                                material4 = matArray[pos - 1];
                                                material_code[3] = Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(2, 6);
                                            } else {
                                                material_code[3] = "None";
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
                                    btnItem4.setOnClickListener(new View.OnClickListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onClick(View v) {
                                            if (TextUtils.isEmpty(eTxtQuantity4.getText().toString())) {
                                                eTxtQuantity4.setError("Enter Quantity!");
                                            } else {
                                                txtViewCost4.setText(String.valueOf(truncateTo(material4.getCostPerDu() * Integer.parseInt(eTxtQuantity4.getText().toString()))));
                                                price[3] = txtViewCost4.getText().toString().trim();
                                                quantity[3] = eTxtQuantity4.getText().toString().trim();
                                                txtViewTotalCost.setText("Rs. " + String.valueOf(calculateCost()) + "/-");
                                            }

                                        }
                                    });
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "None Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Volley Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    double calculateCost() {
        return truncateTo(Double.parseDouble(txtViewCost1.getText().toString()) + Double.parseDouble(txtViewCost2.getText().toString()) + Double.parseDouble(txtViewCost3.getText().toString()) + Double.parseDouble(txtViewCost4.getText().toString()));
    }

    double truncateTo(double unroundedNumber) {
        int truncatedNumberInt = (int) (unroundedNumber * Math.pow(10, 2));
        return (truncatedNumberInt / Math.pow(10, 2));
    }

    void loadSalesOrders(){
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadSalesOrderNumbers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("sales_order");
                                long sdn;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sdn = jObj.getLong("sales_doc_no");
                                    adapterSO.add(String.valueOf(sdn));
                                }

                                progressBar.setVisibility(View.GONE);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "None Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Volley Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );
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
