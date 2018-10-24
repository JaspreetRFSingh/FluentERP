package com.jstech.fluenterp.hr;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityDisplayEmployeeList extends AppCompatActivity {

    ListView listViewEmployees;
    ArrayList<Employee> arrEmployees, tempList;
    ArrayAdapter<String> empNameAdapter;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    Employee emp;
    TextView txtSearchEmployee;
    EditText eTxtSearchEmployee;
    TextView txtViewListTitleDisplay;

    void initViews(){
        progressBar = findViewById(R.id.progressBarDEL);
        progressBar.setVisibility(View.GONE);
        listViewEmployees = findViewById(R.id.listViewEmployees);
        empNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        arrEmployees = new ArrayList<>();
        tempList = new ArrayList<>();
        listViewEmployees.setAdapter(empNameAdapter);
        txtSearchEmployee = findViewById(R.id.txtSearchEmployee);
        eTxtSearchEmployee = findViewById(R.id.editTextSearchEmployee);
        txtViewListTitleDisplay = findViewById(R.id.txtViewListEmps);
        emp = new Employee();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_employee_list);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        requestQueue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbarDEL);
        setSupportActionBar(toolbar);
        setTitle("Display Employees List");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        showEmployeeList();
        txtViewListTitleDisplay.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        txtSearchEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtSearchEmployee.setVisibility(View.VISIBLE);
                txtSearchEmployee.setVisibility(View.GONE);
            }
        });
        eTxtSearchEmployee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        listViewEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                emp = arrEmployees.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayEmployeeList.this);
                builder.setTitle(emp.getEmpName()+"\n\n\n\n");
                builder.setMessage(emp.toString());
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });
    }


    void showEmployeeList(){
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
                            String empAddress;
                            String empType;
                            long empPhone;
                            String dob;
                            String doj;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("employees");
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    empId = jObj.getInt("emp_id");
                                    empName = jObj.getString("emp_name");
                                    empAddress = jObj.getString("emp_address");
                                    empType = jObj.getString("emp_type");
                                    empPhone = jObj.getLong("emp_phone");
                                    dob = jObj.getString("dob");
                                    doj = jObj.getString("doj");
                                    Employee empTemp = new Employee(empId, empName, empAddress, empType, empPhone, dob, doj);
                                    arrEmployees.add(empTemp);
                                    tempList.add(empTemp);
                                    empNameAdapter.add(empTemp.getEmpId()+": "+empTemp.getEmpName());
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
