package com.jstech.fluenterp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.jstech.fluenterp.adapters.ExpandableListAdapter;
import com.jstech.fluenterp.masterdata.ActivityCustomerCreate;
import com.jstech.fluenterp.masterdata.ActivityCustomerDisplay;
import com.jstech.fluenterp.sd.ActivitySalesOrderCreate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
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

        /*supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Sales Order"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Material Cost Estimate"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Material Cost Estimate"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Material Cost Estimate"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Material"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Material"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Material"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Employee"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Employee"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Employee"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Create Customer"))
                {
                    Intent intent =new Intent(MainActivity.this, ActivityCustomerCreate.class);
                    startActivity(intent);

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Change Customer"))
                {

                }
                else if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Display Customer"))
                {

                    Intent intent =new Intent(MainActivity.this, ActivityCustomerDisplay.class);
                    startActivity(intent);
                }

                //Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " -> " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();

                mDrawerLayout.closeDrawers();
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

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding data header
        listDataHeader.add("Sales & Distribution");
        listDataHeader.add("Costing");
        listDataHeader.add("Manufacturing & Production");
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

        List<String> headingCO = new ArrayList<String>();
        headingCO.add("Create Material Cost Estimate");
        headingCO.add("Change Material Cost Estimate");
        headingCO.add("Display Material Cost Estimate");
        headingCO.add("Price Change");

        List<String> headingPO = new ArrayList<String>();
        headingPO.add("Display Products List");
        headingPO.add("Display Purchase Orders' Production");

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
        headingHR.add("Create Employee");
        headingHR.add("Change Employee");
        headingHR.add("Display Employee");
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
        listDataChild.put(listDataHeader.get(1), headingCO);
        listDataChild.put(listDataHeader.get(2), headingPO);
        listDataChild.put(listDataHeader.get(3), headingMM);
        listDataChild.put(listDataHeader.get(4), headingPUR);
        listDataChild.put(listDataHeader.get(5), headingHR);
        listDataChild.put(listDataHeader.get(6), headingDD);
        listDataChild.put(listDataHeader.get(7), headingMD);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
}