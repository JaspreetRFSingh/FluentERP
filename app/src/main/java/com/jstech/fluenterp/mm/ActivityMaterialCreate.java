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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.R;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityMaterialCreate extends AppCompatActivity {
    String date;
    ProgressBar progressBar;
    RequestQueue requestQueue;
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
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbarMC);
        setSupportActionBar(toolbar);
        setTitle("Create Material Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
            final String url = "https://jaspreettechnologies.000webhostapp.com/createMaterial.php";
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
            requestQueue.add(stringRequest);
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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