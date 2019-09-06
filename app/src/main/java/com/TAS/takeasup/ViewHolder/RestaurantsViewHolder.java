package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class RestaurantsViewHolder extends RecyclerView.ViewHolder {

    public ImageView image,favButton;
    public TextView name,cuisine,cost;
    public RelativeLayout layout;


    public RestaurantsViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.nameText);
        cuisine = itemView.findViewById(R.id.cuisineText);
        cost = itemView.findViewById(R.id.costText);
        favButton = itemView.findViewById(R.id.favRestButton);
        layout = itemView.findViewById(R.id.relativeLayout);

    }
}
