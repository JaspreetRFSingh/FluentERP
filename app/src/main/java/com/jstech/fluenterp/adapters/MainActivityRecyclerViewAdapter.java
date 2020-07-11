package com.jstech.fluenterp.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jstech.fluenterp.R;
import com.jstech.fluenterp.models.DataModel;

import java.util.List;

public class MainActivityRecyclerViewAdapter
        extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder> {

    private final List<DataModel> mValues;
    private final Context mContext;
    private final ItemListener mListener;

    public MainActivityRecyclerViewAdapter(Context context, List<DataModel> values,
                                           ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textView;
        final ImageView imageView;
        final RelativeLayout relativeLayout;
        DataModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            relativeLayout = v.findViewById(R.id.relativeLayout);
        }

        public void setData(DataModel item) {
            this.item = item;
            textView.setText(item.getText());
            imageView.setImageResource(item.getDrawable());
            relativeLayout.setBackgroundColor(Color.parseColor(item.getColor()));
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(DataModel item);
    }
}
