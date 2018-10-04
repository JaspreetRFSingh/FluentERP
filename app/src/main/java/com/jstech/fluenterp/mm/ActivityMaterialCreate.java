package com.jstech.fluenterp.mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMC);
        setSupportActionBar(toolbar);
        setTitle("Create Material Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
    }


}