package com.jstech.fluenterp.misc;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jstech.fluenterp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RequestAccountCredentialsActivity extends AppCompatActivity {

    int mode;
    TextView txtViewTitle;
    TextView txtDateShow;
    ProgressBar progressBar;
    EditText eTxtUsername;
    EditText eTxtPassword;
    EditText eTxtRegisteringName;
    EditText eTxtAuthorizingName;
    Button btnSubmit;
    String date;

    void initViews(){
        txtViewTitle = findViewById(R.id.txtChoose);
        txtDateShow = findViewById(R.id.dateShowRAC);
        progressBar = findViewById(R.id.progressBarRAC);
        eTxtUsername = findViewById(R.id.editTextUserName);
        eTxtPassword = findViewById(R.id.editTextPassword);
        eTxtRegisteringName = findViewById(R.id.editTextRegisteringName);
        eTxtAuthorizingName = findViewById(R.id.editTextRegisteringAuthorityName);
        btnSubmit = findViewById(R.id.btnSubmit);
        eTxtRegisteringName.setVisibility(View.GONE);
        eTxtAuthorizingName.setVisibility(View.GONE);
        date = "";
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_account_credentials);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar =  findViewById(R.id.toolbarRAC);
        setSupportActionBar(toolbar);
        mode = Objects.requireNonNull(getIntent().getExtras()).getInt("mode");
        setTitle("Account Credentials");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        txtDateShow.setText(date.substring(6,8)+"/"+date.substring(4,6)+"/"+date.substring(0,4));
        if (mode == 1) {
            txtViewTitle.setText("Request New Account Credentials");
            eTxtRegisteringName.setVisibility(View.VISIBLE);
            eTxtAuthorizingName.setVisibility(View.VISIBLE);
        }
        else if (mode == 2){
            txtViewTitle.setText("Modify Existing Account");
            eTxtRegisteringName.setVisibility(View.GONE);
            eTxtAuthorizingName.setVisibility(View.GONE);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode ==1){
                    createAccount();
                }
                else if(mode == 2){
                    modifyAccount();
                }
            }
        });
    }

    void createAccount(){
        Toast.makeText(this, "Creating an Account", Toast.LENGTH_LONG).show();
    }

    void modifyAccount(){
        Toast.makeText(this, "Modifying Account", Toast.LENGTH_LONG).show();
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
