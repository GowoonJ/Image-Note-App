package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.ImageListAdapter;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.model.ImageData;
import org.gowoon.mynoteapp.model.NoteDetailData;

import java.util.ArrayList;
import java.util.List;

public class EditNoteActivity extends AppCompatActivity {
    EditText editTextTitle, editTextContent;
    RecyclerView recyclerView;
    Button buttonEdit;
    ImageListAdapter imageListAdapter = new ImageListAdapter();

    static int COLUMN_ID = 0;

    NoteDB noteDB;
    NoteDetailData noteData;
    List<ImageData> imageList;
    ArrayList<Uri> subImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setCustomAppBar();
        initView();

        COLUMN_ID = getIntent().getIntExtra("postId",0);

        noteDB = NoteDB.getDatabase(this);
        getNoteData();

        recyclerView.setAdapter(imageListAdapter);
        buttonEdit.setOnClickListener(view -> {

        });
    }
    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("기록 편집");
        customAppBar.setBackClickListener(v -> finish());
    }

    private void initView(){
        editTextTitle = findViewById(R.id.editText_title);
        editTextContent = findViewById(R.id.editText_content);
        recyclerView = findViewById(R.id.recycler_add_image);
        buttonEdit = findViewById(R.id.button_write);
        buttonEdit.setText("기록 수정");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
    }

    public void getNoteData(){
        new Thread(() ->{
            noteData = noteDB.noteDao().getNoteDetail(COLUMN_ID);
            imageList = noteDB.noteDao().getImgList(COLUMN_ID);
            for (int i = 0 ; i < imageList.size(); i++){
                subImageList.add(Uri.parse(imageList.get(i).url));
            }
            imageListAdapter.setDataList(subImageList);
            recyclerView.setAdapter(imageListAdapter);
            setData();
        }).start();
    }

    public void setData(){
        editTextTitle.setText(noteData.title);
        editTextContent.setText(noteData.content);
    }

    @Override
    protected void onDestroy() {
        NoteDB.destroyDatabase();
        noteDB = null;
        super.onDestroy();
    }
}
