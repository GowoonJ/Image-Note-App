package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.databinding.LayoutNoteBinding;

public class NoteActivity extends AppCompatActivity {

    private LayoutNoteBinding binding;
    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.layout_note);
        setCustomAppBar();
        binding.buttonWrite.setOnClickListener(view -> {
            getStringData();
        });
        binding.tvNoteImageAdd.setOnClickListener(view -> {
            getImage();
        });
    }
    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("기록 남기기");
        customAppBar.setBackClickListener(v -> finish());
    }

    private void getStringData(){
        title = binding.editTextTitle.getText().toString();
        content = binding.editTextContent.getText().toString();
    }
    private void getImage(){

    }
}
