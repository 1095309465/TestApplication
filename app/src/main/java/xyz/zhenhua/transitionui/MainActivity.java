package xyz.zhenhua.transitionui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import xyz.zhenhua.transitionui.waterfull.GenerateRandomData;
import xyz.zhenhua.transitionui.waterfull.WaterfallAdapter;
import xyz.zhenhua.transitionui.waterfull.WaterfallBean;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WaterfallAdapter waterfallAdapter;
    StaggeredGridLayoutManager layoutManager;
    List<WaterfallBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    public void initData() {
        GenerateRandomData generateRandomData = new GenerateRandomData();
        list = generateRandomData.generateRandomDate();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recy_content);

        waterfallAdapter = new WaterfallAdapter(this, list);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(waterfallAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void btn(View view) {


//        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
//                R.anim.dialog_in_anim, R.anim.dialog_out_anim);
//        ActivityCompat.startActivity(this,
//                new Intent(this, Main2Activity.class), compat.toBundle());


        Intent i = new Intent(this, Main2Activity.class);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation
                (this, null);
        startActivity(i, transitionActivityOptions.toBundle());


//        startActivity(new Intent(this, Main2Activity.class));

    }
}
