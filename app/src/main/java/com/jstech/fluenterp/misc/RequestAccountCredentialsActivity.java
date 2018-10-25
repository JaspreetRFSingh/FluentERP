package com.jstech.fluenterp.misc;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestAccountCredentialsActivity extends AppCompatActivity {

    int mode;
    TextView txtViewTitle;
    TextView txtDateShow;
    ProgressBar progressBar;
    EditText eTxtUsername;
    EditText eTxtPassword;
    EditText eTxtRegisteringEmpId;
    Spinner spEmpList;
    ArrayAdapter<String> adapterEmp;
    EditText eTxtAuthorizingName;
    Button btnSubmit;
    Button btnUpdate;
    String date;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    String idPass;

    void initViews(){
        requestQueue = Volley.newRequestQueue(this);
        txtViewTitle = findViewById(R.id.txtChoose);
        txtDateShow = findViewById(R.id.dateShowRAC);
        progressBar = findViewById(R.id.progressBarRAC);
        eTxtUsername = findViewById(R.id.editTextUserName);
        eTxtPassword = findViewById(R.id.editTextPassword);
        eTxtRegisteringEmpId = findViewById(R.id.editTextRegisteringId);
        eTxtAuthorizingName = findViewById(R.id.editTextRegisteringAuthorityName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUpdate = findViewById(R.id.btnUpdateAccount);
        btnUpdate.setVisibility(View.GONE);
        eTxtRegisteringEmpId.setVisibility(View.GONE);
        eTxtAuthorizingName.setVisibility(View.GONE);
        spEmpList = findViewById(R.id.spinnerEmployeeList);
        adapterEmp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapterEmp.add("Choose an Employee");
        loadEmployees();
        spEmpList.setAdapter(adapterEmp);
        date = "";
    }


    void loadEmployees(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://jaspreettechnologies.000webhostapp.com/retrieveEmployees.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int empId;
                            String empName;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("employees");
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    empId = jObj.getInt("emp_id");
                                    empName = jObj.getString("emp_name");
                                    adapterEmp.add(empId+" - "+empName);
                                }
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
        requestQueue.add(stringRequest);
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
            spEmpList.setVisibility(View.VISIBLE);
            eTxtRegisteringEmpId.setVisibility(View.VISIBLE);
            eTxtAuthorizingName.setVisibility(View.VISIBLE);
        }
        else if (mode == 2){
            spEmpList.setVisibility(View.GONE);
            txtViewTitle.setText("Modify Existing Account");
            eTxtRegisteringEmpId.setVisibility(View.GONE);
            eTxtAuthorizingName.setVisibility(View.GONE);
        }

        spEmpList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strTemp = adapterEmp.getItem(position);
                if(position!=0){
                    int ind = Objects.requireNonNull(strTemp).indexOf("-");
                    eTxtRegisteringEmpId.setText(strTemp);
                    idPass = strTemp.substring(0,ind);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode ==1){
                    createAccount();
                }
                else if(mode == 2){
                    checkLoginCredentials();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyAccount();
            }
        });
    }

    void checkLoginCredentials(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, "https://jaspreettechnologies.000webhostapp.com/checkLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    String id;
                    String pass;
                    String userName;
                    String authPers;
                    if (success == 1) {
                        eTxtPassword.setVisibility(View.VISIBLE);
                        eTxtAuthorizingName.setVisibility(View.VISIBLE);
                        eTxtUsername.setVisibility(View.VISIBLE);
                        eTxtRegisteringEmpId.setVisibility(View.VISIBLE);
                        eTxtRegisteringEmpId.setEnabled(false);
                        spEmpList.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.getJSONArray("emp_credentials");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            id = jObj.getString("emp_id");
                            userName = jObj.getString("empUserName");
                            pass = jObj.getString("empPassword");
                            authPers = jObj.getString("auth_person");
                            eTxtRegisteringEmpId.setText(id);
                            eTxtUsername.setText(userName);
                            eTxtAuthorizingName.setText(authPers);
                            eTxtPassword.setText(pass);
                        }
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Some Exception: " + e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Volley Error: "+error, Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("username", eTxtUsername.getText().toString());
                map.put("password", eTxtPassword.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    void createAccount(){
        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/registerNewAccount.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if(success == 1){
                                    progressBar.setVisibility(View.GONE);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RequestAccountCredentialsActivity.this);
                                    builder.setTitle(eTxtRegisteringEmpId.getText().toString());
                                    builder.setMessage("Successfully created "+eTxtUsername.getText().toString()+"'s account!");
                                    builder.setPositiveButton("Add Another Account", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(RequestAccountCredentialsActivity.this, RequestAccountCredentialsActivity.class));
                                        }
                                    });
                                    builder.setNegativeButton("View Account", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                                    dialog.show();
                                    clearFields();
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }

                            }catch (Exception e){
                                Toast.makeText(RequestAccountCredentialsActivity.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RequestAccountCredentialsActivity.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("id", idPass);
                    map.put("username", eTxtUsername.getText().toString());
                    map.put("password", eTxtPassword.getText().toString());
                    map.put("auth_person", eTxtAuthorizingName.getText().toString());
                    return map;
                }
            }
            ;
            requestQueue.add(stringRequest);
        }

    }

    boolean checkRecords(){
        boolean ch = true;
        if(TextUtils.isEmpty(eTxtUsername.getText().toString())){
            eTxtUsername.setError("Username is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtPassword.getText().toString())){
            eTxtPassword.setError("Password is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtAuthorizingName.getText().toString())){
            eTxtAuthorizingName.setError("Authorizing name is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtRegisteringEmpId.getText().toString())){
            eTxtRegisteringEmpId.setError("Registering name is required!");
            ch = false;
        }
        return ch;
    }

    void clearFields(){
        eTxtRegisteringEmpId.setText("");
        eTxtAuthorizingName.setText("");
        eTxtPassword.setText("");
        eTxtUsername.setText("");
    }

    void modifyAccount(){
        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/updateExistingAccount.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if(success == 1){
                                    progressBar.setVisibility(View.GONE);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RequestAccountCredentialsActivity.this);
                                    builder.setTitle(eTxtRegisteringEmpId.getText().toString());
                                    builder.setMessage("Successfully Updated "+eTxtUsername.getText().toString()+"'s account!");
                                    builder.setPositiveButton("Update Another Account", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(RequestAccountCredentialsActivity.this, RequestAccountCredentialsActivity.class));
                                        }
                                    });
                                    builder.setNegativeButton("View Account", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                                    dialog.show();
                                    clearFields();
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }

                            }catch (Exception e){
                                Toast.makeText(RequestAccountCredentialsActivity.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RequestAccountCredentialsActivity.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("id", eTxtRegisteringEmpId.getText().toString());
                    map.put("username", eTxtUsername.getText().toString());
                    map.put("password", eTxtPassword.getText().toString());
                    map.put("auth_person", eTxtAuthorizingName.getText().toString());
                    return map;
                }
            }
            ;
            requestQueue.add(stringRequest);
        }

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
