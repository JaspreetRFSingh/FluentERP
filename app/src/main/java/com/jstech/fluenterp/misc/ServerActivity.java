package com.jstech.fluenterp.misc;

import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;

import com.jstech.fluenterp.R;

public class ServerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        setupToolbar(R.id.toolbarServer, "Server");
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}