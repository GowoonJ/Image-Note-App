package org.gowoon.mynoteapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.model.ImageData;

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    ArrayList<Uri> mImageList;

    @NonNull
    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_recycler, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListAdapter.ViewHolder holder, int position) {
        Uri itemData = mImageList.get(position);
        Glide.with(holder.itemView).load(itemData).into(holder.imageAdded);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAdded, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAdded = itemView.findViewById(R.id.imageView_added);
            deleteBtn = itemView.findViewById(R.id.imageView_delete);
        }
    }
    public void setDataList(ArrayList<Uri> dataList){
        this.mImageList = dataList;
        notifyDataSetChanged();
    }
}
