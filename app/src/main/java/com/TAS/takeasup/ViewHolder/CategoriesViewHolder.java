package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryName;
    public RecyclerView categoryRecylerView;

    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.dishCategoryName);
        categoryRecylerView = itemView.findViewById(R.id.categoryRecyclerView);
    }
}
