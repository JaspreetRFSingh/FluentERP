package com.jstech.fluenterp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.SalesOrder;

import java.util.ArrayList;

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

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewSalesOrderNumber = itemView.findViewById(R.id.textViewSalesDocumentNumber);
            this.textViewCustomer = itemView.findViewById(R.id.textViewCustomerNumber);
            this.textViewDateOfOrder = itemView.findViewById(R.id.textViewDate);
            this.textViewPrice = itemView.findViewById(R.id.textViewOrderPrice);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewSalesOrderNumber = holder.textViewSalesOrderNumber;
        TextView textViewCustomerNumber = holder.textViewCustomer;
        TextView textViewOrderDate = holder.textViewDateOfOrder;
        TextView textViewOrderPrice = holder.textViewPrice;
        textViewSalesOrderNumber.setText(String.valueOf(dataSet.get(position).getSalesDocNumber()));
        textViewCustomerNumber.setText(String.valueOf(dataSet.get(position).getCustomerNumber()));
        textViewOrderDate.setText(String.valueOf(dataSet.get(position).getOrderDate()));
        textViewOrderPrice.setText(String.valueOf(dataSet.get(position).getOrderPrice()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
