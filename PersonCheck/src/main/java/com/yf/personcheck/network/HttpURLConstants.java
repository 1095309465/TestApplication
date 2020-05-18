package com.yf.personcheck.network;

public class HttpURLConstants {


    //基础域名
    public static final String BASE_URL = "https://api.hiyanai.com/v1/";

    //获取权限
    public static final String AUTH_GET_TOKEN = BASE_URL + "auth/getToken";

    //人脸识别
    public static final String FACE_FEATURE = BASE_URL + "face/feature";

    //人脸对比
    public static final String FACE_COMPARE = BASE_URL + "face/compare";

    //人证合一照片检测
    public static final String IDENTITY_PHOTO_CHECK = BASE_URL + "identity/photo/check";

    //人证合一动作获取
    public static final String IDENTITY_SESSION_GET = BASE_URL + "identity/session/get";

    //人证合一动作视频检测
    public static final String IDENTITY_VIDEO_ACTIONCHECK = BASE_URL + "identity/video/actioncheck";

    //人证合一简单检测
    public static final String IDENTITY_SIMPLE_CHECK = BASE_URL + "identity/simple/check";


    //人证合一静默视频检测
    public static final String IDENTITY_VIDEO_CHECK = BASE_URL + "identity/video/check";



}
