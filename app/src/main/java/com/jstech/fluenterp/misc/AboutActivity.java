package com.jstech.fluenterp.misc;
import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import com.jstech.fluenterp.R;
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupToolbar(R.id.toolbarAbout, "About Activity");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}