package com.jstech.fluenterp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
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
import com.jstech.fluenterp.misc.ReportsActivity;
import com.jstech.fluenterp.misc.RequestAccountCredentialsActivity;
import com.jstech.fluenterp.misc.ServerActivity;
import com.jstech.fluenterp.misc.TCodeHelpActivity;
import com.jstech.fluenterp.mm.ActivityCurrentStock;
import com.jstech.fluenterp.mm.ActivityDisplayMaterialsList;
import com.jstech.fluenterp.mm.ActivityMaterialCreate;
import com.jstech.fluenterp.mm.ActivityMaterialDisplay;
import com.jstech.fluenterp.mm.ActivityMaterialModify;
import com.jstech.fluenterp.models.DataModel;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderCreate;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderDisplay;
import com.jstech.fluenterp.purchasing.ActivityPurchaseOrderModify;
import com.jstech.fluenterp.sd.ActivityCreateQuotation;
import com.jstech.fluenterp.sd.ActivityDisplayQuotation;
import com.jstech.fluenterp.sd.ActivityModifyQuotation;
import com.jstech.fluenterp.sd.ActivitySalesOrderCreate;
import com.jstech.fluenterp.sd.ActivitySalesOrderDisplay;
import com.jstech.fluenterp.sd.ActivitySalesOrderList;
import com.jstech.fluenterp.sd.ActivitySalesOrderModify;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    ArrayList arrayList;
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList();
        arrayList.add(new DataModel("Graphical Analysis", R.drawable.graph64, "#ffffff"));
        arrayList.add(new DataModel("Reports", R.drawable.report64, "#ffffff"));
        arrayList.add(new DataModel("Request Account Credentials", R.drawable.account64, "#ffffff"));
        arrayList.add(new DataModel("T-Code Help", R.drawable.help64, "#ffffff"));
        arrayList.add(new DataModel("Server", R.drawable.server64, "#ffffff"));
        arrayList.add(new DataModel("About", R.drawable.about64, "#ffffff"));

        MainActivityRecyclerViewAdapter adapter = new MainActivityRecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableList.setIndicatorBounds(expandableList.getRight()- 80, expandableList.getWidth());
        } else {
            expandableList.setIndicatorBoundsRelative(expandableList.getRight()- 80, expandableList.getWidth());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }

        setContentView(R.layout.activity_main);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawer_layout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setupToolbar();
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
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView,
                                        View view,
                                        int groupPosition,
                                        int childPosition, long id) {

                if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Sales Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivitySalesOrderCreate.class);
                    startActivity(intent);

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Sales Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivitySalesOrderModify.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Sales Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivitySalesOrderDisplay.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Sales Orders List"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivitySalesOrderList.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Quotation"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCreateQuotation.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Quotation"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityModifyQuotation.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Quotation"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayQuotation.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Material"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityMaterialCreate.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Material"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityMaterialModify.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Material"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityMaterialDisplay.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Materials List"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayMaterialsList.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Current Stock"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCurrentStock.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Purchase Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerCreate.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Purchase Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerModify.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Purchase Order"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerDisplay.class);
                    startActivity(intent);
                }

                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display List of Employees"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Attendance Record"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Employee Salary Schema"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Employee Bonuses and Incentives"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
                    startActivity(intent);
                }

                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Dispatch Incoming Orders"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Check Order Status"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCheckOrderStatus.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Employee"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityEmployeeCreate.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Employee"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityEmployeeModify.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Employee"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityEmployeeDisplay.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Customer"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerCreate.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Customer"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerModify.class);
                    startActivity(intent);
                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Customer"))
                {
                    Intent intent = new Intent(MainActivity.this, ActivityCustomerDisplay.class);
                    startActivity(intent);
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
        strTCode = eTxtTCode.getText().toString().trim();
        if(strTCode.equals("va01") || strTCode.equals("VA01")){
            Intent intent = new Intent(MainActivity.this, ActivitySalesOrderCreate.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va02") || strTCode.equals("VA02")){
            Intent intent = new Intent(MainActivity.this, ActivitySalesOrderModify.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va03") || strTCode.equals("VA03")){
            Intent intent = new Intent(MainActivity.this, ActivitySalesOrderDisplay.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va05") || strTCode.equals("VA05")){
            Intent intent = new Intent(MainActivity.this, ActivitySalesOrderList.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va11") || strTCode.equals("VA11")){
            Intent intent = new Intent(MainActivity.this, ActivityCreateQuotation.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va12") || strTCode.equals("VA12")){
            Intent intent = new Intent(MainActivity.this, ActivityModifyQuotation.class);
            startActivity(intent);
        }
        else if(strTCode.equals("va13") || strTCode.equals("VA13")){
            Intent intent = new Intent(MainActivity.this, ActivityDisplayQuotation.class);
            startActivity(intent);
        }
        else if(strTCode.equals("mm01") || strTCode.equals("MM01")){
            Intent intent = new Intent(MainActivity.this, ActivityMaterialCreate.class);
            startActivity(intent);
        }
        else if(strTCode.equals("mm02") || strTCode.equals("MM02")){
            Intent intent = new Intent(MainActivity.this, ActivityMaterialModify.class);
            startActivity(intent);
        }
        else if(strTCode.equals("mm03") || strTCode.equals("MM03")){
            Intent intent = new Intent(MainActivity.this, ActivityMaterialDisplay.class);
            startActivity(intent);
        }
        else if(strTCode.equals("mm04") || strTCode.equals("MM04")){
            Intent intent = new Intent(MainActivity.this, ActivityDisplayMaterialsList.class);
            startActivity(intent);
        }
        else if(strTCode.equals("mm12") || strTCode.equals("MM12")){
            Intent intent = new Intent(MainActivity.this, ActivityCurrentStock.class);
            startActivity(intent);
        }
        else if(strTCode.equals("pp01") || strTCode.equals("PP01")){
            Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderCreate.class);
            startActivity(intent);
        }
        else if(strTCode.equals("pp02") || strTCode.equals("PP02")){
            Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderModify.class);
            startActivity(intent);
        }
        else if(strTCode.equals("pp03") || strTCode.equals("PP03")){
            Intent intent = new Intent(MainActivity.this, ActivityPurchaseOrderDisplay.class);
            startActivity(intent);
        }
        else if(strTCode.equals("hr10") || strTCode.equals("HR10")){
            Intent intent = new Intent(MainActivity.this, ActivityDisplayEmployeeList.class);
            startActivity(intent);
        }
        else if(strTCode.equals("hr05") || strTCode.equals("HR05")){

        }
        else if(strTCode.equals("hr15") || strTCode.equals("HR15")){

        }
        else if(strTCode.equals("hr25") || strTCode.equals("HR25")){

        }
        else if(strTCode.equals("dd01") || strTCode.equals("DD01")){

        }
        else if(strTCode.equals("dd02") || strTCode.equals("DD02")){
            Intent intent = new Intent(MainActivity.this, ActivityCheckOrderStatus.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md01") || strTCode.equals("MD01")){
            Intent intent = new Intent(MainActivity.this, ActivityCustomerCreate.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md02") || strTCode.equals("MD02")){
            Intent intent = new Intent(MainActivity.this, ActivityCustomerModify.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md03") || strTCode.equals("MD03")){
            Intent intent = new Intent(MainActivity.this, ActivityCustomerDisplay.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md11") || strTCode.equals("MD11")){
            Intent intent = new Intent(MainActivity.this, ActivityEmployeeCreate.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md12") || strTCode.equals("MD12")){
            Intent intent = new Intent(MainActivity.this, ActivityEmployeeModify.class);
            startActivity(intent);
        }
        else if(strTCode.equals("md13") || strTCode.equals("MD13")){
            Intent intent = new Intent(MainActivity.this, ActivityEmployeeDisplay.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Invalid T-Code!", Toast.LENGTH_LONG).show();
        }

    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" FLUENT ERP");
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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding data header
        listDataHeader.add("Sales & Distribution");
        listDataHeader.add("Material Management");
        listDataHeader.add("Purchasing");
        listDataHeader.add("Human Resource Management");
        listDataHeader.add("Dispatch & Delivery");
        listDataHeader.add("Manage Master Data");

        List<String> headingSD = new ArrayList<String>();
        headingSD.add("Create Sales Order");
        headingSD.add("Change Sales Order");
        headingSD.add("Display Sales Order");
        headingSD.add("Display Sales Orders List");
        headingSD.add("Create Quotation");
        headingSD.add("Change Quotation");
        headingSD.add("Display Quotation");

        /*List<String> headingCO = new ArrayList<String>();
        headingCO.add("Create Material Cost Estimate");
        headingCO.add("Change Material Cost Estimate");
        headingCO.add("Display Material Cost Estimate");
        headingCO.add("Price Change");*/

        /*List<String> headingPO = new ArrayList<String>();
        headingPO.add("Display Products List");
        headingPO.add("Display Purchase Orders' Production");*/

        List<String> headingMM = new ArrayList<String>();
        headingMM.add("Create Material");
        headingMM.add("Change Material");
        headingMM.add("Display Material");
        headingMM.add("Display Materials List");
        headingMM.add("Current Stock");

        List<String> headingPUR = new ArrayList<String>();
        headingPUR.add("Create Purchase Order");
        headingPUR.add("Change Purchase Order");
        headingPUR.add("Display Purchase Order");

        List<String> headingHR = new ArrayList<String>();
        headingHR.add("Display List of Employees");
        headingHR.add("Attendance Record");
        headingHR.add("Display Employee Salary Schema");
        headingHR.add("Employee Bonuses and Incentives");

        List<String> headingDD = new ArrayList<String>();
        headingDD.add("Dispatch Incoming Orders");
        headingDD.add("Check Order Status");

        List<String> headingMD = new ArrayList<String>();
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
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
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
        if(itemName.equals("Graphical Analysis")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("CHOOSE\n\n");
            builder.setMessage("Please choose an option for Sales Analysis");
            builder.setPositiveButton("Region-wise", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, GraphicalAnalysisActivity.class);
                    intent.putExtra("mode",1);
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
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeModified;
            dialog.show();
        }
        else if(itemName.equals("Reports")){
            Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
            startActivity(intent);
        }
        else if(itemName.equals("Server")){
            Intent intent = new Intent(MainActivity.this, ServerActivity.class);
            startActivity(intent);
        }
        else if(itemName.equals("About")){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        else if(itemName.equals("Request Account Credentials")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("CHOOSE\n\n");
            builder.setMessage("Please choose an option!");
            builder.setPositiveButton("Request New Account Credentials", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, RequestAccountCredentialsActivity.class);
                    intent.putExtra("mode",1);
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
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeModified;
            dialog.show();
        }
        else if(itemName.equals("T-Code Help")){
            Intent intent = new Intent(MainActivity.this, TCodeHelpActivity.class);
            startActivity(intent);
        }
        else{
        }
    }
}