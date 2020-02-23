package org.gowoon.mynoteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.model.NoteData;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private List<NoteData> mDataList;

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        NoteData currentData = mDataList.get(position);
        holder.tvTitle.setText(currentData.getTitle());
        holder.tvContent.setText(currentData.getContent());

        Glide.with(holder.itemView).load(currentData.imageUri)
                .error(R.drawable.no_image_img)
                .into(holder.imageThumbnail);

        bindingImage(position);
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) return mDataList.size(); else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent;
        private ImageView imageThumbnail;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvContent = itemView.findViewById(R.id.tv_item_content);
            imageThumbnail = itemView.findViewById(R.id.imageView_thumbnail);
        }
    }

    private void bindingImage(int position){

    }

    public void setmDataList(List<NoteData> dataList){
        this.mDataList = dataList;
        notifyDataSetChanged();
    }
}
