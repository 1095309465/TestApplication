package com.yf.checkapp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class CheckImgApi {
    private static final int REQUEST_TIMEOUT = 60 * 1000;//设置超时60秒
    public static final int HAND_REQUEST_SUCCESS = 300;
    public static final int HAND_REQUEST_FAILURE = 400;
    public static String TAG = "图片检测";
    public static final String ASY_CHECK_IMG = "http://192.168.0.114:8083/api/asyCheckImg";

    //请求数据
    public static void sendRequestPost(final String data) {

        new Thread() {
            @Override
            public void run() {
                super.run();

                BufferedReader reader = null;
//                String data = "{\"base64Img\":\"" + data + "\",\"callback\":\"\"}";

                HashMap<String, Object> params = new HashMap<>();
                params.put("base64Img", data);
                params.put("callback", "服务器回调需要接收的json数据,注意是json字符串");
                Gson gson = new Gson();
                String jsonParam = gson.toJson(params);
                Log.e(TAG, "data=" + jsonParam);
                try {
                    URL url = new URL(ASY_CHECK_IMG);// 创建连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(REQUEST_TIMEOUT);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
                    connection.setRequestMethod("POST"); // 设置请求方式
                    connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
                    //设置发送数据长度（用于发送大量数据使用）
//                    connection.setRequestProperty("Content-Length", String.valueOf(jsonParam.length()));
                    //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
                    out.append(jsonParam);
                    out.flush();
                    out.close();
                    Log.e(TAG, "getResponseCode" + String.valueOf(connection.getResponseCode()));
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 读取响应
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                        String line;
                        String res = "";
                        while ((line = reader.readLine()) != null) {
                            res += line;
                        }
                        Log.e(TAG, "getResponsebady=" + res);
                        reader.close();
                    } else {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "IOException=" + e);
                }
            }
        }.start();
    }


}
