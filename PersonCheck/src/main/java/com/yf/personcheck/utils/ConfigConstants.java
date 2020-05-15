package com.yf.personcheck.utils;

/**
 * @author Shaun_Xu
 * @description ConfigManager操作数据库时所用到的字段
 * @date 2018/4/13
 */

public class ConfigConstants {
    public static final String SECRET_ID = "tkXs4VPQUM5Dl6y391jbfKXP";
    public static final String BUSINESS_ID = "68012315";
    public static final String SECRET_KEY = "JghHC7q3Gy5VpgS7OXi6n2IUHctf8bk5";

    public static final String IMG_PRICE = "img_price";
    public static final String PATH_PICTURE = "Pictures";

    public static final String PATH_VIDEO = "YFCamera";
    public static class FILE {
        public static final int PHOTO_OR_VIDEO = 102;//视频或者图片
        public static final int RECORD_REQUEST = 101;//拍摄视频
        public static final int REQUEST_CODE_CHOOSE = 23; //选择照片请求码
        public static final int REQUEST_CODE_CAMERA = 24; //拍摄照片请求码
        public static final String PATH_PICTURE = "Pictures";
        public static final String AVATOR_PICTURE = "/ic_avator.jpg";
        public static final String CROP_AVATOR_PICTURE = "/ic_crop_avator.jpg";
        public static final String PICTURE_END_NAME = ".jpg";

    }

    //保存token
    public static String ACCESS_TOKEN = "";


}
