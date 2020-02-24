package org.gowoon.mynoteapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.DetailImageListAdapter;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.databinding.LayoutNoteDetailBinding;
import org.gowoon.mynoteapp.model.ImageData;
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

        binding.tvEdit.setOnClickListener(view -> {
            Intent intentEdit = new Intent(this, EditNoteActivity.class);
            intentEdit.putExtra("postId",COLUMN_ID);
            startActivity(intentEdit);
            finish();
        });

        binding.tvDelete.setOnClickListener(view -> {
            setDialog();
        });
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

    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("기록 삭제").setMessage("정말 삭제하실건가요?");

        builder.setPositiveButton("OK", (dialog, id) ->{
            deleteNote();
            Toast.makeText(getApplicationContext(), "기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });

        builder.setNegativeButton("Cancel", (dialog, id) -> Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteNote(){
        new Thread(() ->{
            noteDB.noteDao().DeleteNote(COLUMN_ID);
            noteDB.noteDao().DeleteNoteImg(COLUMN_ID);
        }).start();
    }
}
