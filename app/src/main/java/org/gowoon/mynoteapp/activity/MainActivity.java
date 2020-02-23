package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.NoteListAdapter;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.databinding.ActivityMainBinding;
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

    }

    public void getNoteData(){
        new Thread(() ->{
            noteList = noteDB.noteDao().getNoteList();
            noteList = noteList.subList(1,noteList.size());
            Log.w("noteList", String.valueOf(noteList.get(0)));
            noteListAdapter.setmDataList(noteList);
            noteListAdapter.notifyDataSetChanged();
            binding.recyclerNoteList.setAdapter(noteListAdapter);
        }).start();
    }

    @Override
    protected void onDestroy() {
        NoteDB.destroyDatabase();
        noteDB = null;
        super.onDestroy();
    }
}
