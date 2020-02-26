package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class UsersPastOrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView orderNo,status,totalOrder;
    public RecyclerView dishesRecyclerView;

    public UsersPastOrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        orderNo = itemView.findViewById(R.id.ordernumbervh);
        status = itemView.findViewById(R.id.statusvh);
        totalOrder = itemView.findViewById(R.id.totalOrdervh);
        dishesRecyclerView = itemView.findViewById(R.id.dishesRecyclerViewvh);
    }
}
