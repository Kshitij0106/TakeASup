package com.TAS.takeasup.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.Model.Order;
import com.TAS.takeasup.R;

import java.util.List;

public class PastOrdersDishesAdapter extends RecyclerView.Adapter<PastOrdersDishesAdapter.PastOrdersDishesViewHolder> {
    private List<Order> dishesList;
    private Context context;

    public PastOrdersDishesAdapter(List<Order> dishesList, Context context) {
        this.dishesList = dishesList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastOrdersDishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishes_layout, parent, false);
        PastOrdersDishesViewHolder pdvh = new PastOrdersDishesViewHolder(view);
        return pdvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersDishesViewHolder holder, int position) {
        holder.dname.setText("Name : " + dishesList.get(position).getDishName());
        holder.dqty.setText("Qty : " + dishesList.get(position).getDishQuantity());
    }

    @Override
    public int getItemCount() {
        return dishesList.size();
    }

    public class PastOrdersDishesViewHolder extends RecyclerView.ViewHolder {
        public TextView dname, dqty;

        public PastOrdersDishesViewHolder(@NonNull View itemView) {
            super(itemView);
            dname = itemView.findViewById(R.id.dname);
            dqty = itemView.findViewById(R.id.dqty);
        }
    }
}
