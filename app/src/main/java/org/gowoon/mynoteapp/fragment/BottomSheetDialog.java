package org.gowoon.mynoteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.databinding.LayoutBottomSheetBinding;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private ViewDataBinding binding;
    public Button btnCamera, btnUri;

    public interface OnCameraClickListener {
        void onClick();
    }
    private OnCameraClickListener cameraClickListener = null;

    public interface OnUriClickListener{
        void onClick();
    }
    private  OnUriClickListener uriClickListener = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet,container,false);

        binding.getRoot().findViewById(R.id.tv_cancel).setOnClickListener(view -> dismiss());

        btnCamera = binding.getRoot().findViewById(R.id.button_camera);
        btnUri = binding.getRoot().findViewById(R.id.button_uri);

        btnCamera.setOnClickListener(view -> cameraButton());
        btnUri.setOnClickListener(view -> uriButton());

        return binding.getRoot();
    }

    public void setOnCameraClickListener(OnCameraClickListener listener){
        this.cameraClickListener = listener;
    }
    private void cameraButton(){
        if(cameraClickListener != null){
            cameraClickListener.onClick();
        }
        dismiss();
    }

    public void setOnUriClickListener(OnUriClickListener listener){
        this.uriClickListener = listener;
    }
    private void uriButton(){
        if (uriClickListener != null){
            uriClickListener.onClick();
        }
        dismiss();
    }
}
