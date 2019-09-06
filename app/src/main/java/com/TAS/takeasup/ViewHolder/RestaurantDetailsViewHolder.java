package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

    public TextView restHours,restAddress,restCost,restCuisines,restSpeciality,restType;
    public TextView restPhoneNo,restWebsite,restFacebook,restInstagram,seeMore;

    public RestaurantDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        restHours = itemView.findViewById(R.id.dispHours);
        restAddress = itemView.findViewById(R.id.dispAddress);
        restCost = itemView.findViewById(R.id.dispCost);
        restCuisines = itemView.findViewById(R.id.dispCuisines);
        restSpeciality = itemView.findViewById(R.id.dispSpeciality);
        restType = itemView.findViewById(R.id.dispType);
        restPhoneNo = itemView.findViewById(R.id.dispPhoneNo);
        restWebsite = itemView.findViewById(R.id.dispWebsite);
        restFacebook = itemView.findViewById(R.id.dispFacebook);
        restInstagram = itemView.findViewById(R.id.dispInstagram);
        seeMore = itemView.findViewById(R.id.seemore);
    }
}
