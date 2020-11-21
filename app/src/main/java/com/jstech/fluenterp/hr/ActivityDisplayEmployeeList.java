package com.jstech.fluenterp.hr;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import androidx.appcompat.app.AlertDialog;
import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityDisplayEmployeeList extends BaseActivity {

    ListView listViewEmployees;
    ArrayList<Employee> arrEmployees, tempList;
    ArrayAdapter<String> empNameAdapter;
    StringRequest stringRequest;
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

        setupToolbar(R.id.toolbarDEL, "Display Employees List");
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
        stringRequest = new StringRequest(Request.Method.GET, Constants.URL_RETRIEVE_EMPLOYEES,
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
