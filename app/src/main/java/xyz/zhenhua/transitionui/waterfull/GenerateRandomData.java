package xyz.zhenhua.transitionui.waterfull;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zachary on 2018/4/24.
 */

public class GenerateRandomData {


    public List<WaterfallBean> generateRandomDate() {
        List<WaterfallBean> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            WaterfallBean waterfallBean = new WaterfallBean();
            waterfallBean.setBgColor(getRandomColorStr());
            waterfallBean.setHeight(getRandomheigt());
            list.add(waterfallBean);
        }
        return list;
    }

    protected String getRandomColorStr() {
        String colorStr = "";
        StringBuilder tempStr = new StringBuilder();
        String[] c = {"a", "b", "c", "d", "e", "f", "0", "1", "3", "4", "5", "6", "7", "8", "9"};

        Random random = new Random();
        for (int i = 1; i <= 6; i++) {
            tempStr.append(c[random.nextInt(c.length)]);
        }

        colorStr = "#" + tempStr;
        Log.d("TAG", colorStr);

        return colorStr;
    }

    private int getRandomheigt() {
        int height = 300;
        height = (int) (500 + Math.random() * 200);
        return height;
    }

}
