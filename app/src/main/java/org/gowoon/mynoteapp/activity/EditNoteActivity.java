package org.gowoon.mynoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.ImageListAdapter;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.database.ImageTable;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.database.NoteTable;
import org.gowoon.mynoteapp.databinding.LayoutNoteBinding;
import org.gowoon.mynoteapp.fragment.BottomSheetDialog;
import org.gowoon.mynoteapp.helper.CurrentDateHelper;
import org.gowoon.mynoteapp.model.ImageData;
import org.gowoon.mynoteapp.model.NoteDetailData;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class EditNoteActivity extends AppCompatActivity {
    ImageListAdapter imageListAdapter = new ImageListAdapter();
    CurrentDateHelper dateHelper = new CurrentDateHelper();

    LayoutNoteBinding binding;
    static int COLUMN_ID = 0;

    private static int permissionCamera, permissionStorage;

    NoteDB noteDB;
    NoteTable noteTable = new NoteTable();
    ImageTable imageTable = new ImageTable();

    NoteDetailData noteData;
    List<ImageData> imageList;
    ArrayList<Uri> subImageList = new ArrayList<>();
    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.layout_note);
        setCustomAppBar();
        initView();
        getPermissionCheck();

        COLUMN_ID = getIntent().getIntExtra("postId",0);

        binding.recyclerAddImage.setAdapter(imageListAdapter);
        noteDB = NoteDB.getDatabase(this);
        getNoteData();

        binding.buttonWrite.setOnClickListener(view -> {
            updateData();
        });
    }
    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("기록 편집");
        customAppBar.setBackClickListener(v -> finish());
    }

    private void initView(){
        binding.buttonWrite.setText("기록 수정");
        binding.tvNoteImageAdd.setOnClickListener(view -> {
            getImage();
        });

        binding.recyclerAddImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
    }

    public void getNoteData(){
        new Thread(() ->{
            noteData = noteDB.noteDao().getNoteDetail(COLUMN_ID);
            imageList = noteDB.noteDao().getImgList(COLUMN_ID);
            for (int i = 0 ; i < imageList.size(); i++){
                subImageList.add(Uri.parse(imageList.get(i).url));
            }
            imageListAdapter.setDataList(subImageList);
            binding.recyclerAddImage.setAdapter(imageListAdapter);
            setData();
        }).start();
    }

    public void setData(){
        binding.editTextTitle.setText(noteData.title);
        binding.editTextContent.setText(noteData.content);
    }

    @Override
    protected void onDestroy() {
        NoteDB.destroyDatabase();
        noteDB = null;
        super.onDestroy();
    }

    private void updateData(){
        title = binding.editTextTitle.getText().toString();
        content = binding.editTextContent.getText().toString();

        ArrayList<ImageTable> imageTables = new ArrayList<>();
        ArrayList<Uri> subImageList = imageListAdapter.getDataList();

        for (int i = 0; i < subImageList.size();i++){
            imageTable.url = String.valueOf(subImageList.get(i));
            imageTable.noteId = COLUMN_ID;
            imageTables.add(imageTable);
        }

        new Thread(() -> {
            //update noteTable
            noteDB.noteDao().updateNote(COLUMN_ID,title, content, dateHelper.getDate());
            //delete and insert imageTable
            noteDB.noteDao().DeleteNoteImg(COLUMN_ID);
            noteDB.noteDao().insertImg(imageTables);
            finish();
        }).start();
        Log.w("수정", String.valueOf(noteTable));
        Log.w("수정", String.valueOf(imageTable));
        Toast.makeText(this, "수정되었습니다",Toast.LENGTH_SHORT).show();
    }

    private void getImage(){
        if (permissionStorage == PackageManager.PERMISSION_GRANTED && permissionCamera == PackageManager.PERMISSION_GRANTED){
            BottomSheetDialog selectDialog = new BottomSheetDialog();
            selectDialog.show(getSupportFragmentManager(),"select");
            subImageList.clear();
            selectDialog.setOnCameraClickListener(() -> {
                Toast.makeText(this, "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                TedImagePicker.with(this)
                        .startMultiImage(list -> {
                            subImageList.addAll(list);
                            imageListAdapter.setDataList(subImageList);
                            binding.recyclerAddImage.setAdapter(imageListAdapter);
                        });
            });
            selectDialog.setOnUriClickListener(() ->{
                Toast.makeText(this, "외부 이미지 링크를 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.layoutEditUri.setVisibility(View.VISIBLE);
                binding.tvAddUri.setOnClickListener(view -> {
                    binding.layoutEditUri.setVisibility(View.GONE);
                    subImageList.add(Uri.parse(binding.editTextUri.getText().toString()));
                    imageListAdapter.setDataList(subImageList);
                    binding.recyclerAddImage.setAdapter(imageListAdapter);
                    binding.editTextUri.setText("");
                });
            });
            binding.recyclerAddImage.setAdapter(imageListAdapter);
        }else{
            Toast.makeText(this, "앱 사용권한을 확인해주세요", Toast.LENGTH_SHORT).show();
        }
        deleteImage();

    }
    private void getPermissionCheck(){
        permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void deleteImage(){
        imageListAdapter.setItemClick((view, position) -> {
            imageListAdapter.mImageList.remove(position);
            imageListAdapter.notifyItemRemoved(position);
            imageListAdapter.notifyItemRangeRemoved(position, imageListAdapter.mImageList.size());
        });
    }
}
