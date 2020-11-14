package com.jstech.fluenterp.misc;

import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jstech.fluenterp.R;

import java.util.ArrayList;

public class TCodeHelpActivity extends BaseActivity {
    ListView listView;
    ArrayList<String> SAPTCodeList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcode_help);
        listView = findViewById(R.id.listViewTCodes);
        setupToolbar(R.id.toolbarTCH, "T-Codes");
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


    @Override
    public void onBackPressed() {
        finish();
    }
}