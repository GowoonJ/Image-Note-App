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
import java.util.List;

public class DetailImageListAdapter extends RecyclerView.Adapter<DetailImageListAdapter.ViewHolder> {
    public List<ImageData> mImageList = new ArrayList<>();

    @NonNull
    @Override
    public DetailImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImageListAdapter.ViewHolder holder, int position) {
        Uri itemData = Uri.parse(mImageList.get(position).url);
        Glide.with(holder.itemView).load(itemData)
                .error(R.drawable.no_image_img)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
        }
    }
    public void setDataList(List<ImageData> dataList){
        mImageList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<ImageData> getDataList(){
        return mImageList;
    }
}
