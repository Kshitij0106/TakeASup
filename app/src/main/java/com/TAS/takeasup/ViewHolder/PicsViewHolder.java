package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class PicsViewHolder extends RecyclerView.ViewHolder {
    public ImageView activityPics;

    public PicsViewHolder(@NonNull View itemView) {
        super(itemView);

        activityPics = itemView.findViewById(R.id.imageGalleryPic);
    }
}
