package com.jstech.fluenterp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jstech.fluenterp.adapters.ExpandableListAdapter;
import com.jstech.fluenterp.adapters.MainActivityRecyclerViewAdapter;
import com.jstech.fluenterp.dd.ActivityCheckOrderStatus;
import com.jstech.fluenterp.hr.ActivityDisplayEmployeeList;
import com.jstech.fluenterp.masterdata.ActivityCustomerCreate;
import com.jstech.fluenterp.masterdata.ActivityCustomerDisplay;
import com.jstech.fluenterp.masterdata.ActivityCustomerModify;
import com.jstech.fluenterp.masterdata.ActivityEmployeeCreate;
import com.jstech.fluenterp.masterdata.ActivityEmployeeDisplay;
import com.jstech.fluenterp.masterdata.ActivityEmployeeModify;
import com.jstech.fluenterp.misc.AboutActivity;
import com.jstech.fluenterp.misc.GraphicalAnalysisActivity;
import com.jstech.fluenterp.misc.RequestAccountCredentialsActivity;
import com.jstech.fluenterp.misc.ServerActivity;
import com.jstech.fluenterp.misc.SettingsActivity;
import com.jstech.fluenterp.misc.TCodeHelpActivity;
import com.jstech.fluenterp.mm.ActivityCurrentStock;
import com.jstech.fluenterp.mm.ActivityDisplayMaterialsList;
import com.jstech.fluenterp.mm.ActivityMaterialCreate;
import com.jstech.fluenterp.mm.ActivityMaterialDisplay;
import com.jstech.fluenterp.mm.ActivityMaterialModify;
import com.jstech.fluenterp.models.DataModel;
import com.jstech.fluenterp.purchasing.ActivityDisplaySellers;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderCreate;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderDisplay;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderModify;
import com.jstech.fluenterp.purchasing.ChangePurchaseOrderStatus;
import com.jstech.fluenterp.sd.ActivitySalesOrderCreate;
import com.jstech.fluenterp.sd.ActivitySalesOrderDisplay;
import com.jstech.fluenterp.sd.ActivitySalesOrderList;
import com.jstech.fluenterp.sd.ActivitySalesOrderModify;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityRecyclerViewAdapter.ItemListener {


    private FlowingDrawer mDrawer;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    //
    EditText eTxtTCode;
    Button btnTCode;
    String strTCode;
    //

    //Main Activity Content
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    ImageView imgLogo;
    TextView txtVisitWebsite;
    TextView txtExplore;
    TextView txtExpand;

    void initMainContent(){
        imgLogo = findViewById(R.id.imgLogo);
        eTxtTCode = findViewById(R.id.editTextEnterTCode);
        btnTCode = findViewById(R.id.btnEnterTcode);
        txtExplore = findViewById(R.id.txtExplore);
        txtExpand = findViewById(R.id.txtCommandLine);
        txtVisitWebsite = findViewById(R.id.txtVisitWebsite);
        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("Graphical Analysis", R.drawable.graph64, "#ffffff"));
        arrayList.add(new DataModel("Reports", R.drawable.report64, "#ffffff"));
        arrayList.add(new DataModel("Account Credentials", R.drawable.account64, "#ffffff"));
        arrayList.add(new DataModel("T-Code Help", R.drawable.help64, "#ffffff"));
        arrayList.add(new DataModel("Server", R.drawable.server64, "#ffffff"));
        arrayList.add(new DataModel("About", R.drawable.about64, "#ffffff"));

        MainActivityRecyclerViewAdapter adapter = new MainActivityRecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expandableList.setIndicatorBoundsRelative(expandableList.getRight()- 80, expandableList.getWidth());
    }

    boolean isConnection(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else {
            connected = false;
        }
        return connected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //noinspection deprecation
        window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));

        setContentView(R.layout.activity_main);
        mDrawer = findViewById(R.id.drawer_layout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setupToolbar();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                                101);
                    }
        }


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                101);
                    }
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                101);
                    }
        }

        //Activity
        initMainContent();
        strTCode = "";
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        imgLogo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce));
        eTxtTCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left));
        btnTCode.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
        txtExplore.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://herocycles.com/")));
            }
        });
        txtVisitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://herocycles.com/")));
            }
        });
        txtExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTCode.setVisibility(View.VISIBLE);
                eTxtTCode.setVisibility(View.VISIBLE);
                txtExpand.setVisibility(View.GONE);
            }
        });
        //Activity
        expandableList = findViewById(R.id.navigationmenu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        setupDrawerContent(navigationView);
        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);
        if(!isConnection()){
            Snackbar.make( findViewById(R.id.content), "It seems you are not connected to the network!", Snackbar.LENGTH_LONG).show();

        }
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView,
                                        View view,
                                        int groupPosition,
                                        int childPosition, long id) {

                switch (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)) {
                    case "Create Sales Order": {
                        Intent intent = new Intent(MainActivity.this, ActivitySalesOrderCreate.class);
                        startActivity(intent);

                        break;
                    }
                    case "Change Sales Order": {
                        Intent intent = new Intent(MainActivity.this, ActivitySalesOrderModify.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Sales Order": {
                        Intent intent = new Intent(MainActivity.this, ActivitySalesOrderDisplay.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Sales Orders List": {
                        Intent intent = new Intent(MainActivity.this, ActivitySalesOrderList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Create Material": {
                        Intent intent = new Intent(MainActivity.this, ActivityMaterialCreate.class);
                        startActivity(intent);
                        break;
                    }
                    case "Change Material": {
                        Intent intent = new Intent(MainActivity.this, ActivityMaterialModify.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Material": {
                        Intent intent = new Intent(MainActivity.this, ActivityMaterialDisplay.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Materials List": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplayMaterialsList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Current Stock": {
                        Intent intent = new Intent(MainActivity.this, ActivityCurrentStock.class);
                        startActivity(intent);
                        break;
                    }
                    case "Create Purchase Order": {
                        Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderCreate.class);
                        startActivity(intent);
                        break;
                    }
                    case "Change Purchase Order": {
                        Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderModify.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Purchase Order": {
                        Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderDisplay.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display List of Sellers": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplaySellers.class);
                        startActivity(intent);
                        break;
                    }
                    case "Change Purchase Order Status":{
                        Intent intent = new Intent(MainActivity.this, ChangePurchaseOrderStatus.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display List of Employees": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Attendance Record": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Employee Salary Schema": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Employee Bonuses and Incentives": {
                        Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                        startActivity(intent);
                        break;
                    }
                    case "Dispatch Incoming Orders":

                        break;
                    case "Check Order Status": {
                        Intent intent = new Intent(MainActivity.this, ActivityCheckOrderStatus.class);
                        startActivity(intent);
                        break;
                    }
                    case "Create Employee": {
                        Intent intent = new Intent(MainActivity.this, ActivityEmployeeCreate.class);
                        startActivity(intent);
                        break;
                    }
                    case "Change Employee": {
                        Intent intent = new Intent(MainActivity.this, ActivityEmployeeModify.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Employee": {
                        Intent intent = new Intent(MainActivity.this, ActivityEmployeeDisplay.class);
                        startActivity(intent);
                        break;
                    }
                    case "Create Customer": {
                        Intent intent = new Intent(MainActivity.this, ActivityCustomerCreate.class);
                        startActivity(intent);
                        break;
                    }
                    case "Change Customer": {
                        Intent intent = new Intent(MainActivity.this, ActivityCustomerModify.class);
                        startActivity(intent);
                        break;
                    }
                    case "Display Customer": {
                        Intent intent = new Intent(MainActivity.this, ActivityCustomerDisplay.class);
                        startActivity(intent);
                        break;
                    }
                }
                //Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " -> " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();
                mDrawer.closeMenu();
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");
                return false;
            }
        });

        //
        btnTCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inspectTCode();
            }
        });
        //
    }

    void inspectTCode(){
        strTCode = eTxtTCode.getText().toString().toLowerCase().trim();
        switch (strTCode) {
            case "va01": {
                Intent intent = new Intent(MainActivity.this, ActivitySalesOrderCreate.class);
                startActivity(intent);
                break;
            }
            case "va02": {
                Intent intent = new Intent(MainActivity.this, ActivitySalesOrderModify.class);
                startActivity(intent);
                break;
            }
            case "va03": {
                Intent intent = new Intent(MainActivity.this, ActivitySalesOrderDisplay.class);
                startActivity(intent);
                break;
            }
            case "va05": {
                Intent intent = new Intent(MainActivity.this, ActivitySalesOrderList.class);
                startActivity(intent);
                break;
            }
            case "mm01": {
                Intent intent = new Intent(MainActivity.this, ActivityMaterialCreate.class);
                startActivity(intent);
                break;
            }
            case "mm02": {
                Intent intent = new Intent(MainActivity.this, ActivityMaterialModify.class);
                startActivity(intent);
                break;
            }
            case "mm03": {
                Intent intent = new Intent(MainActivity.this, ActivityMaterialDisplay.class);
                startActivity(intent);
                break;
            }
            case "mm04": {
                Intent intent = new Intent(MainActivity.this, ActivityDisplayMaterialsList.class);
                startActivity(intent);
                break;
            }
            case "mm12": {
                Intent intent = new Intent(MainActivity.this, ActivityCurrentStock.class);
                startActivity(intent);
                break;
            }
            case "pp01": {
                Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderCreate.class);
                startActivity(intent);
                break;
            }
            case "pp02": {
                Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderModify.class);
                startActivity(intent);
                break;
            }
            case "pp03": {
                Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderDisplay.class);
                startActivity(intent);
                break;
            }
            case "pp10": {
                Intent intent = new Intent(MainActivity.this, ActivityDisplaySellers.class);
                startActivity(intent);
                break;
            }
            case "pp05": {
                Intent intent = new Intent(MainActivity.this, ChangePurchaseOrderStatus.class);
                startActivity(intent);
                break;
            }
            case "hr10": {
                Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                startActivity(intent);
                break;
            }
            case "hr05": {
                Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                startActivity(intent);
                break;
            }
            case "hr15": {

                Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                startActivity(intent);
                break;
            }
            case "hr25": {

                Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                startActivity(intent);
                break;
            }
            case "dd01": {
                Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                startActivity(intent);
                break;
            }
            case "dd02": {
                Intent intent = new Intent(MainActivity.this, ActivityCheckOrderStatus.class);
                startActivity(intent);
                break;
            }
            case "md01": {
                Intent intent = new Intent(MainActivity.this, ActivityCustomerCreate.class);
                startActivity(intent);
                break;
            }
            case "md02": {
                Intent intent = new Intent(MainActivity.this, ActivityCustomerModify.class);
                startActivity(intent);
                break;
            }
            case "md03": {
                Intent intent = new Intent(MainActivity.this, ActivityCustomerDisplay.class);
                startActivity(intent);
                break;
            }
            case "md11": {
                Intent intent = new Intent(MainActivity.this, ActivityEmployeeCreate.class);
                startActivity(intent);
                break;
            }
            case "md12": {
                Intent intent = new Intent(MainActivity.this, ActivityEmployeeModify.class);
                startActivity(intent);
                break;
            }
            case "md13": {
                Intent intent = new Intent(MainActivity.this, ActivityEmployeeDisplay.class);
                startActivity(intent);
                break;
            }
            default:
                Toast.makeText(this, "Invalid T-Code!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" FluentERP");
        toolbar.setLogo(R.drawable.ic_applogo);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding data header
        listDataHeader.add("Sales & Distribution");
        listDataHeader.add("Material Management");
        listDataHeader.add("Purchasing");
        listDataHeader.add("Human Resource Management");
        listDataHeader.add("Dispatch & Delivery");
        listDataHeader.add("Manage Master Data");

        List<String> headingSD = new ArrayList<>();
        headingSD.add("Create Sales Order");
        headingSD.add("Change Sales Order");
        headingSD.add("Display Sales Order");
        headingSD.add("Display Sales Orders List");

        /*List<String> headingCO = new ArrayList<String>();
        headingCO.add("Create Material Cost Estimate");
        headingCO.add("Change Material Cost Estimate");
        headingCO.add("Display Material Cost Estimate");
        headingCO.add("Price Change");*/

        /*List<String> headingPO = new ArrayList<String>();
        headingPO.add("Display Products List");
        headingPO.add("Display Purchase Orders' Production");*/

        List<String> headingMM = new ArrayList<>();
        headingMM.add("Create Material");
        headingMM.add("Change Material");
        headingMM.add("Display Material");
        headingMM.add("Display Materials List");
        headingMM.add("Current Stock");

        List<String> headingPUR = new ArrayList<>();
        headingPUR.add("Create Purchase Order");
        headingPUR.add("Change Purchase Order");
        headingPUR.add("Display Purchase Order");
        headingPUR.add("Display List of Sellers");
        headingPUR.add("Change Purchase Order Status");

        List<String> headingHR = new ArrayList<>();
        headingHR.add("Display List of Employees");
        headingHR.add("Attendance Record");
        headingHR.add("Display Employee Salary Schema");
        headingHR.add("Employee Bonuses and Incentives");

        List<String> headingDD = new ArrayList<>();
        headingDD.add("Dispatch Incoming Orders");
        headingDD.add("Check Order Status");

        List<String> headingMD = new ArrayList<>();
        headingMD.add("Create Employee");
        headingMD.add("Change Employee");
        headingMD.add("Display Employee");
        headingMD.add("Create Customer");
        headingMD.add("Change Customer");
        headingMD.add("Display Customer");

        listDataChild.put(listDataHeader.get(0), headingSD);
        listDataChild.put(listDataHeader.get(1), headingMM);
        listDataChild.put(listDataHeader.get(2), headingPUR);
        listDataChild.put(listDataHeader.get(3), headingHR);
        listDataChild.put(listDataHeader.get(4), headingDD);
        listDataChild.put(listDataHeader.get(5), headingMD);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        //mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

   /* @Override
    public void onBackPressed() {
        *//*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*//*
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        //int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            if (doubleBackToExitPressed == 2) {
                finishAffinity();
                System.exit(0);
            }
            else {
                doubleBackToExitPressed++;
                Snackbar.make( findViewById(R.id.content), "Press Back again to exit!", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressed=1;
                }
            }, 2000);
        }
    }

    @Override
    public void onItemClick(DataModel item) {
        String itemName = item.text;
        switch (itemName) {
            case "Graphical Analysis": {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("CHOOSE\n\n");
                builder.setMessage("Please choose an option for Sales Analysis");
                builder.setPositiveButton("Region-wise", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, GraphicalAnalysisActivity.class);
                        intent.putExtra("mode", 1);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Month-wise", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, GraphicalAnalysisActivity.class);
                        intent.putExtra("mode", 2);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
                Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                //noinspection deprecation
                bNeg.setTextColor(getResources().getColor(R.color.splashback));
                Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                //noinspection deprecation
                bPos.setTextColor(getResources().getColor(R.color.splashback));
                break;
            }
            case "Reports": {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Report Generation Options Prompt");
                String[] options = {"Customer-wise", "Material-wise", "Price-wise"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createCustomerReport.php");
                            startActivity(intent);
                        }
                        else if(which == 1){
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createMaterialReport.php");
                            startActivity(intent);
                        }
                        else if(which == 2){
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("url", "http://docs.google.com/gview?embedded=true&url=https://jaspreettechnologies.000webhostapp.com/createPriceReport.php");
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
                break;
            }
            case "Server": {
                Intent intent = new Intent(MainActivity.this, ServerActivity.class);
                startActivity(intent);
                break;
            }
            case "About": {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case "Account Credentials": {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("CHOOSE\n\n");
                builder.setMessage("Please choose an option!");
                builder.setPositiveButton("Request New Account Credentials", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, RequestAccountCredentialsActivity.class);
                        intent.putExtra("mode", 1);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Modify Existing Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, RequestAccountCredentialsActivity.class);
                        intent.putExtra("mode", 2);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
                Button bNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                bNeg.setTextColor(getResources().getColor(R.color.splashback));
                Button bPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                bPos.setTextColor(getResources().getColor(R.color.splashback));
                break;
            }
            case "T-Code Help": {
                Intent intent = new Intent(MainActivity.this, TCodeHelpActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}