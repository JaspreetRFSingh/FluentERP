package com.jstech.fluenterp.adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.Material;
import java.util.ArrayList;

public class AdapterDisplayMaterials extends RecyclerView.Adapter<AdapterDisplayMaterials.MaterialViewHolder> {

    ArrayList<Material> dataSet;

    public AdapterDisplayMaterials(ArrayList<Material> data){
        this.dataSet = data;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_material, parent, false);
        AdapterDisplayMaterials.MaterialViewHolder myViewHolder = new AdapterDisplayMaterials.MaterialViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {

        TextView textViewMaterialId = holder.textViewMatId;
        TextView textViewMaterialType = holder.textViewMatType;
        TextView textViewMaterialDescription = holder.textViewMatDesc;
        TextView textViewMaterialDU = holder.textViewMatDu;
        TextView textViewMaterialCost = holder.textViewMatCost;
        textViewMaterialId.setText(String.valueOf(dataSet.get(position).getMaterialCode()));
        textViewMaterialType.setText(String.valueOf(dataSet.get(position).getMaterialType()));
        textViewMaterialDescription.setText(String.valueOf(dataSet.get(position).getMaterialDescription()));
        textViewMaterialDU.setText(String.valueOf(dataSet.get(position).getDimensionalUnit()));
        textViewMaterialCost.setText(String.valueOf(dataSet.get(position).getCostPerDu()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder{
        TextView textViewMatId;
        TextView textViewMatDesc;
        TextView textViewMatType;
        TextView textViewMatDu;
        TextView textViewMatCost;
        public MaterialViewHolder(View itemView) {
            super(itemView);
            this.textViewMatId = itemView.findViewById(R.id.textViewMaterialId);
            this.textViewMatType = itemView.findViewById(R.id.textViewMaterialType);
            this.textViewMatDesc = itemView.findViewById(R.id.textViewMaterialDescription);
            this.textViewMatDu = itemView.findViewById(R.id.textViewMaterialDu);
            this.textViewMatCost = itemView.findViewById(R.id.textViewMaterialCost);
        }
    }
}
