package com.TAS.takeasup.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.R;

public class GalleryPicsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageGallery;
    public TextView textGallery;

    public GalleryPicsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageGallery = itemView.findViewById(R.id.imageGallery);
        textGallery = itemView.findViewById(R.id.textGallery);
    }
}
