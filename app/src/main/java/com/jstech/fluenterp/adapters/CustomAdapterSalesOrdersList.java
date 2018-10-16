package com.jstech.fluenterp.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        final Context ctx = view.getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                final String sdn = myViewHolder.textViewSalesOrderNumber.getText().toString();
                builder.setTitle("Sales Document");
                builder.setMessage("Do you want to generate a PDF for the sales order "+myViewHolder.textViewSalesOrderNumber.getText()+"?\n");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String urlString="http://jaspreettechnologies.000webhostapp.com/createInvoice.php?sales_doc_no="+sdn;
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setPackage("com.android.chrome");
                        try {
                            ctx.startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            // Chrome browser presumably not installed so allow user to choose instead
                            intent.setPackage(null);
                            ctx.startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeModified;
                dialog.show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewSalesOrderNumber = holder.textViewSalesOrderNumber;
        TextView textViewCustomerNumber = holder.textViewCustomer;
        TextView textViewOrderDate = holder.textViewDateOfOrder;
        TextView textViewOrderPrice = holder.textViewPrice;
        TextView textViewOrderStatus = holder.textViewOrderStatus;
        textViewSalesOrderNumber.setText(String.valueOf(dataSet.get(position).getSalesDocNumber()));
        textViewCustomerNumber.setText(String.valueOf(dataSet.get(position).getCustomerNumber()));
        textViewOrderDate.setText(String.valueOf(dataSet.get(position).getOrderDate()));
        textViewOrderPrice.setText(String.valueOf(dataSet.get(position).getOrderPrice()));
        textViewOrderStatus.setText(String.valueOf(dataSet.get(position).getOrderStatus()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




}
