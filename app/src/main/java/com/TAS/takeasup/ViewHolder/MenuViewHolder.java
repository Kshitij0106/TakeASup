package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    public TextView menuName,menuPrice,menuCategory,menuType;
    public ImageView addButton;
    public RelativeLayout layout;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        menuName = itemView.findViewById(R.id.nameText);
        menuPrice = itemView.findViewById(R.id.priceText);
//        menuCategory = itemView.findViewById(R.id.categoryText);
        menuType = itemView.findViewById(R.id.typeText);
        addButton = itemView.findViewById(R.id.addDish);

        layout = itemView.findViewById(R.id.relativeLayout);
    }
}
