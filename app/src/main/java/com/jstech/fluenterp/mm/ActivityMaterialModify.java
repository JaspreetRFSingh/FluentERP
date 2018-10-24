package com.jstech.fluenterp.mm;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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

public class ActivityMaterialModify extends AppCompatActivity {
    String date;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    TextView txtDateShow;
    Spinner spinnerMaterial;
    ArrayAdapter<String> adapterSpinnerMaterial;
    String chStr;
    String typeStr;
    EditText eTxtDescription;
    EditText eTxtDimensionalUnit;
    EditText costPerDimensionalUnit;
    RadioGroup rgMaterialType;
    Button btnModifyMaterial;

    void initViews(){
        txtDateShow = findViewById(R.id.dateShowMatModify);
        progressBar = findViewById(R.id.progressBarMM);
        progressBar.setVisibility(View.GONE);
        eTxtDescription = findViewById(R.id.ModifyMaterialDescription);
        eTxtDimensionalUnit = findViewById(R.id.ModifyMaterialDimensional);
        costPerDimensionalUnit = findViewById(R.id.ModifyMaterialCostPerDu);
        rgMaterialType = findViewById(R.id.ModifyMaterialType);
        btnModifyMaterial = findViewById(R.id.btnModifyMaterial);
        spinnerMaterial = findViewById(R.id.spinnerMaterialList);
        adapterSpinnerMaterial = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerMaterial.add("Choose a Material");
        chStr = "";
        spinnerMaterial.setAdapter(adapterSpinnerMaterial);
        loadMaterials();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_modify);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbarMM);
        setSupportActionBar(toolbar);
        setTitle("Modify Material Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chStr = adapterSpinnerMaterial.getItem(position);
                if(position != 0){
                    int in = Objects.requireNonNull(chStr).indexOf("-") - 1;
                    fillOutFields(chStr.substring(2,in));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rgMaterialType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.mrbfg){
                    typeStr = "FG";
                }
                else if(checkedId == R.id.mrbug){
                    typeStr = "UG";
                }
            }
        });
        btnModifyMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMaterial();
            }
        });
    }

    void fillOutFields(final String strMatId){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveMaterialById.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String mDesc;
                            String mDu;
                            double mCost;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    mDesc = jObj.getString("material_description");
                                    mDu = jObj.getString("dimensional_unit");
                                    mCost = jObj.getDouble("cost_per_du");
                                    eTxtDescription.setText(mDesc);
                                    eTxtDimensionalUnit.setText(mDu);
                                    costPerDimensionalUnit.setText(String.valueOf(mCost));
                                    progressBar.setVisibility(View.GONE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"None Found",Toast.LENGTH_LONG).show();
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
                map.put("mat_id", strMatId);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }
    void loadMaterials(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadMaterials.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int mc;
                            String mt;
                            String md;
                            if(success == 1){

                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    mc = jObj.getInt("material_code");
                                    mt = jObj.getString("material_type");
                                    md = jObj.getString("material_description");
                                    adapterSpinnerMaterial.add(mt+Integer.toString(mc) +" - "+md);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
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
    void updateMaterial(){

        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/modifyMaterial.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialModify.this);
                                builder.setTitle(eTxtDescription.getText().toString());
                                builder.setMessage("Successfully modified "+eTxtDescription.getText().toString());
                                clearFields();
                                builder.setPositiveButton("Modify Another Material", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(ActivityMaterialModify.this, ActivityMaterialModify.class));
                                    }
                                });
                                builder.setNegativeButton("View Material", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityMaterialModify.this, ActivityMaterialDisplay.class));
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
                                Toast.makeText(ActivityMaterialModify.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ActivityMaterialModify.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    int in = chStr.indexOf("-") - 1;
                    map.put("material_id", chStr.substring(2,in));
                    map.put("description", eTxtDescription.getText().toString());
                    map.put("type", typeStr);
                    map.put("du", eTxtDimensionalUnit.getText().toString());
                    map.put("costperdu", costPerDimensionalUnit.getText().toString());
                    return map;
                }
            }
            ;
            requestQueue.add(stringRequest);
        }

    }
    void clearFields(){
        costPerDimensionalUnit.setText("");
        eTxtDescription.setText("");
        eTxtDimensionalUnit.setText("");
        rgMaterialType.clearCheck();
    }
    boolean checkRecords(){
        boolean ch = true;
        if(TextUtils.isEmpty(eTxtDescription.getText().toString())){
            eTxtDescription.setError("Description is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtDimensionalUnit.getText().toString())){
            eTxtDimensionalUnit.setError("Dimensional unit is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(costPerDimensionalUnit.getText().toString())){
            costPerDimensionalUnit.setError("Cost is required!");
            ch = false;
        }
        return ch;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            if(!eTxtDescription.getText().toString().trim().isEmpty() || !eTxtDimensionalUnit.getText().toString().trim().isEmpty() || !costPerDimensionalUnit.getText().toString().trim().isEmpty())
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialModify.this);
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
        if(!eTxtDescription.getText().toString().trim().isEmpty() || !eTxtDimensionalUnit.getText().toString().trim().isEmpty() || !costPerDimensionalUnit.getText().toString().trim().isEmpty())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialModify.this);
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