package com.jstech.fluenterp.mm;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityMaterialDisplay extends AppCompatActivity {


    Button btnRetrieve;
    Button btnRetrieveAll;
    EditText eTxtMaterial;
    TextView txtViewMaterial;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ArrayList<Material> materialArrayList;
    ArrayList<String> materialNameList;
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView txtResultView;
    ProgressBar progressBar;
    String mn;
    Material mat;

    void init(){
        requestQueue = Volley.newRequestQueue(this);
        btnRetrieve = findViewById(R.id.btnRetrieveMaterial);
        btnRetrieveAll = findViewById(R.id.btnAllM);
        txtResultView = findViewById(R.id.txtResultDetailViewMaterial);
        eTxtMaterial = findViewById(R.id.editTextMaterialNumber);
        txtViewMaterial = findViewById(R.id.txtMaterialDetail);
        materialArrayList = new ArrayList<>();
        materialNameList = new ArrayList<>();
        listView = findViewById(R.id.listViewRetrieveMaterials);
        progressBar = findViewById(R.id.progressBarMD);
        adapter = new ArrayAdapter<>(ActivityMaterialDisplay.this, android.R.layout.simple_list_item_1, materialNameList);
        listView.setAdapter(adapter);
        mat = new Material();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_display);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarMD);
        setSupportActionBar(toolbar);
        setTitle("Display Material");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        btnRetrieveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResultView.setVisibility(View.VISIBLE);
                txtResultView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
                retrieveAllM();
                listView.setVisibility(View.VISIBLE);
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResultView.setVisibility(View.VISIBLE);
                txtResultView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
                listView.setVisibility(View.GONE);
                mn = eTxtMaterial.getText().toString();
                retrieveM();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mat = materialArrayList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMaterialDisplay.this);
                builder.setTitle(mat.getMaterialDescription()+"\n\n\n\n");
                builder.setMessage(mat.toString());
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });
    }


    void retrieveAllM(){
        listView.setVisibility(View.VISIBLE);
        txtViewMaterial.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        txtViewMaterial.setText("");
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/loadMaterials.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            int code;
                            String type;
                            String desc;
                            String du;
                            double price;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    code = jObj.getInt("material_code");
                                    type = jObj.getString("material_type");
                                    desc = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    price = jObj.getDouble("cost_per_du");

                                    Material material = new Material(code, type, desc, du, price);
                                    materialArrayList.add(material);
                                    materialNameList.add(code+" - "+desc);
                                }
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityMaterialDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityMaterialDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityMaterialDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    void retrieveM(){
        txtViewMaterial.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveMaterialById.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            int code;
                            String type;
                            String desc;
                            String du;
                            double price;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    code = jObj.getInt("material_code");
                                    type = jObj.getString("material_type");
                                    desc = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    price = jObj.getDouble("cost_per_du");

                                    Material material = new Material(code, type, desc, du, price);
                                    txtViewMaterial.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right));
                                    txtViewMaterial.setText(material.toString());
                                    txtViewMaterial.clearAnimation();
                                }
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ActivityMaterialDisplay.this,"No Records Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityMaterialDisplay.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ActivityMaterialDisplay.this,"Volley Error: "+error,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("mat_id",mn);
                return map;
            }
        }
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
