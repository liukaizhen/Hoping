package com.hp.libcore.tools;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.hp.libcore.base.IApp;
import com.hp.libcore.di.AppComponent;

/**
 * 工具类
 */
public class Utils {
    private static Context context;
    private static Toast mToast;
    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //先取消正在显示的Toast
            if (mToast != null) {
                mToast.cancel();
            }
            CharSequence message = (CharSequence) msg.obj;
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 在BaseApplication中初始化Context
     * @param context
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     * @return ApplicationContext
     */
    public static Context getAPPContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static AppComponent obtainAppComponent(){
        PredictUtil.checkNotNull(context, "%s cannot be null", Context.class.getName());
        PredictUtil.checkState(context.getApplicationContext() instanceof IApp,
                "%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        return ((IApp) context.getApplicationContext()).obtainAppComponent();
    }

    /**
     * 显示Toast
     * @param message
     */
    public static void toast(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            handler.sendMessage(handler.obtainMessage(0, message));
        }
    }

    /**
     * 页面无交互时主动取消Toast
     */
    public static void cancelToast(){
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 获取版本名
     * @return
     */
    public static String getVersionName() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号
     * @return
     */
    public static long getVersionCode() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return info.getLongVersionCode();
            }else {
                return info.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取App项目名
     * @return
     */
    public static String getAppName() {
        String applicationName = "";
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * dp 转 px
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int px2dp(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得屏幕的宽度
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     * @return
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 全屏,并且沉侵式状态栏
     * @param activity
     */
    public static void statusInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 状态栏字体变成黑字，只适用于6.0以上
     * @param activity
     */
    public static void statusBarLight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View获取Activity的工具
     * @param view view
     * @return Activity
     */
    public static Activity getAttachActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public static boolean isNetConnect() {
        if (context != null) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
