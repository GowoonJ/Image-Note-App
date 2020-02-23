package org.gowoon.mynoteapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.gowoon.mynoteapp.R;
import org.gowoon.mynoteapp.databinding.LayoutBottomSheetBinding;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    LayoutBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet,container,false);

        binding.tvCancel.setOnClickListener(view -> dismiss());

        return binding.getRoot();
    }
}
