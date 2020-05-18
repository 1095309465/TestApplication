package com.yf.personcheck.network;

import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.SignatureUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ApiUtils {


    /**
     * 获取token
     *
     * @param callback
     */
    public static void getToken(Callback callback) {
        String timestamp = System.currentTimeMillis() + "";
        String nonce = new Random().nextInt(Integer.MAX_VALUE) + "";
        Map<String, String> params = new HashMap<>();
        params.put("secretId", ConfigConstants.SECRET_ID);
        params.put("businessId", ConfigConstants.BUSINESS_ID);
        params.put("timestamp", timestamp);
        params.put("nonce", nonce);
        String signature = SignatureUtils.genSignature(ConfigConstants.SECRET_KEY, params);

        FormBody body = new FormBody.Builder()
                .add("secretId", ConfigConstants.SECRET_ID)
                .add("businessId", ConfigConstants.BUSINESS_ID)
                .add("timestamp", timestamp)
                .add("nonce", nonce)
                .add("signature", signature)
                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.AUTH_GET_TOKEN)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }

    /**
     * 人脸识别
     *
     * @param type
     * @param face
     * @param callback
     */
    public static void postFaceCheck(String type, String face, Callback callback) {

        FormBody body = new FormBody.Builder()
                .add("accessToken", ConfigConstants.ACCESS_TOKEN)
                .add("type", type)//图片类型 1.url 2.base64
                .add("face", face)//人脸图像

                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.FACE_FEATURE)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }


    /**
     * 人脸对比
     *
     * @param feature
     * @param type
     * @param face
     * @param callback
     */
    public static void postFaceCompare(String feature, String type, String face, Callback callback) {

        FormBody body = new FormBody.Builder()
                .add("accessToken", ConfigConstants.ACCESS_TOKEN)
                .add("feature", feature)
                .add("type", type)//图片类型 1.url 2.base64
                .add("face", face)//人脸图像

                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.FACE_COMPARE)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }

    /**
     * @param image64
     * @param name
     * @param idNum
     * @param callback
     */
    public static void postIdentityPhotoCheck(String image64, String name, String idNum, Callback callback) {

        FormBody body = new FormBody.Builder()
                .add("accessToken", ConfigConstants.ACCESS_TOKEN)
                .add("image64", image64)
                .add("name", name)
                .add("idNum", idNum)
//                .add("idType", "")//证件类型，默认身份证

                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.IDENTITY_PHOTO_CHECK)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }

    /**
     * 人证合一动作描述获取
     *
     * @param callback
     */
    public static void getIdentitySession(Callback callback) {

        FormBody body = new FormBody.Builder()
                .add("accessToken", ConfigConstants.ACCESS_TOKEN)
                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.IDENTITY_SESSION_GET)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }

    //人证合一动作视频检测
    public static void actionCheck(String videoPath, String sessionId, String name, String idNum, Callback callback) {

        File file = new File(videoPath);//bitmap是图片资源
        MediaType mediaType = MediaType.parse("application/octet-stream");//
        //POST请求
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", file.getName(), requestBody)//文件名,请求体里的文件
//                .addFormDataPart("videoUrl", "")
                .addFormDataPart("sessionId", sessionId)
                .addFormDataPart("type", "1")//类型，分别为1：视频流，2: 视频URL
                .addFormDataPart("name", name)
                .addFormDataPart("idNum", idNum)
                .addFormDataPart("accessToken", ConfigConstants.ACCESS_TOKEN)
                .build();

        Request request = new Request.Builder()
                .url(HttpURLConstants.IDENTITY_VIDEO_ACTIONCHECK)

                .post(multipartBody)
                .build();

        HttpClient.enqueue(request, callback);

    }

    /**
     * 人证合一静默视频检测
     * @param videoPath
     * @param name
     * @param idNum
     * @param callback
     */
    public static void videoCheck(String videoPath, String name, String idNum, Callback callback) {

        File file = new File(videoPath);//bitmap是图片资源
        MediaType mediaType = MediaType.parse("application/octet-stream");//
        //POST请求
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", file.getName(), requestBody)//文件名,请求体里的文件
//                .addFormDataPart("videoUrl", "")
                .addFormDataPart("type", "1")//类型，分别为1：视频流，2: 视频URL
                .addFormDataPart("name", name)
                .addFormDataPart("idNum", idNum)
                .addFormDataPart("accessToken", ConfigConstants.ACCESS_TOKEN)
                .build();

        Request request = new Request.Builder()
                .url(HttpURLConstants.IDENTITY_VIDEO_CHECK)

                .post(multipartBody)
                .build();

        HttpClient.enqueue(request, callback);

    }

    //

    //人证合一动作视频检测
    public static void simpleCheck(String name, String idNum, Callback callback) {

        FormBody body = new FormBody.Builder()
                .add("accessToken", ConfigConstants.ACCESS_TOKEN)
                .add("name", name)
                .add("idNum", idNum)
                .build();
        Request request = new Request.Builder()
                .url(HttpURLConstants.IDENTITY_SIMPLE_CHECK)
                .post(body)
                .build();

        HttpClient.enqueue(request, callback);

    }


}
