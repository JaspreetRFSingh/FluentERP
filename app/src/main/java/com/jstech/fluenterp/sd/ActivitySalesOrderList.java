package com.jstech.fluenterp.sd;

import com.jstech.fluenterp.network.VolleySingleton;

import com.jstech.fluenterp.Constants;

import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import com.jstech.fluenterp.BaseActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.MainActivity;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.adapters.CustomAdapterSalesOrdersList;
import com.jstech.fluenterp.models.SalesOrder;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ActivitySalesOrderList extends BaseActivity{

    Spinner spinnerChoice;
    Button datePicker1;
    Button datePicker2;
    Button retrieve;
    EditText eTxtSalesDocumentNumber;
    EditText eTxtCustomerNumber;
    EditText editTextDate1;
    EditText editTextDate2;
    CheckBox cbCreated;
    CheckBox cbProcessing;
    CheckBox cbProcessed;
    CheckBox cbDispatched;
    CheckBox cbDelivered;
    ArrayAdapter<String> adapterChoice;
    String choiceStr;

    ProgressBar progressBar;
    ArrayAdapter<String> adapterCustomer;
    Spinner spinnerCustomer;
    StringRequest stringRequest;
    StringRequest stringRequest1;


    //Custom
    String url = "";
    LinearLayout llChoice;
    LinearLayout llResults;
    CustomAdapterSalesOrdersList adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private ArrayList<SalesOrder> data;
    String customer;
    String salesDocNo;
    String dateStr;
    String dateStr2;

    //CheckBoxes
    String strCreated = "";
    String strProcessing = "";
    String strProcessed = "";
    String strDispatched = "";
    String strDelivered = "";

    Context ctx;

    void initViews(){
        setupToolbar(R.id.toolbarSOL, "List of Sales Order");
        progressBar = findViewById(R.id.progressBarSOL);
        spinnerChoice = findViewById(R.id.spinnerChoice);
        spinnerCustomer = findViewById(R.id.spinnerCustomer);
        eTxtCustomerNumber = findViewById(R.id.editTextCustomer);
        eTxtSalesDocumentNumber = findViewById(R.id.editTextSalesDocNo);
        datePicker1 = findViewById(R.id.datePicker1);
        editTextDate1 = findViewById(R.id.editTextDate1);
        editTextDate2 = findViewById(R.id.editTextDate2);
        datePicker2 = findViewById(R.id.datePicker2);
        retrieve = findViewById(R.id.btnRetrieveRecords);
        cbCreated = findViewById(R.id.checkBoxCreated);
        cbProcessing = findViewById(R.id.checkBoxProcessing);
        cbProcessed = findViewById(R.id.checkBoxProcessed);
        cbDispatched = findViewById(R.id.checkBoxDispatched);
        cbDelivered = findViewById(R.id.checkBoxDelivered);
        llChoice = findViewById(R.id.layoutChoice);
        llResults = findViewById(R.id.layoutResult);
        llResults.setVisibility(View.GONE);
        adapterChoice = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterChoice.add("No filters selected");
        adapterChoice.add("Display by customer");
        adapterChoice.add("Display by sales document number");
        adapterChoice.add("Display by date");
        adapterChoice.add("Display by range of dates");
        adapterChoice.add("Display by order status");
        spinnerChoice.setAdapter(adapterChoice);
        choiceStr = "";
        ctx = getApplicationContext();

    }

    void initCustomer(){
        //progressBar.setVisibility(View.VISIBLE);
        adapterCustomer = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        adapterCustomer.add("~~ Select a Customer ~~");
        retrieveCustomers();
        //progressBar.setVisibility(View.GONE);
        spinnerCustomer.setAdapter(adapterCustomer);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = adapterCustomer.getItem(position);
                int eind = Objects.requireNonNull(item).indexOf("-");
                if(eind != -1){
                    eTxtCustomerNumber.setText(item.substring(0,eind-1));
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eTxtCustomerNumber.setText("Please select a valid customer number");
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order_list);
        initViews();
        spinnerChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                choiceStr = adapterChoice.getItem(position);
                switch (Objects.requireNonNull(choiceStr)) {
                    case "No filters selected":
                        eTxtCustomerNumber.setVisibility(View.GONE);
                        eTxtSalesDocumentNumber.setVisibility(View.GONE);
                        editTextDate1.setVisibility(View.GONE);
                        datePicker1.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.GONE);
                        datePicker2.setVisibility(View.GONE);
                        spinnerCustomer.setVisibility(View.GONE);
                        cbCreated.setVisibility(View.GONE);
                        cbProcessing.setVisibility(View.GONE);
                        cbProcessed.setVisibility(View.GONE);
                        cbDispatched.setVisibility(View.GONE);
                        cbDelivered.setVisibility(View.GONE);
                        eTxtCustomerNumber.setText("");
                        eTxtSalesDocumentNumber.setText("");
                        editTextDate1.setText("");
                        editTextDate2.setText("");
                        cbCreated.setChecked(false);
                        cbProcessing.setChecked(false);
                        cbProcessed.setChecked(false);
                        cbDispatched.setChecked(false);
                        cbDelivered.setChecked(false);

                        break;
                    case "Display by customer":
                        spinnerCustomer.setVisibility(View.VISIBLE);
                        eTxtCustomerNumber.setVisibility(View.VISIBLE);
                        eTxtSalesDocumentNumber.setVisibility(View.GONE);
                        editTextDate1.setVisibility(View.GONE);
                        datePicker1.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.GONE);
                        datePicker2.setVisibility(View.GONE);
                        cbCreated.setVisibility(View.GONE);
                        cbProcessing.setVisibility(View.GONE);
                        cbProcessed.setVisibility(View.GONE);
                        cbDispatched.setVisibility(View.GONE);
                        cbDelivered.setVisibility(View.GONE);
                        eTxtSalesDocumentNumber.setText("");
                        editTextDate1.setText("");
                        editTextDate2.setText("");
                        cbCreated.setChecked(false);
                        cbProcessing.setChecked(false);
                        cbProcessed.setChecked(false);
                        cbDispatched.setChecked(false);
                        cbDelivered.setChecked(false);
                        initCustomer();

                        break;
                    case "Display by sales document number":
                        eTxtSalesDocumentNumber.setVisibility(View.VISIBLE);
                        eTxtCustomerNumber.setVisibility(View.GONE);
                        editTextDate1.setVisibility(View.GONE);
                        spinnerCustomer.setVisibility(View.GONE);
                        datePicker1.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.GONE);
                        datePicker2.setVisibility(View.GONE);
                        cbCreated.setVisibility(View.GONE);
                        cbProcessing.setVisibility(View.GONE);
                        cbProcessed.setVisibility(View.GONE);
                        cbDispatched.setVisibility(View.GONE);
                        cbDelivered.setVisibility(View.GONE);
                        eTxtCustomerNumber.setText("");
                        editTextDate1.setText("");
                        editTextDate2.setText("");
                        cbCreated.setChecked(false);
                        cbProcessing.setChecked(false);
                        cbProcessed.setChecked(false);
                        cbDispatched.setChecked(false);
                        cbDelivered.setChecked(false);

                        break;
                    case "Display by date":
                        editTextDate1.setVisibility(View.VISIBLE);
                        datePicker1.setVisibility(View.VISIBLE);
                        spinnerCustomer.setVisibility(View.GONE);
                        eTxtSalesDocumentNumber.setVisibility(View.GONE);
                        eTxtCustomerNumber.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.GONE);
                        datePicker2.setVisibility(View.GONE);
                        cbCreated.setVisibility(View.GONE);
                        cbProcessing.setVisibility(View.GONE);
                        cbProcessed.setVisibility(View.GONE);
                        cbDispatched.setVisibility(View.GONE);
                        cbDelivered.setVisibility(View.GONE);
                        eTxtCustomerNumber.setText("");
                        eTxtSalesDocumentNumber.setText("");
                        editTextDate2.setText("");
                        cbCreated.setChecked(false);
                        cbProcessing.setChecked(false);
                        cbProcessed.setChecked(false);
                        cbDispatched.setChecked(false);
                        cbDelivered.setChecked(false);
                        break;
                    case "Display by range of dates":
                        editTextDate1.setVisibility(View.VISIBLE);
                        datePicker1.setVisibility(View.VISIBLE);
                        eTxtSalesDocumentNumber.setVisibility(View.GONE);
                        eTxtCustomerNumber.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.VISIBLE);
                        datePicker2.setVisibility(View.VISIBLE);
                        spinnerCustomer.setVisibility(View.GONE);
                        cbCreated.setVisibility(View.GONE);
                        cbProcessing.setVisibility(View.GONE);
                        cbProcessed.setVisibility(View.GONE);
                        cbDispatched.setVisibility(View.GONE);
                        cbDelivered.setVisibility(View.GONE);
                        eTxtCustomerNumber.setText("");
                        eTxtSalesDocumentNumber.setText("");
                        cbCreated.setChecked(false);
                        cbProcessing.setChecked(false);
                        cbProcessed.setChecked(false);
                        cbDispatched.setChecked(false);
                        cbDelivered.setChecked(false);

                        break;
                    case "Display by order status":
                        cbCreated.setVisibility(View.VISIBLE);
                        cbProcessing.setVisibility(View.VISIBLE);
                        cbProcessed.setVisibility(View.VISIBLE);
                        cbDispatched.setVisibility(View.VISIBLE);
                        cbDelivered.setVisibility(View.VISIBLE);
                        editTextDate1.setVisibility(View.GONE);
                        datePicker1.setVisibility(View.GONE);
                        eTxtSalesDocumentNumber.setVisibility(View.GONE);
                        eTxtCustomerNumber.setVisibility(View.GONE);
                        editTextDate2.setVisibility(View.GONE);
                        datePicker2.setVisibility(View.GONE);
                        spinnerCustomer.setVisibility(View.GONE);
                        eTxtCustomerNumber.setText("");
                        eTxtSalesDocumentNumber.setText("");

                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        datePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivitySalesOrderList.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        datePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(ActivitySalesOrderList.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        initRecyclerView();
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (choiceStr) {
                    case "No filters selected":
                        url = Constants.URL_FILTER_SALES_ORDERS;
                        break;
                    case "Display by customer":
                        if (TextUtils.isEmpty(eTxtCustomerNumber.getText().toString())) {
                            eTxtCustomerNumber.setError("Please Select the Customer");
                            return;
                        } else {
                            customer = eTxtCustomerNumber.getText().toString();
                        }
                        url = Constants.URL_FILTER_SALES_ORDERS_BY_CUSTOMER;
                        break;
                    case "Display by sales document number":
                        if (TextUtils.isEmpty(eTxtSalesDocumentNumber.getText().toString())) {
                            eTxtSalesDocumentNumber.setError("Enter Valid Bill Number");
                            return;
                        } else {
                            salesDocNo = eTxtSalesDocumentNumber.getText().toString();
                        }
                        url = Constants.URL_FILTER_SALES_ORDERS_BY_DOC;

                        break;
                    case "Display by date":
                        if (TextUtils.isEmpty(editTextDate1.getText().toString())) {
                            editTextDate1.setError("Select Date");
                            return;
                        } else {
                            dateStr = editTextDate1.getText().toString();
                        }
                        url = Constants.URL_FILTER_SALES_ORDERS_BY_ONE_DATE;
                        break;
                    case "Display by range of dates":

                        if (TextUtils.isEmpty(editTextDate1.getText().toString())) {
                            editTextDate1.setError("Select Date");
                            return;
                        } else {
                            dateStr = editTextDate1.getText().toString();
                        }
                        if (TextUtils.isEmpty(editTextDate2.getText().toString())) {
                            editTextDate2.setError("Select Date");
                            return;
                        } else {
                            dateStr2 = editTextDate2.getText().toString();
                        }
                        url = Constants.URL_FILTER_SALES_ORDERS_BY_TWO_DATES;

                        break;
                    case "Display by order status":

                        if (!cbCreated.isChecked() && !cbProcessing.isChecked() && !cbProcessed.isChecked() && !cbDispatched.isChecked() && !cbDelivered.isChecked()) {
                            Toast.makeText(ActivitySalesOrderList.this, "You must checkmark atleast one status!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    /*else
                    {

                    }*/
                        url = Constants.URL_FILTER_SALES_ORDERS_BY_STATUS;

                        break;
                }
                retrieveRecords();
                //clearFields();

            }
        });
    }


    //custom
    void initRecyclerView(){

        recyclerView =  findViewById(R.id.recyclerViewSalesOrdersList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        adapter = new CustomAdapterSalesOrdersList(data);
    }
    //Custom end




    void retrieveRecords(){
        progressBar.setVisibility(View.VISIBLE);
        llChoice.setVisibility(View.GONE);
        llResults.setVisibility(View.VISIBLE);
        stringRequest1 = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            int c;
                            long sdn;
                            String d;
                            double p;
                            String os;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("sales_orders_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    sdn = jObj.getLong("sales_doc_no");
                                    c = jObj.getInt("Customer");
                                    d = jObj.getString("date_of_order");
                                    p = jObj.getDouble("bill_price");
                                    os = jObj.getString("order_status");
                                    SalesOrder so = new SalesOrder(sdn, c, d, p, os);
                                    data.add(so);
                                }
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
                            }else{
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
            HashMap<String,String> map = new HashMap<>();
            if(!eTxtCustomerNumber.getText().toString().isEmpty() && eTxtSalesDocumentNumber.getText().toString().isEmpty() && editTextDate1.getText().toString().isEmpty() && editTextDate2.getText().toString().isEmpty()){
                map.put("customer", customer);
            }
            else if(eTxtCustomerNumber.getText().toString().isEmpty() && !eTxtSalesDocumentNumber.getText().toString().isEmpty() && editTextDate1.getText().toString().isEmpty() && editTextDate2.getText().toString().isEmpty())
            {
                map.put("sales_doc_no", salesDocNo);
            }
            else if(eTxtCustomerNumber.getText().toString().isEmpty() && eTxtSalesDocumentNumber.getText().toString().isEmpty() && !editTextDate1.getText().toString().isEmpty() && editTextDate2.getText().toString().isEmpty())
            {
                map.put("date_of_order1", dateStr);
            }
            else if(eTxtCustomerNumber.getText().toString().isEmpty() && eTxtSalesDocumentNumber.getText().toString().isEmpty() && !editTextDate1.getText().toString().isEmpty() && !editTextDate2.getText().toString().isEmpty())
            {
                map.put("date_of_order1", dateStr);
                map.put("date_of_order2", dateStr2);
            }
            else if((cbCreated.isChecked()||cbProcessing.isChecked()||cbDelivered.isChecked()||cbProcessed.isChecked()||cbDispatched.isChecked()) && (eTxtCustomerNumber.getText().toString().isEmpty() && eTxtSalesDocumentNumber.getText().toString().isEmpty() && editTextDate1.getText().toString().isEmpty() && editTextDate2.getText().toString().isEmpty())){
                map.put("string_condition",strCreated+strProcessing+strProcessed+strDispatched+strDelivered);
            }
            return map;
        }
        }
        ;
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest1);
    }


    void retrieveCustomers(){
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, Constants.URL_RETRIEVE_CUSTOMERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            int c;
                            String n;
                            if(success == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("customers");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jObj = jsonArray.getJSONObject(i);
                                    c = jObj.getInt("Customer_id");
                                    n = jObj.getString("Name");
                                    adapterCustomer.add(Integer.toString(c) +" - "+ n);
                                }
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),"None Found",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    //DatePicker — each picker has its own Calendar to avoid cross-contamination.
    final Calendar calendar1 = Calendar.getInstance();
    final Calendar calendar2 = Calendar.getInstance();
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    DatePickerDialog.OnDateSetListener date1 = (view, year, monthOfYear, dayOfMonth) -> {
        calendar1.set(Calendar.YEAR, year);
        calendar1.set(Calendar.MONTH, monthOfYear);
        calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        editTextDate1.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar1.getTime()));
    };

    DatePickerDialog.OnDateSetListener date2 = (view, year, monthOfYear, dayOfMonth) -> {
        calendar2.set(Calendar.YEAR, year);
        calendar2.set(Calendar.MONTH, monthOfYear);
        calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        editTextDate2.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(calendar2.getTime()));
    };
    //Date Picker end

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivitySalesOrderList.this);
        builder1.setTitle("BACK!\n\n");
        builder1.setMessage("Where do you want to go back?");
        builder1.setPositiveButton("Home Screen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ActivitySalesOrderList.this, MainActivity.class));
            }
        });
        builder1.setNegativeButton("Sales Order Initial Screen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                llResults.setVisibility(View.GONE);
                llChoice.setVisibility(View.VISIBLE);
            }
        });
        AlertDialog dialog = builder1.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
        Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bNeg.setTextColor(ContextCompat.getColor(this, R.color.splashback));
        Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bPos.setTextColor(ContextCompat.getColor(this, R.color.splashback));
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        int id = view.getId();
        if (id == R.id.checkBoxCreated) {
            strCreated = checked ? " order_status = 'Created' OR " : "";
        } else if (id == R.id.checkBoxProcessing) {
            strProcessing = checked ? " order_status = 'Processing' OR " : "";
        } else if (id == R.id.checkBoxProcessed) {
            strProcessed = checked ? " order_status = 'Processed' OR " : "";
        } else if (id == R.id.checkBoxDispatched) {
            strDispatched = checked ? " order_status = 'Dispatched' OR " : "";
        } else if (id == R.id.checkBoxDelivered) {
            strDelivered = checked ? " order_status = 'Delivered' OR " : "";
        }
    }

}