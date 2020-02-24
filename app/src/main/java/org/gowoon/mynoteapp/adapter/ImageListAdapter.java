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

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    public ArrayList<Uri> mImageList = new ArrayList<>();
    private ItemClick itemClick;

    public interface ItemClick{
        void onClick(View view, int position);
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

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
        Glide.with(holder.itemView).load(itemData)
                .error(R.drawable.no_image_img)
                .into(holder.imageAdded);

        holder.deleteBtn.setOnClickListener(view -> {
            if (itemClick!=null){
                itemClick.onClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAdded, deleteBtn;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAdded = itemView.findViewById(R.id.imageView_added);
            deleteBtn = itemView.findViewById(R.id.imageView_delete);
        }
    }
    public void addDataList(ArrayList<Uri> dataList){
        mImageList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setDataList(ArrayList<Uri> dataList){
        this.mImageList = dataList;
        notifyDataSetChanged();
    }

    public ArrayList<Uri> getDataList(){
        return mImageList;
    }
}
