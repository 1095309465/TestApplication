package com.yf.glidegzip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    ImageView iv1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = findViewById(R.id.iv1);
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://wx.sunzhoubo.top/img/b.jpg";
                L("开始加载");
                Glide.with(MainActivity.this).load(url).into(iv1);


            }
        });

    }


    public static void L(String msg) {

        Log.e("图片gzip测试", msg);
    }




}
