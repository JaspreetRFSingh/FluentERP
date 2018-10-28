package com.jstech.fluenterp.misc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jstech.fluenterp.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class GraphicalAnalysisActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    PieChart pieChart;
    BarChart barChart;
    StringRequest stringRequest;
    StringRequest stringRequest1;
    RequestQueue requestQueue;
    ProgressBar progressBar;

    double ldhPrice;
    double jldPrice;
    double allahPrice;
    double totPrice;
    RelativeLayout rlBar;
    FloatingActionButton fabBar;
    FloatingActionButton fabPie;
    RelativeLayout rlPie;

    double pricem1;
    double pricem2;
    double pricem3;
    double pricem4;
    double pricem5;
    double pricem6;

    String dateCurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical_analysis);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarGraphical);
        setSupportActionBar(toolbar);
        setTitle("Graphical Analysis");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int mode = Objects.requireNonNull(getIntent().getExtras()).getInt("mode");
        requestQueue = Volley.newRequestQueue(this);
        pieChart =  findViewById(R.id.piechart);
        barChart =  findViewById(R.id.barchart);
        fabBar = findViewById(R.id.fabBar);
        fabPie = findViewById(R.id.fabPie);
        rlBar = findViewById(R.id.rlBarChart);
        progressBar = findViewById(R.id.progressBarGraphicalAnalysis);
        rlPie = findViewById(R.id.rlPieChart);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        dateCurr = sdf.format(new Date());

        if(mode == 1){
            progressBar.setVisibility(View.VISIBLE);
            rlBar.setVisibility(View.GONE);
            rlPie.setVisibility(View.VISIBLE);
            retrieveSumTotal();
        }
        else if(mode == 2){
            progressBar.setVisibility(View.VISIBLE);
            rlPie.setVisibility(View.GONE);
            rlBar.setVisibility(View.VISIBLE);
            retrieveSumsMonth();
        }

        fabBar.setImageResource(R.drawable.ic_menu_share);
        fabPie.setImageResource(R.drawable.ic_menu_share);
        fabBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart.saveToGallery("barImage.jpg", 85);
                Toast.makeText(getApplicationContext(), "Chart saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });
        fabPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.saveToGallery("pieImage.jpg", 85);
                Toast.makeText(getApplicationContext(), "Chart saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void retrieveSumTotal(){
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveChartResults.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int successL = jsonObject.getInt("success");
                            String messageL = jsonObject.getString("message");
                            if(successL == 1){
                                JSONArray jsonArrayL = jsonObject.getJSONArray("ludhiana_orders");
                                for(int i = 0; i<jsonArrayL.length(); i++) {
                                    JSONObject jObjJ = jsonArrayL.getJSONObject(i);
                                    ldhPrice = jObjJ.getDouble("SUM(bill_price)");
                                }
                                    JSONArray jsonArrayJ = jsonObject.getJSONArray("jalandhar_orders");
                                    for(int i = 0; i<jsonArrayJ.length(); i++){
                                        JSONObject jObjJ = jsonArrayJ.getJSONObject(i);
                                        jldPrice = jObjJ.getDouble("SUM(bill_price)");
                                    }

                                JSONArray jsonArrayA = jsonObject.getJSONArray("allahabad_orders");
                                for(int i = 0; i<jsonArrayA.length(); i++){
                                    JSONObject jObjJ = jsonArrayA.getJSONObject(i);
                                    allahPrice = jObjJ.getDouble("SUM(bill_price)");
                                }

                                JSONArray jsonArrayT = jsonObject.getJSONArray("total_orders");
                                for(int i = 0; i<jsonArrayT.length(); i++){
                                    JSONObject jObjJ = jsonArrayT.getJSONObject(i);
                                    totPrice = jObjJ.getDouble("SUM(bill_price)");
                                }

                                initPieChart((float)ldhPrice,(float)jldPrice,(float)allahPrice,(float)totPrice);

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),messageL,Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    void initPieChart(float sl, float sj, float sa, float st)
    {
        progressBar.setVisibility(View.GONE);
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setUsePercentValues(true);
        ArrayList<Entry> yvalues = new ArrayList<>();
        yvalues.add(new Entry( sl, 0));
        yvalues.add(new Entry( sj, 1));
        yvalues.add(new Entry( sa, 2));
        yvalues.add(new Entry(st - sl -sj - sa, 3));

        PieDataSet dataSet = new PieDataSet(yvalues, "Cities");

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Ludhiana");
        xVals.add("Jalandhar");
        xVals.add("Allahabad");
        xVals.add("Other");

        PieData data = new PieData(xVals, dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("City-wise sales analysis");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateXY(1400, 1400);

    }

    void retrieveSumsMonth(){
        stringRequest1 = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/retrieveChartResultsBar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int successL = jsonObject.getInt("success");
                            String messageL = jsonObject.getString("message");
                            if(successL == 1){
                                JSONArray jsonArray1 = jsonObject.getJSONArray("orders_one");
                                for(int i = 0; i<jsonArray1.length(); i++) {
                                    String pr1;
                                    JSONObject jObjJ = jsonArray1.getJSONObject(i);
                                    pr1 = jObjJ.getString("SUM(bill_price)");
                                    if(pr1.equals("null")){
                                        pricem1 = 0.0;
                                    }
                                    else{
                                        pricem1 = Double.parseDouble(pr1);
                                    }
                                }
                                JSONArray jsonArray2 = jsonObject.getJSONArray("orders_two");
                                for(int i = 0; i<jsonArray2.length(); i++){
                                    String pr2;
                                    JSONObject jObjJ = jsonArray2.getJSONObject(i);
                                    pr2 = jObjJ.getString("SUM(bill_price)");
                                    if(pr2.equals("null")){
                                        pricem2 = 0.0;
                                    }
                                    else{
                                        pricem2 = Double.parseDouble(pr2);
                                    }
                                }

                                JSONArray jsonArray3 = jsonObject.getJSONArray("orders_three");
                                for(int i = 0; i<jsonArray3.length(); i++){
                                    String pr3;
                                    JSONObject jObjJ = jsonArray3.getJSONObject(i);
                                    pr3 = jObjJ.getString("SUM(bill_price)");
                                    if(pr3.equals("null")){
                                        pricem3 = 0.0;
                                    }
                                    else{
                                        pricem3 = Double.parseDouble(pr3);
                                    }
                                }

                                JSONArray jsonArray4 = jsonObject.getJSONArray("orders_four");
                                for(int i = 0; i<jsonArray4.length(); i++){
                                    String pr4;
                                    JSONObject jObjJ = jsonArray4.getJSONObject(i);
                                    pr4 = jObjJ.getString("SUM(bill_price)");
                                    if(pr4.equals("null")){
                                        pricem4 = 0.0;
                                    }
                                    else{
                                        pricem4 = Double.parseDouble(pr4);
                                    }
                                }
                                JSONArray jsonArray5 = jsonObject.getJSONArray("orders_five");
                                for(int i = 0; i<jsonArray5.length(); i++){
                                    String pr5;
                                    JSONObject jObjJ = jsonArray5.getJSONObject(i);
                                    pr5 = jObjJ.getString("SUM(bill_price)");
                                    if(pr5.equals("null")){
                                        pricem5 = 0.0;
                                    }
                                    else{
                                        pricem5 = Double.parseDouble(pr5);
                                    }
                                }

                                JSONArray jsonArray6 = jsonObject.getJSONArray("orders_six");
                                for(int i = 0; i<jsonArray6.length(); i++){
                                    String pr6;
                                    JSONObject jObjJ = jsonArray6.getJSONObject(i);
                                    pr6 = jObjJ.getString("SUM(bill_price)");
                                    if(pr6.equals("null")){
                                        pricem6 = 0.0;
                                    }
                                    else{
                                        pricem6 = Double.parseDouble(pr6);
                                    }
                                }

                                initChart((float)pricem1,(float)pricem2,(float)pricem3,(float)pricem4, (float)pricem5, (float)pricem6);

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),messageL,Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() {

                HashMap<String,String> map = new HashMap<>();
                map.put("current_date", dateCurr);
                return map;

            }
        }
        ;
        requestQueue.add(stringRequest1);
    }

    void initChart(float s1, float s2, float s3, float s4, float s5, float s6){
        progressBar.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(s1, 0));
        entries.add(new BarEntry(s2, 1));
        entries.add(new BarEntry(s3, 2));
        entries.add(new BarEntry(s4, 3));
        entries.add(new BarEntry(s5, 4));
        entries.add(new BarEntry(s6, 5));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        ArrayList<String> labels = new ArrayList<>();
        switch (dateCurr.substring(4, 6)) {
            case "12":
                labels.add("Dec" + "\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Nov" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Oct" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Sep" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Aug" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jul" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "11":
                labels.add("Nov" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Oct" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Sep" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Aug" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jul" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jun" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "10":
                labels.add("Oct" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Sep" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Aug" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jul" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jun" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "09":
                labels.add("Sep" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Aug" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jul" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jun" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "08":
                labels.add("Aug" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Jul" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jun" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Mar" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "07":
                labels.add("Jul" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Jun" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Mar" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Feb" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "06":
                labels.add("Jun" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Mar" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Feb" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                break;
            case "05":
                labels.add("May" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Mar" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Feb" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Dec" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));

                break;
            case "04":
                labels.add("Apr" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Mar" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Feb" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Dec" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Nov" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                break;
            case "03":
                labels.add("Mar" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Feb" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Dec" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Nov" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Oct" +"\n" + "/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));

                break;
            case "02":
                labels.add("Feb" +"\n" + "/" + dateCurr.substring(2, 4));
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Dec" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Nov" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Oct" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Sep" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                break;
            case "01":
                labels.add("Jan" + "\n" +"/" + dateCurr.substring(2, 4));
                labels.add("Dec" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Nov" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Oct" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Sep" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                labels.add("Aug" + "\n" +"/" + String.valueOf(Integer.parseInt(dateCurr.substring(2, 4)) - 1));
                break;
        }
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription("Monthwise sales for the year "+ dateCurr.substring(0,4)+" in ₹");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED", "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {

    }
}
