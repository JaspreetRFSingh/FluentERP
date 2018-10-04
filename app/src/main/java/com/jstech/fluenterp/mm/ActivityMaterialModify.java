package com.jstech.fluenterp.mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.jstech.fluenterp.models.Material;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityMaterialModify extends AppCompatActivity {
    String date;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    TextView txtDateShow;
    Spinner spinnerMaterial;
    ArrayAdapter<String> adapterSpinnerMaterial;

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
        adapterSpinnerMaterial = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerMaterial.add("Choose a Material");
        spinnerMaterial.setAdapter(adapterSpinnerMaterial);
        loadMaterials();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_modify);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMM);
        setSupportActionBar(toolbar);
        setTitle("Modify Material Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
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
                                    final Material material = new Material(mc, mt, md, du, cost);
                                    //matArray[i] = material;*/
                                    adapterSpinnerMaterial.add(mt+Integer.toString(mc) +" - "+md);
                                    progressBar.setVisibility(View.GONE);
                                    spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                            if(pos!=0){
                                                eTxtDescription.setText(material.getMaterialDescription());
                                                eTxtDimensionalUnit.setText(material.getDimensionalUnit());
                                                costPerDimensionalUnit.setText(String.valueOf(material.getCostPerDu()));
                                            }
                                            else if (pos == 0)
                                            {
                                            }
                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });
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

}