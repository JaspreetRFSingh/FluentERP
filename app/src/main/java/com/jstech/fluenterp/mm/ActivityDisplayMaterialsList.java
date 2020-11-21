package com.jstech.fluenterp.mm;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.adapters.AdapterDisplayMaterials;
import com.jstech.fluenterp.models.Material;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDisplayMaterialsList extends BaseActivity {


    RecyclerView rvMatList;
    ProgressBar progressBar;
    RecyclerView.Adapter adapterMaterials;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Material> matData;
    StringRequest stringRequest;


    void initViews(){
        rvMatList = findViewById(R.id.recyclerViewMaterialList);
        progressBar = findViewById(R.id.progressBarDML);
        rvMatList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvMatList.setLayoutManager(layoutManager);
        rvMatList.setItemAnimator(new DefaultItemAnimator());
        matData = new ArrayList<>();
        loadMaterials();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_materials_list);
        setupToolbar(R.id.toolbarDML, "Display Materials List");
        initViews();
    }

    void loadMaterials(){

        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, Constants.URL_LOAD_MATERIALS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int mc;
                            String mt;
                            String md;
                            String du;
                            double cost;
                            if(success == 1){

                                JSONArray jsonArray = jsonObject.getJSONArray("materials");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    mc = jObj.getInt("material_code");
                                    mt = jObj.getString("material_type");
                                    md = jObj.getString("material_description");
                                    du = jObj.getString("dimensional_unit");
                                    cost = jObj.getDouble("cost_per_du");
                                    Material material = new Material(mc, mt, md, du, cost);
                                    matData.add(material);
                                }
                                adapterMaterials = new AdapterDisplayMaterials(matData);
                                rvMatList.setAdapter(adapterMaterials);
                                progressBar.setVisibility(View.GONE);
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
        );
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
