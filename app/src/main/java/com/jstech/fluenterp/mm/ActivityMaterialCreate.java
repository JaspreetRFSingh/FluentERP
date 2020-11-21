package com.jstech.fluenterp.mm;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityMaterialCreate extends BaseActivity {
    String date;
    ProgressBar progressBar;
    StringRequest stringRequest;
    TextView txtDateShow;
    EditText eTxtDescription;
    EditText eTxtDimensionalUnit;
    EditText costPerDimensionalUnit;
    RadioGroup rgMaterialType;
    String rgMatTypeStr;
    Button btnCreateMaterial;

    void initViews(){
        txtDateShow = findViewById(R.id.dateShowMatCreate);
        progressBar = findViewById(R.id.progressBarMC);
        progressBar.setVisibility(View.GONE);
        eTxtDescription = findViewById(R.id.CreateMaterialDescription);
        eTxtDimensionalUnit = findViewById(R.id.CreateMaterialDimensional);
        costPerDimensionalUnit = findViewById(R.id.CreateMaterialCostPerDu);
        rgMaterialType = findViewById(R.id.CreateMaterialType);
        btnCreateMaterial = findViewById(R.id.btnCreateMaterial);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_create);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        setupToolbar(R.id.toolbarMC, "Create Material Screen");
        initViews();
        rgMaterialType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbfg){
                    rgMatTypeStr = "FG";
                }
                else if(checkedId == R.id.rbug){
                    rgMatTypeStr = "UG";
                }
            }
        });
        btnCreateMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMaterial();
            }
        });
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

    void createMaterial(){

        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = Constants.URL_CREATE_MATERIAL;
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialCreate.this);
                                builder.setTitle(eTxtDescription.getText().toString());
                                builder.setMessage("Successfully created "+eTxtDescription.getText().toString());
                                clearFields();
                                builder.setPositiveButton("Add Another Material", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityMaterialCreate.this, ActivityMaterialCreate.class));
                                    }
                                });
                                builder.setNegativeButton("View Material", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityMaterialCreate.this, ActivityMaterialDisplay.class));
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                                dialog.show();
                            }catch (Exception e){
                                Toast.makeText(ActivityMaterialCreate.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ActivityMaterialCreate.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }}
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("description", eTxtDescription.getText().toString());
                    map.put("type", rgMatTypeStr);
                    map.put("du", eTxtDimensionalUnit.getText().toString());
                    map.put("costperdu", costPerDimensionalUnit.getText().toString());
                    return map;
                }
            }
            ;
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }


    @Override
    public void onBackPressed() {
        if(!eTxtDescription.getText().toString().trim().isEmpty() || !eTxtDimensionalUnit.getText().toString().trim().isEmpty() || !costPerDimensionalUnit.getText().toString().trim().isEmpty())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialCreate.this);
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