package com.jstech.fluenterp.masterdata;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.jstech.fluenterp.adapters.AdapterDisplayEmployees;
import com.jstech.fluenterp.models.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ActivityEmployeeDisplay extends AppCompatActivity {

    ProgressBar progressBarEd;
    LinearLayout llChoiceEmployee;
    LinearLayout llResults;
    Spinner spChoiceEmployee;
    EditText eTxtEmployeeNumber;
    Spinner spEmpType;
    EditText eTxtEmpType;
    EditText eTxtJoiningDate;
    EditText eTxtJoiningDate2;
    Button btnSearch;

    TextView txtSearchEmployee;
    EditText eTxtSearchEmployee;
    //adapter recycler
    RecyclerView recyclerViewEmployeeList;
    AdapterDisplayEmployees adapterEmployees;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Employee> empData;
    ArrayAdapter<String> adapterChoiceEmployee;
    ArrayAdapter<String> adapterEmployeeType;
    String choiceStr;
    String choiceSpinnerType;
    //Volley
    StringRequest stringRequest;
    RequestQueue requestQueue;
    //
    String empId;
    String strDate1;
    String strDate2;
    String empType;
    String urlCh = "";
    void initViews(){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        Toolbar toolbar = findViewById(R.id.toolbarED);
        setSupportActionBar(toolbar);
        setTitle("Display Employee Screen");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBarEd = findViewById(R.id.progressBarED);
        llChoiceEmployee = findViewById(R.id.layoutChoiceEmployee);
        llResults = findViewById(R.id.layoutResultEmployees);
        txtSearchEmployee = findViewById(R.id.txtSearchEmployee);
        eTxtSearchEmployee = findViewById(R.id.editTextSearchEmpName);
        spChoiceEmployee = findViewById(R.id.spinnerChoiceEmployee);
        eTxtEmployeeNumber = findViewById(R.id.editTextEmployeeNumber);
        spEmpType = findViewById(R.id.spinnerEmployeeType);
        eTxtEmpType = findViewById(R.id.editTextEmployeeType);
        eTxtJoiningDate = findViewById(R.id.editTextDate1);
        eTxtJoiningDate2 = findViewById(R.id.editTextDate2);
        recyclerViewEmployeeList = findViewById(R.id.recyclerViewEmployeeList);
        btnSearch = findViewById(R.id.btnSearchEmployees);
        llResults.setVisibility(View.GONE);
        llChoiceEmployee.setVisibility(View.VISIBLE);
        adapterChoiceEmployee = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterEmployeeType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        initEmployeeTypeSpinner();
    }
    void initChoices(){
        adapterChoiceEmployee.add("No filters selected");
        adapterChoiceEmployee.add("Display by employee number");
        adapterChoiceEmployee.add("Display by employee type");
        adapterChoiceEmployee.add("Display by date of joining");
        adapterChoiceEmployee.add("Display by range of date of joining");
        spChoiceEmployee.setAdapter(adapterChoiceEmployee);
        choiceStr = "";
        choiceSpinnerType = "";

    }
    void initEmployeeTypeSpinner()
    {
        adapterEmployeeType.add("RE-1");
        adapterEmployeeType.add("RE-2");
        adapterEmployeeType.add("RE-3");
        adapterEmployeeType.add("EE-2");
        adapterEmployeeType.add("EE-1");
        spEmpType.setAdapter(adapterEmployeeType);
    }
    void initRecyclerView(){
        recyclerViewEmployeeList = findViewById(R.id.recyclerViewEmployeeList);
        recyclerViewEmployeeList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmployeeList.setLayoutManager(layoutManager);
        recyclerViewEmployeeList.setItemAnimator(new DefaultItemAnimator());
        empData = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_display);
        requestQueue = Volley.newRequestQueue(this);
        initViews();
        initChoices();
        spChoiceEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceStr = adapterChoiceEmployee.getItem(position);
                switch (Objects.requireNonNull(choiceStr)) {
                    case "No filters selected":
                        eTxtEmployeeNumber.setVisibility(View.GONE);
                        spEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setText("");
                        eTxtJoiningDate.setVisibility(View.GONE);
                        eTxtJoiningDate.setText("");
                        eTxtJoiningDate2.setVisibility(View.GONE);
                        eTxtJoiningDate2.setText("");
                        break;
                    case "Display by employee number":
                        eTxtEmployeeNumber.setVisibility(View.VISIBLE);
                        spEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setText("");
                        eTxtJoiningDate.setVisibility(View.GONE);
                        eTxtJoiningDate.setText("");
                        eTxtJoiningDate2.setVisibility(View.GONE);
                        eTxtJoiningDate2.setText("");
                        break;
                    case "Display by employee type":
                        eTxtEmployeeNumber.setVisibility(View.GONE);
                        spEmpType.setVisibility(View.VISIBLE);
                        eTxtEmpType.setVisibility(View.VISIBLE);
                        //eTxtEmpType.setText("");
                        eTxtJoiningDate.setVisibility(View.GONE);
                        eTxtJoiningDate.setText("");
                        eTxtJoiningDate2.setVisibility(View.GONE);
                        eTxtJoiningDate2.setText("");
                        break;
                    case "Display by date of joining":
                        eTxtEmployeeNumber.setVisibility(View.GONE);
                        spEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setText("");
                        eTxtJoiningDate.setVisibility(View.VISIBLE);
                        //eTxtJoiningDate.setText("");
                        eTxtJoiningDate2.setVisibility(View.GONE);
                        //eTxtJoiningDate2.setText("");
                        break;
                    case "Display by range of date of joining":
                        eTxtEmployeeNumber.setVisibility(View.GONE);
                        spEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setVisibility(View.GONE);
                        eTxtEmpType.setText("");
                        eTxtJoiningDate.setVisibility(View.VISIBLE);
                        //eTxtJoiningDate.setText("");
                        eTxtJoiningDate2.setVisibility(View.VISIBLE);
                        //eTxtJoiningDate2.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        eTxtJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivityEmployeeDisplay.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eTxtJoiningDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivityEmployeeDisplay.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        spEmpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eTxtEmpType.setText(adapterEmployeeType.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initRecyclerView();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (choiceStr) {
                    case "No filters selected":
                        urlCh = "https://jaspreettechnologies.000webhostapp.com/retrieveEmployees.php";
                        retrieveEmployees();
                        break;
                    case "Display by employee number":
                        if (TextUtils.isEmpty(eTxtEmployeeNumber.getText().toString())) {
                            eTxtEmployeeNumber.setError("Please enter an employee id!");
                        } else {
                            urlCh = "https://jaspreettechnologies.000webhostapp.com/retrieveEmployeeByNumber.php";
                            empId = eTxtEmployeeNumber.getText().toString();
                            retrieveEmployees();
                        }
                        break;
                    case "Display by employee type":
                        if (TextUtils.isEmpty(eTxtEmpType.getText().toString())) {
                            eTxtEmpType.setError("Please select an employee type!");
                        } else {
                            urlCh = "https://jaspreettechnologies.000webhostapp.com/retrieveEmployeeByType.php";
                            empType = eTxtEmpType.getText().toString();
                            retrieveEmployees();
                        }
                        break;
                    case "Display by date of joining":
                        if (TextUtils.isEmpty(eTxtJoiningDate.getText().toString())) {
                            eTxtJoiningDate.setError("Please select a joining date!");
                        } else {
                            urlCh = "https://jaspreettechnologies.000webhostapp.com/retrieveEmployeeByDates.php";
                            strDate1 = eTxtJoiningDate.getText().toString();
                            retrieveEmployees();
                        }
                        break;
                    case "Display by range of date of joining":
                        if (TextUtils.isEmpty(eTxtJoiningDate.getText().toString())) {
                            eTxtJoiningDate.setError("Please select a joining date!");
                        } else {
                            urlCh = "https://jaspreettechnologies.000webhostapp.com/retrieveEmployeeByDates.php";
                            strDate1 = eTxtJoiningDate.getText().toString();
                            retrieveEmployees();
                        }
                        if (TextUtils.isEmpty(eTxtJoiningDate2.getText().toString())) {
                            eTxtJoiningDate2.setError("Please select a joining date!");
                        } else {
                            strDate2 = eTxtJoiningDate2.getText().toString();
                            retrieveEmployees();
                        }
                        break;
                }
            }
        });
        txtSearchEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchEmployee.setVisibility(View.GONE);
                eTxtSearchEmployee.setVisibility(View.VISIBLE);
            }
        });
        eTxtSearchEmployee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterEmployees.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void retrieveEmployees(){
        progressBarEd.setVisibility(View.VISIBLE);
        llChoiceEmployee.setVisibility(View.GONE);
        llResults.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, urlCh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            int empId;
                            String empName;
                            String empAddress;
                            String empType;
                            long empPhone;
                            String dob;
                            String doj;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("employees");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    empId = jObj.getInt("emp_id");
                                    empName = jObj.getString("emp_name");
                                    empAddress = jObj.getString("emp_address");
                                    empType = jObj.getString("emp_type");
                                    empPhone = jObj.getLong("emp_phone");
                                    dob = jObj.getString("dob");
                                    doj = jObj.getString("doj");
                                    Employee emp = new Employee(empId,empName, empAddress, empType, empPhone, dob, doj);
                                    empData.add(emp);
                                }
                                adapterEmployees = new AdapterDisplayEmployees(empData);
                                recyclerViewEmployeeList.setAdapter(adapterEmployees);
                                progressBarEd.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                progressBarEd.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            progressBarEd.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        progressBarEd.setVisibility(View.GONE);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams(){
                HashMap<String,String> map = new HashMap<>();
                if(!eTxtEmployeeNumber.getText().toString().isEmpty() && eTxtEmpType.getText().toString().isEmpty() && eTxtJoiningDate.getText().toString().isEmpty() && eTxtJoiningDate2.getText().toString().isEmpty()){
                    map.put("emp_id", empId);
                }
                else if(eTxtEmployeeNumber.getText().toString().isEmpty() && !eTxtEmpType.getText().toString().isEmpty() && eTxtJoiningDate.getText().toString().isEmpty() && eTxtJoiningDate2.getText().toString().isEmpty())
                {
                    map.put("emp_type", empType);
                }
                else if(eTxtEmployeeNumber.getText().toString().isEmpty() && eTxtEmpType.getText().toString().isEmpty() && !eTxtJoiningDate.getText().toString().isEmpty() && eTxtJoiningDate2.getText().toString().isEmpty())
                {
                    map.put("emp_doj1", strDate1);
                }
                else if(eTxtEmployeeNumber.getText().toString().isEmpty() && eTxtEmpType.getText().toString().isEmpty() && !eTxtJoiningDate.getText().toString().isEmpty() && !eTxtJoiningDate2.getText().toString().isEmpty())
                {
                    map.put("emp_doj1", strDate1);
                    map.put("emp_doj2", strDate2);
                }
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
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
        eTxtJoiningDate.setText(sdf.format(myCalendar.getTime()));
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
        eTxtJoiningDate2.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}
