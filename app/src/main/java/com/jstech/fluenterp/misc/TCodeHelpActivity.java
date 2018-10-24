package com.jstech.fluenterp.misc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jstech.fluenterp.R;

import java.util.ArrayList;

public class TCodeHelpActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> SAPTCodeList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcode_help);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        listView = findViewById(R.id.listViewTCodes);
        Toolbar toolbar = findViewById(R.id.toolbarTCH);
        setSupportActionBar(toolbar);
        setTitle("T-Codes");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initList();
    }

    void initList(){
        SAPTCodeList = new ArrayList<>();
        SAPTCodeList.add("VA01 - Create Sales Order");
        SAPTCodeList.add("VA02 - Modify Sales Order");
        SAPTCodeList.add("VA03 - Display Sales Order");
        SAPTCodeList.add("VA05 - Display Sales Orders List");
        SAPTCodeList.add("MM01 - Create Material");
        SAPTCodeList.add("MM02 - Modify Material");
        SAPTCodeList.add("MM03 - Display Material");
        SAPTCodeList.add("MM04 - Display Materials List");
        SAPTCodeList.add("MM12 - Current Stock");
        SAPTCodeList.add("PP01 - Create Purchase Order");
        SAPTCodeList.add("PP02 - Modify Purchase Order");
        SAPTCodeList.add("PP03 - Display Purchase Order");
        SAPTCodeList.add("PP05 - Change Purchase Order Status");
        SAPTCodeList.add("PP10 - Display List of Sellers");
        SAPTCodeList.add("HR10 - Display List of Employees");
        SAPTCodeList.add("HR05 - Attendance Record");
        SAPTCodeList.add("HR15 - Display Employee Salary Schema");
        SAPTCodeList.add("HR25 - Employee Bonuses and Incentives");
        SAPTCodeList.add("DD01 - Check Order Status");
        SAPTCodeList.add("DD02 - Dispatch Incoming Orders");
        SAPTCodeList.add("MD01 - Create Customer");
        SAPTCodeList.add("MD02 - Modify Customer");
        SAPTCodeList.add("MD03 - Display Customer");
        SAPTCodeList.add("MD11 - Create Employee");
        SAPTCodeList.add("MD12 - Modify Employee");
        SAPTCodeList.add("MD13 - Display Employee");
        adapter = new ArrayAdapter<>(TCodeHelpActivity.this, android.R.layout.simple_list_item_1, SAPTCodeList);
        listView.setAdapter(adapter);
        listView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
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