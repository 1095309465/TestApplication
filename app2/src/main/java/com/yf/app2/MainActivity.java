package com.yf.app2;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SmoothScrollView view;
    MineImageView iv;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        iv = findViewById(R.id.iv);
    }

    public void btn(View v) {
        view.smoothScroll(0, 100, 1000);
        iv.smoothScroll(0, 100, 1000);
    }
}
