package xyz.zhenhua.transitionui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;

public class DetailActivity extends Activity {

    CardView cardView;
    DragLayout dragLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        UIUtils.fullScreen(this);

        initView();
    }

    public void initView() {
        cardView = findViewById(R.id.cv_detail_activity);
        dragLayout = findViewById(R.id.main);

        String color = getIntent().getStringExtra("color");
        cardView.setCardBackgroundColor(Color.parseColor(color));

        dragLayout.init();

        dragLayout.setSlideableListener(new DragLayout.SlideableListener() {
            @Override
            public void onSlideStart() {


            }

            @Override
            public void onSpringStart() {

            }

            @Override
            public void onSpringStop() {

            }

            @Override
            public void onDismiss() {
                finishAfterTransition();
            }
        });
    }
}


