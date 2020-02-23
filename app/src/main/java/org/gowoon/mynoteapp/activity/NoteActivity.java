package org.gowoon.mynoteapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.custom.CustomAppBar;
import org.gowoon.mynoteapp.databinding.LayoutNoteBinding;
import org.gowoon.mynoteapp.fragment.BottomSheetDialog;

public class NoteActivity extends AppCompatActivity {

    int permissionCamera, permissionStorage;
    private static int MY_PERMISSIONS_REQUEST = 200;
    private LayoutNoteBinding binding;
    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.layout_note);
        getPermissionCheck();
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
        if (permissionCamera == PackageManager.PERMISSION_GRANTED){
            BottomSheetDialog selectDialog = new BottomSheetDialog();
            selectDialog.show(getSupportFragmentManager(),"select");
        }else{
            Toast.makeText(this, "앱 사용권한을 확인해주세요", Toast.LENGTH_SHORT).show();
        }
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
