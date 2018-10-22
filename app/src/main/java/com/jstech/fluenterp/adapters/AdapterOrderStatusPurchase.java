package com.jstech.fluenterp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.PurchaseOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterOrderStatusPurchase extends RecyclerView.Adapter<AdapterOrderStatusPurchase.MyViewHolder>{



    ArrayList<PurchaseOrder> dataSet, tempList;

    public AdapterOrderStatusPurchase(ArrayList<PurchaseOrder> data){
        this.dataSet = data;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }


    RequestQueue requestQueue;
    StringRequest stringRequest;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textViewPurchaseOrderNumber;
        TextView textViewOrderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewPurchaseOrderNumber = itemView.findViewById(R.id.textViewPurchaseDocumentNumberCPOS);
            this.textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatusCPOS);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout_purchase_order_status, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        final Context ctx = view.getContext();
        requestQueue = Volley.newRequestQueue(ctx);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] os = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                final String sdn = myViewHolder.textViewPurchaseOrderNumber.getText().toString();
                builder.setTitle("Order Status Change Prompt");
                String[] options = {"Processing", "Processed", "Dispatched", "Delivered"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            os[0] = "Processing";
                        }
                        else if(which == 1){
                            os[0] = "Processed";
                        }
                        else if(which == 2){
                            os[0] = "Dispatched";
                        }
                        else if(which == 3){
                            os[0] = "Delivered";
                        }
                        changeOrderStatus(sdn, os[0],ctx);
                    }
                });
                AlertDialog dialog = builder.create();
                Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });
        return myViewHolder;
    }

    void changeOrderStatus(final String purchasedocno, final String orderStatus, final Context ct){
        final String url = "https://jaspreettechnologies.000webhostapp.com/changeOrderStatusPurchase.php";
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                            builder.setTitle("Order Status Changed");
                            builder.setMessage("Order Status Changed to "+orderStatus);
                            AlertDialog dialog = builder.create();
                            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                            dialog.show();

                        }catch (Exception e){
                            Toast.makeText(ct,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ct,"Some Error: "+error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()  {
                HashMap<String,String> map = new HashMap<>();
                map.put("purchase_doc_no",purchasedocno);
                map.put("order_status", orderStatus);
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewPurchaseOrderNumber = holder.textViewPurchaseOrderNumber;
        TextView textViewOrderStatus = holder.textViewOrderStatus;
        textViewPurchaseOrderNumber.setText(String.valueOf(dataSet.get(position).getPurchaseDocNumber()));
        textViewOrderStatus.setText(String.valueOf(dataSet.get(position).getOrderStatus()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String str){

        dataSet.clear();
        if(str.length()==0){
            dataSet.addAll(tempList);
        }else{
            for(PurchaseOrder purchaseOrder : tempList){
                if(String.valueOf(purchaseOrder.getPurchaseDocNumber()).contains(str.toLowerCase())){
                    dataSet.add(purchaseOrder);
                }
            }
        }
        notifyDataSetChanged();
    }

}