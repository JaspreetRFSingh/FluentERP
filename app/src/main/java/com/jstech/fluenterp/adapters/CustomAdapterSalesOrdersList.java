package com.jstech.fluenterp.adapters;

import com.jstech.fluenterp.Constants;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jstech.fluenterp.R;
import com.jstech.fluenterp.WebViewActivity;
import com.jstech.fluenterp.models.SalesOrder;

import java.util.ArrayList;
import java.util.Objects;

public class CustomAdapterSalesOrdersList extends RecyclerView.Adapter<CustomAdapterSalesOrdersList.MyViewHolder> {

    ArrayList<SalesOrder> dataSet;

    public CustomAdapterSalesOrdersList(ArrayList<SalesOrder> data){
        this.dataSet = data;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCustomer;
        TextView textViewSalesOrderNumber;
        TextView textViewDateOfOrder;
        TextView textViewPrice;
        TextView textViewOrderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewSalesOrderNumber = itemView.findViewById(R.id.textViewSalesDocumentNumber);
            this.textViewCustomer = itemView.findViewById(R.id.textViewCustomerNumber);
            this.textViewDateOfOrder = itemView.findViewById(R.id.textViewDate);
            this.textViewPrice = itemView.findViewById(R.id.textViewOrderPrice);
            this.textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        Context ctx = view.getContext();

        view.setOnClickListener(v -> {
            int pos = myViewHolder.getAdapterPosition();
            if (pos == RecyclerView.NO_ID) return;
            SalesOrder order = dataSet.get(pos);
            String sdn = String.valueOf(order.getSalesDocNumber());

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Sales Document");
            builder.setMessage("Generate a PDF for sales order " + sdn + "?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                String urlString = Constants.GDOCS_VIEWER_PREFIX + Constants.URL_CREATE_INVOICE + "?sales_doc_no=" + sdn;
                Intent intent = new Intent(ctx, WebViewActivity.class);
                intent.putExtra("url", urlString);
                ctx.startActivity(intent);
            });
            builder.setNegativeButton("No", null);
            AlertDialog dialog = builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations =
                    R.style.DialogThemeModified;
            dialog.show();
            int accent = ContextCompat.getColor(ctx, R.color.splashback);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(accent);
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(accent);
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SalesOrder order = dataSet.get(position);
        holder.textViewSalesOrderNumber.setText(String.valueOf(order.getSalesDocNumber()));
        holder.textViewCustomer.setText(String.valueOf(order.getCustomerNumber()));
        holder.textViewDateOfOrder.setText(order.getOrderDate());
        holder.textViewPrice.setText(String.valueOf(order.getOrderPrice()));
        holder.textViewOrderStatus.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




}
