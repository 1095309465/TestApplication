package com.yf.personcheck.utils;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageBase64(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(data, Base64.DEFAULT);
    }

}
