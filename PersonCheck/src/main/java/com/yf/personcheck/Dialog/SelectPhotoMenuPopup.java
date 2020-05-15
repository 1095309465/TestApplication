package com.yf.personcheck.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yf.personcheck.R;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.FileUtils;

import java.io.File;


/**
 * Created by 大灯泡 on 2017/3/2.
 * <p>
 * 选择照片popup
 */

public class SelectPhotoMenuPopup extends Dialog implements View.OnClickListener {

    private View shoot;
    private View album;
    private View cancel;
    private View view;
    private OnSelectPhotoMenuClickListener listener;

    public SelectPhotoMenuPopup(Activity context) {
        super(context, R.style.DialogTheme);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE);
        if (!file.exists()) {
            boolean flag = file.mkdirs();
            Log.e("创建文件夹", flag + "");
        }
        view = View.inflate(context, R.layout.popup_select_photo, null);
        setContentView(view);
        View root = view.findViewById(R.id.root);
        root.setOnClickListener(v -> dismiss());

        shoot = findViewById(R.id.shoot);
        album = findViewById(R.id.album);
        cancel = findViewById(R.id.cancel);
        shoot.setOnClickListener(this);
        album.setOnClickListener(this);
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

    public OnSelectPhotoMenuClickListener getOnSelectPhotoMenuClickListener() {
        return listener;
    }

    public SelectPhotoMenuPopup setOnSelectPhotoMenuClickListener(OnSelectPhotoMenuClickListener listener) {
        this.listener = listener;
        return this;
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

        } else if (i == R.id.cancel) {
            dismiss();

        }
    }


    public interface OnSelectPhotoMenuClickListener {
        void onShootClick();

        void onAlbumClick();
    }
}
