package com.jstech.fluenterp.adapters;

import com.jstech.fluenterp.Constants;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.network.VolleySingleton;
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


    StringRequest stringRequest;
    public static class MyViewHolder extends RecyclerView.ViewHolder {


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
        final String[] ORDER_STATUS_OPTIONS = {"Processing", "Processed", "Dispatched", "Delivered"};
        view.setOnClickListener(v -> {
            final String sdn = myViewHolder.textViewPurchaseOrderNumber.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Order Status Change Prompt");
            builder.setItems(ORDER_STATUS_OPTIONS, (dialog, which) ->
                    changeOrderStatus(sdn, ORDER_STATUS_OPTIONS[which], ctx));
            AlertDialog dialog = builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemeModified;
            dialog.show();
        });
        return myViewHolder;
    }

    void changeOrderStatus(final String purchasedocno, final String orderStatus, final Context ct){
        final String url = Constants.URL_CHANGE_ORDER_STATUS_PURCHASE;
        stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                        builder.setTitle("Order Status Changed");
                        builder.setMessage("Order Status Changed to " + orderStatus);
                        AlertDialog dialog = builder.create();
                        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
                        dialog.show();
                    } catch (Exception e){
                        Toast.makeText(ct,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ct,"Some Error: "+error,Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> map = new HashMap<>();
                map.put("purchase_doc_no", purchasedocno);
                map.put("order_status", orderStatus);
                return map;
            }
        };
        VolleySingleton.getInstance(ct).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewPurchaseOrderNumber.setText(String.valueOf(dataSet.get(position).getPurchaseDocNumber()));
        holder.textViewOrderStatus.setText(String.valueOf(dataSet.get(position).getOrderStatus()));
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