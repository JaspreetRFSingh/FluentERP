package com.jstech.fluenterp.misc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.WebViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class ReportsActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    WebView webView;
    Context ctx;

    StringRequest stringRequest;
    StringRequest stringRequest1;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String dateCurr;


    //charts
    RadarChart chartMat;
    ScatterChart chartCus;
    LineChart chartPrice;
    RelativeLayout rlCus;
    RelativeLayout rlMat;
    RelativeLayout rlPrice;
    FloatingActionButton fabCus;
    FloatingActionButton fabMat;
    FloatingActionButton fabPrice;
    //charts end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        ctx = this;
        Toolbar toolbar = findViewById(R.id.toolbarReports);
        setSupportActionBar(toolbar);
        setTitle("Reports Activity");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int mode = Objects.requireNonNull(getIntent().getExtras()).getInt("mode");
        requestQueue = Volley.newRequestQueue(this);
        chartCus = findViewById(R.id.chartCustomerWise);
        chartMat = findViewById(R.id.chartMaterialWise);
        chartPrice = findViewById(R.id.chartPriceWise);
        rlCus = findViewById(R.id.rlCustomerWise);
        rlMat = findViewById(R.id.rlMaterialWise);
        progressBar = findViewById(R.id.progressBarReports);
        rlPrice = findViewById(R.id.rlPriceWise);
        fabCus = findViewById(R.id.fabCust);
        fabMat = findViewById(R.id.fabMat);
        fabPrice = findViewById(R.id.fabPrice);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        dateCurr = sdf.format(new Date());
        if(mode == 1){
            progressBar.setVisibility(View.VISIBLE);
            rlMat.setVisibility(View.GONE);
            rlCus.setVisibility(View.VISIBLE);
            rlPrice.setVisibility(View.GONE);
            initCustChart();
        }
        else if(mode == 2){
            progressBar.setVisibility(View.VISIBLE);
            rlCus.setVisibility(View.GONE);
            rlMat.setVisibility(View.VISIBLE);
            rlPrice.setVisibility(View.GONE);
            initMatChart();
        }
        else if(mode == 3){
            progressBar.setVisibility(View.VISIBLE);
            rlMat.setVisibility(View.GONE);
            rlCus.setVisibility(View.GONE);
            rlPrice.setVisibility(View.VISIBLE);
            initPriceChart();
        }

        fabCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chartCus.saveToGallery("reportCus.jpg", 85);
                Intent intent = new Intent(ReportsActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createCustomerReport.php");
                startActivity(intent);
            }
        });
        fabMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chartMat.saveToGallery("reportMat.jpg", 85);
                Intent intent = new Intent(ReportsActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createMaterialReport.php");
                startActivity(intent);
            }
        });
        fabPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chartPrice.saveToGallery("reportPrice.jpg", 85);
                Intent intent = new Intent(ReportsActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createPriceReport.php");
                startActivity(intent);
            }
        });
    }

    void initMatChart()
    {
        progressBar.setVisibility(View.GONE);
        chartMat.setVisibility(View.VISIBLE);
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(123f, 0));
        yvalues.add(new Entry(5f,1));
        yvalues.add(new Entry( 67f, 2));
        yvalues.add(new Entry(45f, 3));

        RadarDataSet dataSet = new RadarDataSet(yvalues, "Cities");

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Ludhiana");
        xVals.add("Jalandhar");
        xVals.add("Allahabad");
        xVals.add("Other");

        RadarData data = new RadarData(xVals, dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        chartMat.setData(data);
        chartMat.setDescription("City-wise sales analysis");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        chartMat.setOnChartValueSelectedListener(this);
        chartMat.animateXY(1400, 1400);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void initCustChart(){
        progressBar.setVisibility(View.GONE);
        chartCus.setVisibility(View.VISIBLE);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(34f, 0));
        entries.add(new Entry(44f, 1));
        entries.add(new Entry(56f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(15f, 4));

        ScatterDataSet scatterDataSet = new ScatterDataSet(entries, "Customers");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Cust1");
        labels.add("Cust2");
        labels.add("Cust3");
        labels.add("Cust4");
        labels.add("Others");
        ScatterData data = new ScatterData(labels, scatterDataSet);
        chartCus.setData(data);
        chartCus.setDescription("Sales per Customers");  // set the description
        scatterDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chartCus.animateY(5000);
    }

    void initPriceChart(){
        chartPrice.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new BarEntry(345f, 0));
        entries.add(new BarEntry(543f, 1));
        entries.add(new BarEntry(789f, 2));
        entries.add(new BarEntry(567f, 3));
        entries.add(new BarEntry(34f, 4));
        entries.add(new BarEntry(556f, 5));

        LineDataSet lineDataset = new LineDataSet(entries, "Cells");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Oct");
        labels.add("Sep");
        labels.add("Aug");
        labels.add("Jul");
        labels.add("Jun");
        labels.add("May");
        LineData data = new LineData(labels, lineDataset);
        chartPrice.setData(data);
        chartPrice.setDescription("Distribution amongst range of bill_prices in ₹");  // set the description
        lineDataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chartPrice.animateY(5000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}