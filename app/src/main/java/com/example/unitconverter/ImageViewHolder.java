package com.example.unitconverter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.imageView);
    }
}