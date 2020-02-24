package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.DetailImageListAdapter;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.databinding.LayoutNoteDetailBinding;
import org.gowoon.mynoteapp.model.ImageData;
import org.gowoon.mynoteapp.model.NoteData;
import org.gowoon.mynoteapp.model.NoteDetailData;

import java.util.List;

public class NoteDetailActivity extends AppCompatActivity {
    LayoutNoteDetailBinding binding;
    NoteDB noteDB;
    NoteDetailData noteData;
    List<ImageData> imageList;
    DetailImageListAdapter imageListAdapter = new DetailImageListAdapter();

    int COLUMN_ID = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_note_detail);
        COLUMN_ID = getIntent().getIntExtra("columnId",0);

        setCustomAppBar();

        binding.recyclerNoteImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        noteDB = NoteDB.getDatabase(this);
        getNoteData();
    }

    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("내 기록");
        customAppBar.setBackClickListener(v -> finish());
    }

    public void getNoteData(){
        new Thread(() ->{
            noteData = noteDB.noteDao().getNoteDetail(COLUMN_ID);
            imageList = noteDB.noteDao().getImgList(COLUMN_ID);
            imageListAdapter.setDataList(imageList);
            binding.recyclerNoteImage.setAdapter(imageListAdapter);
            setData();
        }).start();
    }

    public void setData(){
        binding.tvTitle.setText(noteData.title);
        binding.tvContent.setText(noteData.content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        NoteDB.destroyDatabase();
        noteDB = null;
        super.onDestroy();
    }
}
