package com.jstech.fluenterp.masterdata;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ActivityEmployeeCreate extends AppCompatActivity {

    EditText eTxtEmployeeName;
    EditText eTxtEmployeeAddress;
    EditText eTxtEmployeePhone;
    EditText eTxtEmployeeType;
    EditText eTxtEmployeeDob;
    EditText eTxtEmployeeDoj;
    Spinner spEmployeeType;
    String typeStr;
    Button btnCreateEmployee;
    TextView txtViewDate;
    String date;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    ArrayAdapter<String> adapterType;
    StringRequest stringRequest;

    void initViews(){
        eTxtEmployeeName = findViewById(R.id.createEmployeeName);
        eTxtEmployeeAddress = findViewById(R.id.createEmployeeAddress);
        eTxtEmployeePhone = findViewById(R.id.createEmployeePhone);
        eTxtEmployeeDob = findViewById(R.id.editTextDateOfBirth);
        eTxtEmployeeType = findViewById(R.id.createEmployeeType);
        eTxtEmployeeDoj = findViewById(R.id.editTextDateOfJoining);
        spEmployeeType = findViewById(R.id.spinnerEmployeeType);
        btnCreateEmployee = findViewById(R.id.btnCreateEmployee);
        txtViewDate = findViewById(R.id.dateShowCE);
        progressBar = findViewById(R.id.progressBarCE);
        adapterType = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterType.add("RE-1");
        adapterType.add("RE-2");
        adapterType.add("RE-3");
        adapterType.add("EE-2");
        adapterType.add("EE-1");
        spEmployeeType.setAdapter(adapterType);
        typeStr = "";
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eTxtEmployeeDob.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };
    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eTxtEmployeeDoj.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_create);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        date = sdf.format(new Date());
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbarCE);
        setSupportActionBar(toolbar);
        setTitle("Create Employee Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        txtViewDate.setText(date.substring(6,8)+"/"+date.substring(4,6)+"/"+date.substring(0,4));
        progressBar.setVisibility(View.GONE);

        spEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeStr = adapterType.getItem(position);
                switch (Objects.requireNonNull(typeStr)) {
                    case "RE-1":
                        eTxtEmployeeType.setText("RE-1");
                        break;
                    case "RE-2":
                        eTxtEmployeeType.setText("RE-2");
                        break;
                    case "RE-3":
                        eTxtEmployeeType.setText("RE-3");
                        break;
                    case "EE-2":
                        eTxtEmployeeType.setText("EE-2");
                        break;
                    case "EE-1":
                        eTxtEmployeeType.setText("EE-1");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        eTxtEmployeeDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivityEmployeeCreate.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eTxtEmployeeDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivityEmployeeCreate.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCreateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    createEmployee();
            }
        });

    }



    void createEmployee(){
        if (checkRecords()){
            progressBar.setVisibility(View.VISIBLE);
            final String url = "https://jaspreettechnologies.000webhostapp.com/createEmployee.php";
            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEmployeeCreate.this);
                                builder.setTitle(eTxtEmployeeName.getText().toString());
                                builder.setMessage("Successfully created "+eTxtEmployeeName.getText().toString());
                                builder.setPositiveButton("Add Another Employee", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ActivityEmployeeCreate.this, ActivityEmployeeCreate.class));
                                    }
                                });
                                builder.setNegativeButton("View Employee", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                                dialog.show();
                                clearFields();
                            }catch (Exception e){
                                Toast.makeText(ActivityEmployeeCreate.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ActivityEmployeeCreate.this,"Some Error: "+error,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            error.printStackTrace();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("name", eTxtEmployeeName.getText().toString());
                    map.put("address", eTxtEmployeeAddress.getText().toString());
                    map.put("type", eTxtEmployeeType.getText().toString());
                    map.put("phone", eTxtEmployeePhone.getText().toString());
                    map.put("dob", eTxtEmployeeDob.getText().toString());
                    map.put("doj", eTxtEmployeeDoj.getText().toString());
                    return map;
                }
            }
            ;
            requestQueue.add(stringRequest);
        }
    }


    void clearFields() {
        eTxtEmployeeType.setText("");
        eTxtEmployeeDob.setText("");
        eTxtEmployeeName.setText("");
        eTxtEmployeePhone.setText("");
        eTxtEmployeeAddress.setText("");
        eTxtEmployeeDoj.setText("");
    }
    boolean checkRecords(){
        boolean ch = true;
        if(TextUtils.isEmpty(eTxtEmployeeName.getText().toString())){
            eTxtEmployeeName.setError("Name is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtEmployeePhone.getText().toString())){
            eTxtEmployeePhone.setError("Phone is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtEmployeeAddress.getText().toString())){
            eTxtEmployeeAddress.setError("Address is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtEmployeeType.getText().toString())){
            eTxtEmployeeType.setError("Employee type is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtEmployeeDoj.getText().toString())){
            eTxtEmployeeDoj.setError("Date of joining is required!");
            ch = false;
        }
        if(TextUtils.isEmpty(eTxtEmployeeDob.getText().toString())){
            eTxtEmployeeDob.setError("Date of birth is required!");
            ch = false;
        }
        return ch;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            if(!eTxtEmployeeName.getText().toString().trim().isEmpty() || !eTxtEmployeeAddress.getText().toString().trim().isEmpty() || !eTxtEmployeePhone.getText().toString().trim().isEmpty() || !eTxtEmployeeType.getText().toString().trim().isEmpty()  || !eTxtEmployeeDoj.getText().toString().trim().isEmpty() || !eTxtEmployeeDob.getText().toString().trim().isEmpty())
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEmployeeCreate.this);
                builder.setTitle("BACK!\n\n");
                builder.setMessage("Your form is currently under progress. Are you sure you want to go back?");
                builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();
            }else
            {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!eTxtEmployeeName.getText().toString().trim().isEmpty() || !eTxtEmployeeAddress.getText().toString().trim().isEmpty() || !eTxtEmployeePhone.getText().toString().trim().isEmpty() || !eTxtEmployeeType.getText().toString().trim().isEmpty()  || !eTxtEmployeeDoj.getText().toString().trim().isEmpty() || !eTxtEmployeeDob.getText().toString().trim().isEmpty())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityEmployeeCreate.this);
            builder.setTitle("BACK!\n\n");
            builder.setMessage("Your form is currently under progress. Are you sure you want to go back?");
            builder.setPositiveButton("Yes, I'm sure!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.show();
        }else
        {
            finish();
        }
    }

}
