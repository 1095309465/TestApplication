package com.yf.personcheck.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yf.personcheck.R;


/**
 * @author Shaun_Xu
 * @description TODO
 * @date 2018/5/2
 */

public class SelectMediaMenuPopup extends Dialog implements View.OnClickListener {

    private View shoot;
    private View album;
    private View video;
    private View cancel;
    private View view;
    private OnSelectMediaMenuClickListener listener;

    public SelectMediaMenuPopup(Activity context) {
        super(context, R.style.DialogTheme);
        view = View.inflate(context, R.layout.popup_select_media, null);
        this.setContentView(view);
        View root = view.findViewById(R.id.root);
        root.setOnClickListener(v -> dismiss());

        shoot = findViewById(R.id.shoot);
        album = findViewById(R.id.album);
        video = findViewById(R.id.video);
        cancel = findViewById(R.id.cancel);
        shoot.setOnClickListener(this);
        album.setOnClickListener(this);
        video.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Window window = getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_bottom_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public View getPopupWindowView() {
        return view;
    }


    public OnSelectMediaMenuClickListener getListener() {
        return listener;
    }

    public void setListener(OnSelectMediaMenuClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.shoot) {
            if (listener != null) {
                listener.onShootClick();
            }
            dismiss();

        } else if (i == R.id.album) {
            if (listener != null) {
                listener.onAlbumClick();
            }
            dismiss();

        } else if (i == R.id.video) {
            if (listener != null) {
                listener.onVideoClick();
            }
            dismiss();

        } else if (i == R.id.cancel) {
            dismiss();

        }
    }

    public void showPopupWindow() {
        show();
    }

    public interface OnSelectMediaMenuClickListener {
        void onShootClick();

        void onAlbumClick();

        void onVideoClick();
    }
}
