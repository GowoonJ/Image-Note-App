package org.gowoon.mynoteapp.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import org.gowoon.mynoteapp.R;

public class CustomAppBar {
    private Context context;
    private ActionBar actionBar;
    private backClickListener backClickListener = null;

    public interface backClickListener{
        void onBackClick(View v);
    }

    public void setBackClickListener(backClickListener backClickListener) {
        this.backClickListener = backClickListener;
    }

    public CustomAppBar(Context context, ActionBar actionBar){
        this.context = context;
        this.actionBar = actionBar;
    }

    public void setCustomAppBar(String title){
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = LayoutInflater.from(context).inflate(R.layout.layout_custom_app_bar,null);
        TextView tvTitle= mCustomView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ImageButton btnBack = mCustomView.findViewById(R.id.iv_back);

        btnBack.setOnClickListener(view -> {
            if (backClickListener != null){
                  backClickListener.onBackClick(view);
            }
        });

        actionBar.setCustomView(mCustomView);
        actionBar.setElevation(0);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView,params);
    }

}
