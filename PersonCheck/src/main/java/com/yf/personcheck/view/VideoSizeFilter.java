package com.yf.personcheck.view;

import android.content.Context;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.Set;

/**
 * @author Shaun_Xu
 * @description TODO
 * @date 2018/12/27
 */

public class VideoSizeFilter extends Filter {

    @Override
    protected Set<MimeType> constraintTypes() {
        return MimeType.ofVideo();
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (item.isVideo()) {
            if (item.duration < 3000 || item.duration > 8000)
                return new IncapableCause("视频长度限制 3秒-8秒");
            else if (item.size > (1024 * 1024 * 20))
                return new IncapableCause("视频大小不超过20M");
        }
        return null;
    }
}
