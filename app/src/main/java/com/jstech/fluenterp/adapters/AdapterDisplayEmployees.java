package com.jstech.fluenterp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Employee;

import java.util.ArrayList;

public class AdapterDisplayEmployees extends RecyclerView.Adapter<AdapterDisplayEmployees.MyViewHolder>{
    ArrayList<Employee> dataSet, tempList;

    public AdapterDisplayEmployees(ArrayList<Employee> data){
        this.dataSet = data;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewEmployeeName;
        TextView textViewEmployeeType;
        TextView textViewEmployeePhone;
        TextView textViewEmployeeAddress;
        TextView textViewEmployeeDob;
        TextView textViewEmployeeDoj;
        TextView textViewEmployeeId;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
            this.textViewEmployeeType = itemView.findViewById(R.id.textViewEmployeeType);
            this.textViewEmployeePhone = itemView.findViewById(R.id.textViewEmployeePhone);
            this.textViewEmployeeAddress = itemView.findViewById(R.id.textViewEmployeeAddress);
            this.textViewEmployeeDob = itemView.findViewById(R.id.textViewEmployeeDob);
            this.textViewEmployeeDoj = itemView.findViewById(R.id.textViewEmployeeDoj);
            this.textViewEmployeeId = itemView.findViewById(R.id.textViewEmployeeId);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_employee, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewEmployeeName = holder.textViewEmployeeName;
        TextView textViewEmployeeType = holder.textViewEmployeeType;
        TextView textViewEmployeePhone = holder.textViewEmployeePhone;
        TextView textViewEmployeeAddress = holder.textViewEmployeeAddress;
        TextView textViewEmployeeDob = holder.textViewEmployeeDob;
        TextView textViewEmployeeDoj = holder.textViewEmployeeDoj;
        TextView textViewEmployeeId = holder.textViewEmployeeId;
        textViewEmployeeName.setText(String.valueOf(dataSet.get(position).getEmpName()));
        textViewEmployeeType.setText(String.valueOf(dataSet.get(position).getEmpType()));
        textViewEmployeePhone.setText(String.valueOf(dataSet.get(position).getEmpPhone()));
        textViewEmployeeAddress.setText(String.valueOf(dataSet.get(position).getEmpAddress()));
        textViewEmployeeDob.setText(String.valueOf(dataSet.get(position).getDob()));
        textViewEmployeeDoj.setText(String.valueOf(dataSet.get(position).getDoj()));
        textViewEmployeeId.setText(String.valueOf(dataSet.get(position).getEmpId()));
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
            for(Employee employee : tempList){
                if(String.valueOf(employee.getEmpName().toLowerCase()).contains(str.toLowerCase())){
                    dataSet.add(employee);
                }
            }
        }
        notifyDataSetChanged();
    }
}