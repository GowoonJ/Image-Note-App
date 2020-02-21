package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.databinding.LayoutNoteBinding;

public class NoteActivity extends AppCompatActivity {

    private LayoutNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.layout_note);

        binding.buttonWrite.setOnClickListener(view -> getStringData());
    }
    private void getStringData(){

    }
}
