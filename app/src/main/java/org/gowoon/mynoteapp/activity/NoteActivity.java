package org.gowoon.mynoteapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.adapter.ImageListAdapter;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.database.ImageTable;
import org.gowoon.mynoteapp.database.NoteDB;
import org.gowoon.mynoteapp.database.NoteTable;
import org.gowoon.mynoteapp.databinding.LayoutBottomSheetBinding;
import org.gowoon.mynoteapp.databinding.LayoutNoteBinding;
import org.gowoon.mynoteapp.fragment.BottomSheetDialog;
import org.gowoon.mynoteapp.helper.CurrentDateHelper;
import org.gowoon.mynoteapp.model.ImageData;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;

public class NoteActivity extends AppCompatActivity {

    CurrentDateHelper dateHelper = new CurrentDateHelper();
    ImageListAdapter imageListAdapter = new ImageListAdapter();
    ArrayList<Uri> imageList = new ArrayList<>();
    int permissionCamera, permissionStorage;
    long noteTableId;
    private static int MY_PERMISSIONS_REQUEST = 200;
    private LayoutNoteBinding binding;
    String title, content;

    NoteDB noteDB;
    NoteTable noteTable = new NoteTable();
    ImageTable imageTable = new ImageTable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.layout_note);
        getPermissionCheck();
        setCustomAppBar();
        binding.buttonWrite.setOnClickListener(view -> insertData());
        binding.tvNoteImageAdd.setOnClickListener(view -> getImage());
        binding.recyclerAddImage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        noteDB = NoteDB.getDatabase(this);
    }
    private void setCustomAppBar(){
        CustomAppBar customAppBar = new CustomAppBar(this, getSupportActionBar());
        customAppBar.setCustomAppBar("기록 남기기");
        customAppBar.setBackClickListener(v -> finish());
    }

    private void insertData(){
        title = binding.editTextTitle.getText().toString();
        content = binding.editTextContent.getText().toString();

        ArrayList<ImageTable> imageTables = new ArrayList<>();

        new Thread(() -> {
            noteTable.title = title;
            noteTable.content = content;
            noteTable.date = dateHelper.getDate();
            noteTableId = noteDB.noteDao().insertNote(noteTable);

            for (int i = 0; i < imageListAdapter.getDataList().size();i++){
                imageTable.noteId = noteTableId;
                imageTable.url = String.valueOf(imageListAdapter.getDataList().get(i));
                imageTables.add(imageTable);
            }
            noteDB.noteDao().insertImg(imageTables);
            finish();
        }).start();
        Toast.makeText(this, "기록되었습니다",Toast.LENGTH_SHORT).show();
    }

    private void getImage(){
        if (permissionStorage == PackageManager.PERMISSION_GRANTED){
            BottomSheetDialog selectDialog = new BottomSheetDialog();
            selectDialog.show(getSupportFragmentManager(),"select");
            imageList.clear();
            selectDialog.setOnCameraClickListener(() -> {
                Toast.makeText(this, "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                TedImagePicker.with(this)
                        .startMultiImage(list -> {
                            imageList.addAll(list);
                            imageListAdapter.setDataList(imageList);
                            binding.recyclerAddImage.setAdapter(imageListAdapter);
                        });
            });
            selectDialog.setOnUriClickListener(() ->{
                Toast.makeText(this, "외부 이미지 링크를 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.layoutEditUri.setVisibility(View.VISIBLE);
                binding.tvAddUri.setOnClickListener(view -> {
                    binding.layoutEditUri.setVisibility(View.GONE);
                    imageList.add(Uri.parse(binding.editTextUri.getText().toString()));
                    imageListAdapter.setDataList(imageList);
                    binding.recyclerAddImage.setAdapter(imageListAdapter);
                    binding.editTextUri.setText("");
                });
            });
        }else{
            Toast.makeText(this, "앱 사용권한을 확인해주세요", Toast.LENGTH_SHORT).show();
        }
        deleteImage();
    }

    public void deleteImage(){
        imageListAdapter.setItemClick((view, position) -> {
            imageListAdapter.mImageList.remove(position);
            imageListAdapter.notifyItemRemoved(position);
            imageListAdapter.notifyItemRangeRemoved(position, imageListAdapter.mImageList.size());
        });
    }

    public void getPermissionCheck(){
        String[] permissionList = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCamera == PackageManager.PERMISSION_DENIED || permissionStorage == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, permissionList,MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldShowRequestPermissionRationale("권한 거부 시 노트 작성 기능을 이용할 수 없습니다\n[설정] > [권한] 에서 해당 권한을 허용해주세요");
            }
        }
    }
}
