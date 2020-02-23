package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.NoteListAdapter;
import org.gowoon.mynoteapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public NoteListAdapter NoteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recyclerNoteList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.recyclerNoteList.setAdapter(NoteListAdapter);

        binding.floatingActionButton.setOnClickListener((View)->{
            Intent intentWriteNote = new Intent(this,NoteActivity.class);
            startActivity(intentWriteNote);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
