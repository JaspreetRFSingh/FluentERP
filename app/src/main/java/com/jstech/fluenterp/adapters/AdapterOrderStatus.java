package com.jstech.fluenterp.adapters;

import com.jstech.fluenterp.Constants;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.SalesOrder;
import com.jstech.fluenterp.network.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterOrderStatus extends RecyclerView.Adapter<AdapterOrderStatus.MyViewHolder> {

    private static final String[] ORDER_STATUS_OPTIONS = {
            "Processing", "Processed", "Dispatched", "Delivered"
    };
    private static final String CHANGE_STATUS_URL =
            Constants.URL_CHANGE_ORDER_STATUS;

    private final ArrayList<SalesOrder> dataSet;
    private final ArrayList<SalesOrder> tempList;

    public AdapterOrderStatus(ArrayList<SalesOrder> data) {
        this.dataSet = data;
        this.tempList = new ArrayList<>(data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewSalesOrderNumber;
        final TextView textViewPrice;
        final TextView textViewOrderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSalesOrderNumber = itemView.findViewById(R.id.textViewSalesDocumentNumberCOS);
            textViewPrice = itemView.findViewById(R.id.textViewOrderPriceCOS);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatusCOS);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout_order_status, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        final Context ctx = view.getContext();

        view.setOnClickListener(v -> {
            final String sdn = holder.textViewSalesOrderNumber.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Order Status Change Prompt");
            builder.setItems(ORDER_STATUS_OPTIONS, (dialog, which) ->
                    changeOrderStatus(sdn, ORDER_STATUS_OPTIONS[which], ctx));
            AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeModified;
            dialog.show();
        });

        return holder;
    }

    private void changeOrderStatus(final String salesDocNo, final String orderStatus,
                                   final Context ctx) {
        StringRequest request = new StringRequest(Request.Method.POST, CHANGE_STATUS_URL,
                response -> {
                    try {
                        new JSONObject(response); // validate response is JSON
                        new AlertDialog.Builder(ctx)
                                .setTitle("Order Status Changed")
                                .setMessage("Order Status Changed to " + orderStatus)
                                .create()
                                .show();
                    } catch (Exception e) {
                        Toast.makeText(ctx, "Some Exception: " + e, Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(ctx, "Network Error: " + error, Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("sales_doc_no", salesDocNo);
                map.put("order_status", orderStatus);
                return map;
            }
        };
        VolleySingleton.getInstance(ctx).addToRequestQueue(request);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SalesOrder order = dataSet.get(position);
        holder.textViewSalesOrderNumber.setText(String.valueOf(order.getSalesDocNumber()));
        holder.textViewPrice.setText(String.valueOf(order.getOrderPrice()));
        holder.textViewOrderStatus.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String str) {
        dataSet.clear();
        if (str.isEmpty()) {
            dataSet.addAll(tempList);
        } else {
            for (SalesOrder salesOrder : tempList) {
                if (String.valueOf(salesOrder.getSalesDocNumber()).contains(str)) {
                    dataSet.add(salesOrder);
                }
            }
        }
        notifyDataSetChanged();
    }
}
