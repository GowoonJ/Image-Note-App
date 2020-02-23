package org.gowoon.mynoteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.databinding.LayoutBottomSheetBinding;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    public LayoutBottomSheetBinding binding;
    public Button btnCamera, btnUri;

    public interface OnCameraButtonClickListener {
        void onClick();
    }
    public OnCameraButtonClickListener cameraButtonClickListener = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet,container,false);

        binding.tvCancel.setOnClickListener(view -> dismiss());
        binding.buttonCamera.setOnClickListener(view -> {
           cameraButton();
        });

        return binding.getRoot();
    }

    public void setOnCamerraButtonClickListener(OnCameraButtonClickListener listener){
        this.cameraButtonClickListener = listener;
    }
    public void cameraButton(){
        if(cameraButtonClickListener != null){
            cameraButtonClickListener.onClick();
        }
        dismiss();
    }
}
