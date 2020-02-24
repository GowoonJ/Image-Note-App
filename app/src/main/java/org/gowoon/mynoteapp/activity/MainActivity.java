package org.gowoon.mynoteapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.NoteListAdapter;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.databinding.ActivityMainBinding;
import org.gowoon.mynoteapp.model.ImageData;
import org.gowoon.mynoteapp.model.NoteData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public NoteListAdapter noteListAdapter = new NoteListAdapter();

    List<NoteData> noteList = new ArrayList<>();

    NoteDB noteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recyclerNoteList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        binding.floatingActionButton.setOnClickListener((View)->{
            Intent intentWriteNote = new Intent(this,NoteActivity.class);
            startActivity(intentWriteNote);
        });
        noteDB = NoteDB.getDatabase(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNoteData();
        noteListAdapter.setItemClick((view, position) -> {
            Intent intentDetail = new Intent(this,NoteDetailActivity.class);
            intentDetail.putExtra("columnId", noteListAdapter.mDataList.get(position).getNoteId());
            startActivity(intentDetail);
        });
    }

    public void getNoteData(){
        new Thread(() ->{
            noteList = noteDB.noteDao().getNoteList();

            ImageData image;
            for (int i = 0 ; i <noteList.size(); i++){
                image = noteDB.noteDao().getImg(noteList.get(i).noteId);
                noteList.get(i).imageUri = Uri.parse(image.url);
            }
            runOnUiThread(() -> {
                if (noteList.size() == 0){
                    binding.tvNothing.setVisibility(View.VISIBLE);
                }else{
                    binding.tvNothing.setVisibility(View.INVISIBLE);
                    noteListAdapter.setmDataList(noteList);
                    noteListAdapter.notifyDataSetChanged();
                    binding.recyclerNoteList.setAdapter(noteListAdapter);
                }

            });
        }).start();
    }

    @Override
    protected void onDestroy(){
        NoteDB.destroyDatabase();
        noteDB = null;
        super.onDestroy();
    }
}
