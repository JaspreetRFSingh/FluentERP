package com.jstech.fluenterp.purchasing;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityPurchaseOrderCreate extends AppCompatActivity {


    Spinner spinnerSeller;
    ArrayAdapter<String> adapterSellers;
    String seller_number = "";
    StringRequest stringRequest;
    StringRequest stringRequest1;
    RequestQueue requestQueue;
    EditText eTxtSellerName;

    Material material1;
    Material material2;
    Material material3;
    Material material4;
    Spinner spinnerMaterial1;
    ArrayAdapter<String> adapterSpMat1;
    Spinner spinnerMaterial2;
    ArrayAdapter<String> adapterSpMat2;
    Spinner spinnerMaterial3;
    ArrayAdapter<String> adapterSpMat3;
    Spinner spinnerMaterial4;
    ArrayAdapter<String> adapterSpMat4;
    Material material;
    Button btnCreatePurchaseOrder;
    EditText eTxtQuantity1;
    EditText eTxtQuantity2;
    EditText eTxtQuantity3;
    EditText eTxtQuantity4;
    TextView dateShow;
    ProgressBar progressBar;
    String seller_id = "";
    String[] material_code;
    String[] quantity;
    String purchase_doc_no = "";
    String date;

    @SuppressLint("SetTextI18n")
    void initViews(){
        material1 = new Material();
        material2 = new Material();
        material3 = new Material();
        material4 = new Material();
        requestQueue = Volley.newRequestQueue(this);
        material_code = new String[4];
        quantity = new String[4];
        material = new Material();
        eTxtSellerName = findViewById(R.id.editTextSellerNameForPO);
        eTxtSellerName.setEnabled(false);
        dateShow = findViewById(R.id.dateShowCPO);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        dateShow.setText(date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4));
        eTxtQuantity1 = findViewById(R.id.quantityCPO1);
        eTxtQuantity2 = findViewById(R.id.quantityCPO2);
        eTxtQuantity3 = findViewById(R.id.quantityCPO3);
        eTxtQuantity4 = findViewById(R.id.quantityCPO4);
        btnCreatePurchaseOrder = findViewById(R.id.btnCreatePurchaseOrder);
        progressBar = findViewById(R.id.progressBarCPO);
        progressBar.setVisibility(View.VISIBLE);
        spinnerSeller = findViewById(R.id.spinnerSellers);
        adapterSellers = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSellers.add("~~ Select a Seller ~~");
        retrieveSellers();
        spinnerSeller.setAdapter(adapterSellers);
        spinnerSeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                seller_number = adapterSellers.getItem(pos);
                int ind;
                if (pos != 0) {
                    eTxtSellerName.setText(seller_number);
                    ind = seller_number.indexOf("-") - 1;
                    seller_id = eTxtSellerName.getText().toString().substring(0, Math.min(eTxtSellerName.getText().toString().length(), ind));
                    purchase_doc_no = date.substring(2, 12);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                eTxtSellerName.setHint("Seller");
            }
        });
    }

    void retrieveSellers(){

        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveSellers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int sd;
                            String sn;
                            if (success == 1) {
                                JSONArray jsonArray = jsonObject.getJSONArray("sellers");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sd = jObj.getInt("s_id");
                                    sn = jObj.getString("s_name");
                                    adapterSellers.add(Integer.toString(sd) + " - " + sn);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_create);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarCPO);
        setSupportActionBar(toolbar);
        setTitle("Create Purchase Order");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        initSalesOrder();


        btnCreatePurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0] = eTxtQuantity1.getText().toString();
                quantity[1] = eTxtQuantity2.getText().toString();
                quantity[2] = eTxtQuantity3.getText().toString();
                quantity[3] = eTxtQuantity4.getText().toString();
                createPurchaseOrder();
            }
        });
    }
    void initSalesOrder() {
        spinnerMaterial1 = findViewById(R.id.spinnerMaterialsCPO1);
        spinnerMaterial2 = findViewById(R.id.spinnerMaterialsCPO2);
        spinnerMaterial3 = findViewById(R.id.spinnerMaterialsCPO3);
        spinnerMaterial4 = findViewById(R.id.spinnerMaterialsCPO4);
        adapterSpMat1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat1.add("-- Select an Item --");
        adapterSpMat2.add("-- Select an Item --");
        adapterSpMat3.add("-- Select an Item --");
        adapterSpMat4.add("-- Select an Item --");
        spinnerMaterial1.setAdapter(adapterSpMat1);
        spinnerMaterial2.setAdapter(adapterSpMat2);
        spinnerMaterial3.setAdapter(adapterSpMat3);
        spinnerMaterial4.setAdapter(adapterSpMat4);
        loadMaterials();
    }

    void loadMaterials() {
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
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material1 = matArray[pos - 1];
                                            material_code[0] = Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(0, 4);
                                        } else {
                                            material_code[0] = "None";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material2 = matArray[pos - 1];
                                            material_code[1] = Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(0, 4);
                                        } else {
                                            material_code[1] = "None";
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material3 = matArray[pos - 1];
                                            material_code[2] = Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(0, 4);
                                        } else {
                                            material_code[2] = "None";
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                                spinnerMaterial4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                                        if (pos != 0) {
                                            material4 = matArray[pos - 1];
                                            material_code[3] = Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(0, 4);
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

    void createPurchaseOrder(){
        progressBar.setVisibility(View.VISIBLE);
        final String url = "https://jaspreettechnologies.000webhostapp.com/createPurchaseDoc.php";
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            stringRequest1 = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/createPurchaseOrder.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int success1 = jsonObject.getInt("success");
                                        String message1 = jsonObject.getString("message");
                                        if (success1 == 1) {
                                            progressBar.setVisibility(View.GONE);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPurchaseOrderCreate.this);
                                            builder.setTitle("SUCCESS\n\n");
                                            builder.setMessage(message1);
                                            builder.setCancelable(false);
                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    clearFields();
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

                                    } catch (JSONException e) {
                                        progressBar.setVisibility(View.GONE);
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(ActivityPurchaseOrderCreate.this, "Some Error in OrderP: " + error, Toast.LENGTH_LONG).show();
                                    error.printStackTrace();
                                }
                            }) {
                                protected Map<String, String> getParams() {

                                    HashMap<String, String> map = new HashMap<>();

                                    map.put("purchase_doc_no", purchase_doc_no);
                                    map.put("seller_id", seller_id);

                                    if (!eTxtQuantity1.getText().toString().isEmpty()) {
                                        map.put("material_code0", material_code[0]);
                                        map.put("quantity0", quantity[0]);
                                    }

                                    if (!eTxtQuantity2.getText().toString().isEmpty()) {
                                        map.put("material_code1", material_code[1]);
                                        map.put("quantity1", quantity[1]);
                                    }

                                    if (!eTxtQuantity3.getText().toString().isEmpty()) {
                                        map.put("material_code2", material_code[2]);
                                        map.put("quantity2", quantity[2]);
                                    }

                                    if (!eTxtQuantity4.getText().toString().isEmpty()) {
                                        map.put("material_code3", material_code[3]);
                                        map.put("quantity3", quantity[3]);
                                    }
                                    return map;
                                }
                            }
                            ;
                            requestQueue.add(stringRequest1);

                        } catch (Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityPurchaseOrderCreate.this, "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ActivityPurchaseOrderCreate.this, "Some Error in DocP: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("purchase_doc_no", purchase_doc_no);
                map1.put("seller_id", seller_id);
                map1.put("date_of_order", date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
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
        eTxtSellerName.setText("");
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