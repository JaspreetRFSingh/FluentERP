package com.jstech.fluenterp.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Seller;

import java.util.ArrayList;

public class AdapterDisplaySellers extends RecyclerView.Adapter<AdapterDisplaySellers.SellerViewHolder> {

    ArrayList<Seller> dataSet, tempList;
    public AdapterDisplaySellers(ArrayList<Seller> data){
        this.dataSet = data;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_seller, parent, false);
        AdapterDisplaySellers.SellerViewHolder myViewHolder = new SellerViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        holder.textViewSellerName.setText(String.valueOf(dataSet.get(position).getsName()));
        holder.textViewSellerCity.setText(String.valueOf(dataSet.get(position).getsCity()));
        holder.textViewSellerPhone.setText(String.valueOf(dataSet.get(position).getsPhone()));
        holder.textViewSellerAddress.setText(String.valueOf(dataSet.get(position).getsAddress()));
        holder.textViewSellerId.setText(String.valueOf(dataSet.get(position).getsId()));
        holder.textViewSellerGST.setText(String.valueOf(dataSet.get(position).getsGST()));
    }

    public void filter(String str){

        dataSet.clear();
        if(str.length()==0){
            dataSet.addAll(tempList);
        }else{
            for(Seller seller : tempList){
                if(String.valueOf(seller.getsName().toLowerCase()).contains(str.toLowerCase())){
                    dataSet.add(seller);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder{
        TextView textViewSellerName;
        TextView textViewSellerCity;
        TextView textViewSellerPhone;
        TextView textViewSellerAddress;
        TextView textViewSellerId;
        TextView textViewSellerGST;

        public SellerViewHolder(View itemView) {
            super(itemView);
            this.textViewSellerId = itemView.findViewById(R.id.textViewSellerId);
            this.textViewSellerName = itemView.findViewById(R.id.textViewSellerName);
            this.textViewSellerAddress = itemView.findViewById(R.id.textViewSellerAddress);
            this.textViewSellerCity = itemView.findViewById(R.id.textViewSellerCity);
            this.textViewSellerPhone = itemView.findViewById(R.id.textViewSellerPhone);
            this.textViewSellerGST = itemView.findViewById(R.id.textViewSellerGST);
        }
    }

}
