package com.yf.personcheck.utils;

import android.animation.ValueAnimator;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yf.personcheck.app.MainApplication;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by BlueHi on 2016/11/18.
 */

/**
 * 别忘了在application里init
 * logI
 * dp2px、px2dp
 */
public class MyUtils {
    private static Context context;
    private static float density; //屏幕密度
    private static int screenWidth; //屏幕宽度
    private static int screenHeight; //屏幕高度

    private MyUtils() {
    }

    public static void init(Context context) {
        MyUtils.context = context;
        density = context.getResources().getDisplayMetrics().density;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        logI("MyUtils", "屏幕参数=" + density + "," + screenWidth + "," + screenHeight);
    }

    /**
     * 判断多个tv是否为空（TextView）
     *
     * @param tv
     * @return 只要有一个为空，就返回true
     */
    public static boolean isEmpty(TextView... tv) {
        for (TextView t : tv) {
            if (TextUtils.isEmpty(t.getText().toString().trim())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 保留两位小数，如果没有小数，不会会显示小数
     */
    public static String formatDecimalsMaxTwo(double d) {
        return new java.text.DecimalFormat("#.##").format(d);
    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    public static boolean isViVo() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("vivo".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    /**
     * 小米后台弹出界面检测方法
     *
     * @param context
     * @return
     */
    public static boolean canBackgroundStart(Context context) {
        AppOpsManager ops = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        try {
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断vivo后台弹出界面 1未开启 0开启
     * @param context
     * @return
     */
    public static int getvivoBgStartActivityPermissionStatus(Context context) {
        String packageName = context.getPackageName();
        Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        try {
            Cursor cursor = context
                    .getContentResolver()
                    .query(uri2, null, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int currentmode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                    cursor.close();
                    return currentmode;
                } else {
                    cursor.close();
                    return 1;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 1;
    }

    /**
     * 判断vivo锁屏显示 1未开启 0开启
     * @param context
     * @return
     */
    public static int getVivoLockStatus(Context context) {
        String packageName = context.getPackageName();
        Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/control_locked_screen_action");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        try {
            Cursor cursor = context
                    .getContentResolver()
                    .query(uri2, null, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int currentmode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                    cursor.close();
                    return currentmode;
                } else {
                    cursor.close();
                    return 1;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 1;
    }

    /**
     * 小米后台锁屏检测方法
     * @param context
     * @return
     */
    public static boolean canShowLockView(Context context) {
        AppOpsManager ops = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        try {
            int op = 10020; // >= 23
            // ops.checkOpNoThrow(op, uid, packageName)
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]
                    {int.class, int.class, String.class}
            );
            Integer result = (Integer) method.invoke(ops, op,  android.os.Process.myUid(), context.getPackageName());

            return result == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断多个字符串是否为空（String）
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String... str) {
        for (String s : str) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }


    public static void dimBackground(boolean onPaush, Window window) {
        if (window == null) return;
        if (onPaush) {
            /** 窗口背景变暗*/
            dimBackground(window, 1.0f, 0.5f);
        } else {
            /** 窗口背景变亮*/
            dimBackground(window, 0.5f, 1.0f);
        }


    }


    /**
     * 页面动画变暗变亮
     *
     * @param from 开始时透明度
     * @param to   结束时透明度
     */
    private static void dimBackground(Window window, final float from, final float to) {
        if (window == null) return;
        if (window.getAttributes().alpha == to) return;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            if (window == null) return;
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = (Float) animation.getAnimatedValue();
            window.setAttributes(params);
        });
        valueAnimator.start();
    }

    //判断是否是手机号
    public static boolean isPhone(String phone) {
        String PHONE_PATTERN = "^1[0-9]{10}$";
        return Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
    }

    //判断是否身份证号
    public static boolean isCard(String cardId) {

        if (cardId.length() == 15 || cardId.length() == 18) {
            if (!cardCodeVerifySimple(cardId)) {
                return false;
                //error.put("cardId", "15位或18位身份证号码不正确");
            } else {
                if (cardId.length() == 18 && !cardCodeVerify(cardId)) {
                    return false;
                    //    error.put("cardId", "18位身份证号码不符合国家规范");
                }
            }
        } else {
            return false;
            //  error.put("cardId", "身份证号码长度必须等于15或18位");
        }
        return true;
    }

    public static String getMMSSTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date d1 = new Date(time);
        return format.format(d1);
    }

    private static boolean cardCodeVerifySimple(String cardcode) {
        //第一代身份证正则表达式(15位)
        String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        //第二代身份证正则表达式(18位)
        String isIDCard2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";

        //验证身份证
        if (cardcode.matches(isIDCard1) || cardcode.matches(isIDCard2)) {
            return true;
        }
        return false;
    }

    private static boolean cardCodeVerify(String cardcode) {
        int i = 0;
        String r = "error";
        String lastnumber = "";

        i += Integer.parseInt(cardcode.substring(0, 1)) * 7;
        i += Integer.parseInt(cardcode.substring(1, 2)) * 9;
        i += Integer.parseInt(cardcode.substring(2, 3)) * 10;
        i += Integer.parseInt(cardcode.substring(3, 4)) * 5;
        i += Integer.parseInt(cardcode.substring(4, 5)) * 8;
        i += Integer.parseInt(cardcode.substring(5, 6)) * 4;
        i += Integer.parseInt(cardcode.substring(6, 7)) * 2;
        i += Integer.parseInt(cardcode.substring(7, 8)) * 1;
        i += Integer.parseInt(cardcode.substring(8, 9)) * 6;
        i += Integer.parseInt(cardcode.substring(9, 10)) * 3;
        i += Integer.parseInt(cardcode.substring(10, 11)) * 7;
        i += Integer.parseInt(cardcode.substring(11, 12)) * 9;
        i += Integer.parseInt(cardcode.substring(12, 13)) * 10;
        i += Integer.parseInt(cardcode.substring(13, 14)) * 5;
        i += Integer.parseInt(cardcode.substring(14, 15)) * 8;
        i += Integer.parseInt(cardcode.substring(15, 16)) * 4;
        i += Integer.parseInt(cardcode.substring(16, 17)) * 2;
        i = i % 11;
        lastnumber = cardcode.substring(17, 18);
        if (i == 0) {
            r = "1";
        }
        if (i == 1) {
            r = "0";
        }
        if (i == 2) {
            r = "x";
        }
        if (i == 3) {
            r = "9";
        }
        if (i == 4) {
            r = "8";
        }
        if (i == 5) {
            r = "7";
        }
        if (i == 6) {
            r = "6";
        }
        if (i == 7) {
            r = "5";
        }
        if (i == 8) {
            r = "4";
        }
        if (i == 9) {
            r = "3";
        }
        if (i == 10) {
            r = "2";
        }
        if (r.equals(lastnumber.toLowerCase())) {
            return true;
        }
        return false;
    }

    //是否有效字符串(是否包含非法字符)，校验只能是中文,数字,英文字母和指定的其他字符
    public static boolean isValidString(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    //设置绑定的手机号(中间变**)
    public static void setMob(TextView mobileTv, String mobile) {
        String mob = mobile;
        StringBuilder sb = new StringBuilder(mob);
        mobileTv.setText(sb.replace(3, 7, "****"));
    }

    //设置手机号中间变**
    public static String mobHide(String mob) {
        StringBuilder sb = new StringBuilder(mob);
        return sb.replace(3, 7, "****").toString();
    }

    public static void logI(String TAG, String string) {
        Log.i(TAG, string);
    }

    /**
     * 将 dp 转为 px
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * density + 0.5);
    }

    /**
     * 将 px 转为 dp
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / density + 0.5);
    }

    /**
     * 显示没有数据时的背景图片
     */
    public static void loadEnd(SwipeRefreshLayout srl, View view, int listSize) {
//        view.setBackgroundColor(listSize == 0 ?
//                Color.TRANSPARENT : context.getResources().getColor(R.color.bg_gray));
//        srl.setRefreshing(false);//设置下拉刷新状态
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断是否安装了支付宝
     *
     * @return true 为已经安装
     */
    public static boolean hasApplication(Context context) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List<ResolveInfo> list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    //判断手机上是否已安装招商银行APP，这种方式很叼，应用被袁哥的隔离软件隔离后还能判断为有app,这时去跳转app时又会死机（也许只是跳转app的页面卡住，并不是死机）
   /* public static boolean isCMBAppInstalled(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("cmb.pb", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }*/
    //判断手机上是否已安装招商银行APP
    public static boolean isCMBAppInstalled(Context context) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("cmbmobilebank://"));
        List<ResolveInfo> list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    //跳转到app
    public static void toApp(String uriString) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        Uri content_url = Uri.parse(uriString);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 保留两位小数
     */
    public static String formatDecimals(double d) {
        return formatDecimals(d, 2);
//        return  new java.text.DecimalFormat("#.00").format(d);
    }

    /**
     * 保留几位小数
     */
    public static String formatDecimals(double d, int num) {
        return String.format("%." + num + "f", d);
    }

    public static Context getAppContext() {
        return context;
    }

    public static float getDensity() {
        return density;
    }

    public static int getScreenWidth() {
        return screenWidth = MainApplication.getInstance().getBaseContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
