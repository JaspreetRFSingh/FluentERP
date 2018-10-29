package com.jstech.fluenterp.purchasing;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.jstech.fluenterp.models.Material;
import com.jstech.fluenterp.models.Seller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityPurchaseOrderModify extends AppCompatActivity {


    Spinner spPON;
    ArrayAdapter<String> adapterPO;
    Spinner spinnerSeller;
    ArrayAdapter<String> adapter;
    String seller_number = "";
    Seller seller;
    StringRequest stringRequest;
    StringRequest stringRequest1;
    RequestQueue requestQueue;
    EditText eTxtSellerName;
    Spinner spinnerMaterial1;
    ArrayAdapter<String> adapterSpMat1;
    Material material1;
    Spinner spinnerMaterial2;
    ArrayAdapter<String> adapterSpMat2;
    Material material2;
    Spinner spinnerMaterial3;
    ArrayAdapter<String> adapterSpMat3;
    Material material3;
    Spinner spinnerMaterial4;
    ArrayAdapter<String> adapterSpMat4;
    Material material4;
    Material material;
    Button btnModifyPurchaseOrder;
    EditText eTxtQuantity1;
    EditText eTxtQuantity2;
    EditText eTxtQuantity3;
    EditText eTxtQuantity4;
    TextView dateShow;
    ProgressBar progressBar;
    String[] material_code;
    String[] quantity;
    String purchase_doc_no = "";
    String date;
    EditText eTxtPDN;
    TextView txtViewMat1;
    TextView txtViewMat2;
    TextView txtViewMat3;
    TextView txtViewMat4;
    String seller_id = "";

    //PostVariables
    String m1,q1;
    String m2,q2;
    String m3,q3;
    String m4,q4;
    String passSN;
    String passPON;

    void init() {
        material_code = new String[4];
        quantity = new String[4];
        seller = new Seller();
        eTxtSellerName = findViewById(R.id.editTextSellerNameForPOM);
        eTxtSellerName.setEnabled(false);
        eTxtPDN = findViewById(R.id.editTextPONumber);
        eTxtPDN.setEnabled(false);
        txtViewMat1 = findViewById(R.id.textMatCode1);
        txtViewMat2 = findViewById(R.id.textMatCode2);
        txtViewMat3 = findViewById(R.id.textMatCode3);
        txtViewMat4 = findViewById(R.id.textMatCode4);
        eTxtQuantity1 = findViewById(R.id.quantity10POM);
        eTxtQuantity2 = findViewById(R.id.quantity20POM);
        eTxtQuantity3 = findViewById(R.id.quantity30POM);
        eTxtQuantity4 = findViewById(R.id.quantity40POM);
        btnModifyPurchaseOrder = findViewById(R.id.btnUpdatePurchaseOrder);
        progressBar = findViewById(R.id.progressBarMPO);
        progressBar.setVisibility(View.VISIBLE);
        spinnerSeller =  findViewById(R.id.spinnerSellersPOM);
        spPON = findViewById(R.id.spinnerMPO);
        adapterPO = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("~~ Select a Seller to Modify ~~");
        adapterPO.add("~~ Select a Purchase Document Number ~~");
        retrieveSellers();
        spinnerSeller.setAdapter(adapter);
        spPON.setAdapter(adapterPO);
        spinnerSeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                seller_number = adapter.getItem(pos);
                int ind;
                if (pos != 0) {
                    eTxtSellerName.setText(seller_number);
                    ind = seller_number.indexOf("-") - 1;
                     seller_id = eTxtSellerName.getText().toString().substring(0, Math.min(eTxtSellerName.getText().toString().length(), ind));
                    //sales_doc_no = date.substring(0, 12);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eTxtSellerName.setHint("Seller");
            }
        });
    }
    void initPurchaseOrder() {
        material1 = new Material();
        material2 = new Material();
        material3 = new Material();
        material4 = new Material();
        spinnerMaterial1 = findViewById(R.id.spinnerMaterials10POM);
        spinnerMaterial2 = findViewById(R.id.spinnerMaterials20POM);
        spinnerMaterial3 = findViewById(R.id.spinnerMaterials30POM);
        spinnerMaterial4 = findViewById(R.id.spinnerMaterials40POM);
        adapterSpMat1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        spinnerMaterial1.setAdapter(adapterSpMat1);
        spinnerMaterial2.setAdapter(adapterSpMat2);
        spinnerMaterial3.setAdapter(adapterSpMat3);
        spinnerMaterial4.setAdapter(adapterSpMat4);
        adapterSpMat1.add("-- Select an Item to modify --");
        adapterSpMat2.add("-- Select an Item to modify --");
        adapterSpMat3.add("-- Select an Item to modify --");
        adapterSpMat4.add("-- Select an Item to modify --");
        loadUGMaterials();
    }

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
        passSN = eTxtSellerName.getText().toString();
        purchase_doc_no = passPON;

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_modify);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar =  findViewById(R.id.toolbarMPO);
        setSupportActionBar(toolbar);
        setTitle("Modify Purchase Order");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        requestQueue = Volley.newRequestQueue(this);
        dateShow = findViewById(R.id.dateShowMPO);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        dateShow.setText(date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4));
        init();
        initPurchaseOrder();
        loadPurchaseOrders();
        spPON.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    passPON = adapterPO.getItem(position);
                    eTxtPDN.setText("PDN - "+passPON);
                    fillOutPO(passPON);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnModifyPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPostVariables();
                updatePurchaseOrder();
            }
        });
    }


    void fillOutPO(final String pon){
        txtViewMat1.setText("");
        txtViewMat2.setText("");
        txtViewMat3.setText("");
        txtViewMat4.setText("");
        eTxtQuantity1.setText("");
        eTxtQuantity2.setText("");
        eTxtQuantity3.setText("");
        eTxtQuantity4.setText("");
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrievePurchaseOrderWithPurchaseDocument.php",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            int sid;
                            int mc;
                            int quantity;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("purchase_order");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sid = jObj.getInt("s_id");
                                    mc = jObj.getInt("material_code");
                                    quantity = jObj.getInt("quantity");
                                    eTxtSellerName.setText(String.valueOf(sid));
                                    if(i==0){
                                        txtViewMat1.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity1.setText(String.valueOf(quantity));
                                    }
                                    else if(i==1){
                                        txtViewMat2.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity2.setText(String.valueOf(quantity));
                                    }
                                    if(i==2){
                                        txtViewMat3.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity3.setText(String.valueOf(quantity));
                                    }
                                    if(i==3){
                                        txtViewMat4.setText("Material Code: "+String.valueOf(mc));
                                        eTxtQuantity4.setText(String.valueOf(quantity));
                                    }
                                }
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
                map.put("purchase_doc_no", pon);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    void updatePurchaseOrder(){
        progressBar.setVisibility(View.VISIBLE);
        final String url = "https://jaspreettechnologies.000webhostapp.com/modifyPurchaseDoc.php";
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if(success == 1){
                                stringRequest1 = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/modifyPurchaseOrder.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            int success1 = jsonObject1.getInt("success");
                                            String message1 = jsonObject1.getString("message");
                                            if (success1 == 1) {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPurchaseOrderModify.this);
                                                builder.setTitle("SUCCESS\n\n");
                                                builder.setMessage(message1);
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("Modify Another Order", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        clearFields();
                                                        startActivity(new Intent(ActivityPurchaseOrderModify.this, ActivityPurchaseOrderModify.class));
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
                                                Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                                //noinspection deprecation
                                                bPos.setTextColor(getResources().getColor(R.color.splashback));
                                                Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                                //noinspection deprecation
                                                bNeg.setTextColor(getResources().getColor(R.color.splashback));

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Purchase Order Modification Failed", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ActivityPurchaseOrderModify.this, "Some Internal Error: " + error, Toast.LENGTH_LONG).show();
                                        error.printStackTrace();
                                    }
                                }) {
                                    protected Map<String, String> getParams(){

                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("purchase_doc_no", purchase_doc_no);
                                        map.put("seller_id", passSN);
                                        map.put("material_code0", m1);
                                        map.put("quantity0", q1);
                                        map.put("material_code1", m2);
                                        map.put("quantity1", q2);
                                        map.put("material_code2", m3);
                                        map.put("quantity2", q3);
                                        map.put("material_code3", m4);
                                        map.put("quantity3", q4);
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
                            Toast.makeText(ActivityPurchaseOrderModify.this, "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityPurchaseOrderModify.this, "Some External Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("purchase_doc_no", purchase_doc_no);
                map1.put("seller_id", passSN);
                map1.put("date_of_order", date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                return map1;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    void clearFields(){
        txtViewMat1.setText("");
        txtViewMat2.setText("");
        txtViewMat3.setText("");
        txtViewMat4.setText("");
        eTxtSellerName.setText("");
        eTxtQuantity1.setText("");
        eTxtQuantity2.setText("");
        eTxtQuantity3.setText("");
        eTxtQuantity4.setText("");
        eTxtPDN.setText("");
        }

    void loadPurchaseOrders(){
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadPurchaseOrderNumbers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("purchase_order");
                                long sdn;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sdn = jObj.getLong("purchase_doc_no");
                                    adapterPO.add(String.valueOf(sdn));
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

    void loadUGMaterials(){
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadUGMaterials.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int mc;
                            String md;
                            String du;
                            double cost;
                            if (success == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                final Material[] matArray = new Material[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    mc = jObj.getInt("material_code");
                                    md = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    cost = jObj.getDouble("cost_per_du");
                                    material = new Material(mc, "UG", md, du, cost);
                                    matArray[i] = material;
                                    adapterSpMat1.add(Integer.toString(mc) + " - " + md);
                                    adapterSpMat2.add(Integer.toString(mc) + " - " + md);
                                    adapterSpMat3.add(Integer.toString(mc) + " - " + md);
                                    adapterSpMat4.add(Integer.toString(mc) + " - " + md);
                                }
                                progressBar.setVisibility(View.GONE);
                                spinnerMaterial1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material1 = matArray[pos - 1];
                                            material_code[0] = Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(0, 4);
                                            txtViewMat1.setText("Material Code: " + Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(0,4));
                                        } else {
                                            material_code[0] = "None";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material2 = matArray[pos - 1];
                                            material_code[1] = Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(0, 4);
                                            txtViewMat2.setText("Material Code: " + Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(0,4));
                                        } else {
                                            material_code[1] = "None";
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material3 = matArray[pos - 1];
                                            material_code[2] = Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(0, 4);
                                            txtViewMat3.setText("Material Code: " + Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(0,4));
                                        } else {
                                            material_code[2] = "None";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material4 = matArray[pos - 1];
                                            material_code[3] = Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(0, 4);
                                            txtViewMat4.setText("Material Code: " + Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(0,4));
                                        } else {
                                            material_code[3] = "None";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
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

    void retrieveSellers(){
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
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("sellers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sid = jObj.getLong("s_id");
                                    n = jObj.getString("s_name");
                                    adapter.add(String.valueOf(sid)+" - "+n);
                                }
                                progressBar.setVisibility(View.GONE);
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
