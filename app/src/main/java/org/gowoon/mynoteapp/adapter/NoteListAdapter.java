package org.gowoon.mynoteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.model.NoteData;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
    private ArrayList<NoteData> mDataList;

    public NoteListAdapter(ArrayList<NoteData> dataList){
        this.mDataList = dataList;
    }

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
}
