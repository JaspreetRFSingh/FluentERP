package com.jstech.fluenterp.sd;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.MainActivity;
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

public class ActivitySalesOrderCreate extends BaseActivity {


    Spinner spinnerCustomer;
    ArrayAdapter<String> adapter;
    String cust_number = "";
    Customer customer;
    StringRequest stringRequest;
    StringRequest stringRequest1;
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

    String Customer_id = "";
    String[] material_code;
    String[] quantity;
    String[] price;
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
        spinnerCustomer = findViewById(R.id.spinnerCustomers);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("~~ Select a Customer ~~");
        retrieveCustomers();
        spinnerCustomer.setAdapter(adapter);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                cust_number = adapter.getItem(pos);
                int ind;
                if (pos != 0) {
                    eTxtCustomerName.setText(cust_number);
                    ind = cust_number.indexOf("-") - 1;
                    Customer_id = eTxtCustomerName.getText().toString().substring(0, Math.min(eTxtCustomerName.getText().toString().length(), ind));
                    sales_doc_no = date.substring(0, 12);
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
        spinnerMaterial1 = findViewById(R.id.spinnerMaterials10);
        spinnerMaterial2 = findViewById(R.id.spinnerMaterials20);
        spinnerMaterial3 = findViewById(R.id.spinnerMaterials30);
        spinnerMaterial4 = findViewById(R.id.spinnerMaterials40);
        adapterSpMat1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpMat1.add("-- Select an Item --");
        adapterSpMat2.add("-- Select an Item --");
        adapterSpMat3.add("-- Select an Item --");
        adapterSpMat4.add("-- Select an Item --");
        loadMaterials();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_create);

        setupToolbar(R.id.toolbarCSO, getString(R.string.app_name));
        dateShow = findViewById(R.id.dateShow);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        dateShow.setText(date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4));
        init();
        initSalesOrder();
        btnCreateSalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createSalesOrder();
            }
        });
    }

    void retrieveCustomers() {
        stringRequest = new StringRequest(Request.Method.GET, Constants.URL_RETRIEVE_CUSTOMERS,
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void loadMaterials() {
        stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_LOAD_MATERIALS,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("materials");
                            final Material[] matArray = new Material[jsonArray.length()];

                            // 1. Build matArray and populate all four spinner adapters in one pass.
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = jsonArray.getJSONObject(i);
                                int mc = jObj.getInt("material_code");
                                String mt = jObj.getString("material_type");
                                String md = jObj.getString("material_description");
                                String du = jObj.getString("dimensional_unit");
                                double cost = jObj.getDouble("cost_per_du");
                                matArray[i] = new Material(mc, mt, md, du, cost);
                                String label = mt + mc + " - " + md;
                                adapterSpMat1.add(label);
                                adapterSpMat2.add(label);
                                adapterSpMat3.add(label);
                                adapterSpMat4.add(label);
                            }

                            // 2. Attach adapters once, outside the loop.
                            spinnerMaterial1.setAdapter(adapterSpMat1);
                            spinnerMaterial2.setAdapter(adapterSpMat2);
                            spinnerMaterial3.setAdapter(adapterSpMat3);
                            spinnerMaterial4.setAdapter(adapterSpMat4);

                            // 3. Register all listeners once, outside the loop.
                            spinnerMaterial1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> av, View v, int pos, long l) {
                                    if (pos != 0) {
                                        material1 = matArray[pos - 1];
                                        material_code[0] = Objects.requireNonNull(adapterSpMat1.getItem(pos)).substring(2, 6);
                                    } else {
                                        material_code[0] = "None";
                                    }
                                }
                                @Override public void onNothingSelected(AdapterView<?> av) {}
                            });
                            spinnerMaterial2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> av, View v, int pos, long l) {
                                    if (pos != 0) {
                                        material2 = matArray[pos - 1];
                                        material_code[1] = Objects.requireNonNull(adapterSpMat2.getItem(pos)).substring(2, 6);
                                    } else {
                                        material_code[1] = "None";
                                    }
                                }
                                @Override public void onNothingSelected(AdapterView<?> av) {}
                            });
                            spinnerMaterial3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> av, View v, int pos, long l) {
                                    if (pos != 0) {
                                        material3 = matArray[pos - 1];
                                        material_code[2] = Objects.requireNonNull(adapterSpMat3.getItem(pos)).substring(2, 6);
                                    } else {
                                        material_code[2] = "None";
                                    }
                                }
                                @Override public void onNothingSelected(AdapterView<?> av) {}
                            });
                            spinnerMaterial4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> av, View v, int pos, long l) {
                                    if (pos != 0) {
                                        material4 = matArray[pos - 1];
                                        material_code[3] = Objects.requireNonNull(adapterSpMat4.getItem(pos)).substring(2, 6);
                                    } else {
                                        material_code[3] = "None";
                                    }
                                }
                                @Override public void onNothingSelected(AdapterView<?> av) {}
                            });

                            btnItem1.setOnClickListener(v -> {
                                if (TextUtils.isEmpty(eTxtQuantity1.getText())) {
                                    eTxtQuantity1.setError("Enter Quantity!");
                                } else {
                                    txtViewCost1.setText(String.valueOf(truncateTo(
                                            material1.getCostPerDu() * Integer.parseInt(eTxtQuantity1.getText().toString()))));
                                    price[0] = txtViewCost1.getText().toString().trim();
                                    quantity[0] = eTxtQuantity1.getText().toString().trim();
                                    txtViewTotalCost.setText("Rs. " + calculateCost() + "/-");
                                }
                            });
                            btnItem2.setOnClickListener(v -> {
                                if (TextUtils.isEmpty(eTxtQuantity2.getText())) {
                                    eTxtQuantity2.setError("Enter Quantity!");
                                } else {
                                    txtViewCost2.setText(String.valueOf(truncateTo(
                                            material2.getCostPerDu() * Integer.parseInt(eTxtQuantity2.getText().toString()))));
                                    price[1] = txtViewCost2.getText().toString().trim();
                                    quantity[1] = eTxtQuantity2.getText().toString().trim();
                                    txtViewTotalCost.setText("Rs. " + calculateCost() + "/-");
                                }
                            });
                            btnItem3.setOnClickListener(v -> {
                                if (TextUtils.isEmpty(eTxtQuantity3.getText())) {
                                    eTxtQuantity3.setError("Enter Quantity!");
                                } else {
                                    txtViewCost3.setText(String.valueOf(truncateTo(
                                            material3.getCostPerDu() * Integer.parseInt(eTxtQuantity3.getText().toString()))));
                                    price[2] = txtViewCost3.getText().toString().trim();
                                    quantity[2] = eTxtQuantity3.getText().toString().trim();
                                    txtViewTotalCost.setText("Rs. " + calculateCost() + "/-");
                                }
                            });
                            btnItem4.setOnClickListener(v -> {
                                if (TextUtils.isEmpty(eTxtQuantity4.getText())) {
                                    eTxtQuantity4.setError("Enter Quantity!");
                                } else {
                                    txtViewCost4.setText(String.valueOf(truncateTo(
                                            material4.getCostPerDu() * Integer.parseInt(eTxtQuantity4.getText().toString()))));
                                    price[3] = txtViewCost4.getText().toString().trim();
                                    quantity[3] = eTxtQuantity4.getText().toString().trim();
                                    txtViewTotalCost.setText("Rs. " + calculateCost() + "/-");
                                }
                            });

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
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Volley Error: " + error, Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    double calculateCost() {
        return truncateTo(parseCost(txtViewCost1) + parseCost(txtViewCost2)
                + parseCost(txtViewCost3) + parseCost(txtViewCost4));
    }

    private double parseCost(TextView tv) {
        String s = tv.getText().toString().trim();
        return s.isEmpty() ? 0.0 : Double.parseDouble(s);
    }

    double truncateTo(double unroundedNumber) {
        int truncatedNumberInt = (int) (unroundedNumber * Math.pow(10, 2));
        return truncatedNumberInt / Math.pow(10, 2);
    }

    void createSalesOrder() {
        progressBar.setVisibility(View.VISIBLE);
        final String url = Constants.URL_CREATE_SALES_DOC;
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            stringRequest1 = new StringRequest(Request.Method.POST, Constants.URL_CREATE_SALES_ORDER, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject jsonObject;
                                    try {
                                        jsonObject = new JSONObject(response);
                                        int success1 = jsonObject.getInt("success");
                                        String message1 = jsonObject.getString("message");

                                        if (success1 == 1) {
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderCreate.this);
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
                                            bNeg.setTextColor(ContextCompat.getColor(this, R.color.splashback));
                                            Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                            bPos.setTextColor(ContextCompat.getColor(this, R.color.splashback));

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ActivitySalesOrderCreate.this, "Some Error: " + error, Toast.LENGTH_LONG).show();
                                    error.printStackTrace();
                                }
                            }) {
                                protected Map<String, String> getParams() {

                                    HashMap<String, String> map = new HashMap<>();

                                    map.put("sales_doc_no", sales_doc_no);
                                    map.put("Customer_id", Customer_id);

                                    if (!eTxtQuantity1.getText().toString().isEmpty()) {
                                        map.put("material_code0", material_code[0]);
                                        map.put("quantity0", quantity[0]);
                                        map.put("price0", price[0]);
                                    }

                                    if (!eTxtQuantity2.getText().toString().isEmpty()) {
                                        map.put("material_code1", material_code[1]);
                                        map.put("quantity1", quantity[1]);
                                        map.put("price1", price[1]);
                                    }

                                    if (!eTxtQuantity3.getText().toString().isEmpty()) {
                                        map.put("material_code2", material_code[2]);
                                        map.put("quantity2", quantity[2]);
                                        map.put("price2", price[2]);
                                    }

                                    if (!eTxtQuantity4.getText().toString().isEmpty()) {
                                        map.put("material_code3", material_code[3]);
                                        map.put("quantity3", quantity[3]);
                                        map.put("price3", price[3]);
                                    }

                                    return map;

                                }
                            }
                            ;
                            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest1);
                            progressBar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Toast.makeText(ActivitySalesOrderCreate.this, "Some Exception: " + e, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySalesOrderCreate.this, "Some Error: " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("sales_doc_no", sales_doc_no);
                map1.put("Customer", Customer_id);
                map1.put("date_of_order", date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                map1.put("bill_price", String.valueOf(calculateCost()));
                return map1;
            }
        }
        ;
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void clearFields() {
        eTxtQuantity1.setText("");
        eTxtQuantity2.setText("");
        eTxtQuantity3.setText("");
        eTxtQuantity4.setText("");
        txtViewCost1.setText("0");
        txtViewCost2.setText("0");
        txtViewCost3.setText("0");
        txtViewCost4.setText("0");
        txtViewTotalCost.setText("0");
        eTxtCustomerName.setText("");
    }
    void generatePDF(final String sdn) {
        String urlString=Constants.GDOCS_VIEWER_PREFIX + Constants.URL_CREATE_INVOICE + "?sales_doc_no="+sdn;
        Intent intent=new Intent(ActivitySalesOrderCreate.this, WebViewActivity.class);
        intent.putExtra("url", urlString);
        startActivity(intent);

    }






    @Override
    public void onBackPressed() {
        if (!eTxtCustomerName.getText().toString().trim().isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySalesOrderCreate.this);
            builder.setTitle("BACK!\n\n");
            builder.setMessage("Your bill is currently under progress. Are you sure you want to go back?");
            builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
                }
            });
            builder.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            AlertDialog dialog = builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.show();
            Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            bNeg.setTextColor(ContextCompat.getColor(this, R.color.splashback));
            Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            bPos.setTextColor(ContextCompat.getColor(this, R.color.splashback));
        } else {
            startActivity(new Intent(ActivitySalesOrderCreate.this, MainActivity.class));
        }
    }
}
