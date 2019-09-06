package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class RestaurantHomeViewHolder extends RecyclerView.ViewHolder {

    public TextView homeName;
    public ImageView homePic;

    public RestaurantHomeViewHolder(@NonNull View itemView) {
        super(itemView);

        homeName = itemView.findViewById(R.id.homeName);
        homePic = itemView.findViewById(R.id.homePic);
    }
}
