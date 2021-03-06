package com.example.administrator.designdemo.uitle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebView;

import com.example.administrator.designdemo.R;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MyThemeManager {

    public final static int THEME_DAY = 0;
    public final static int THEME_NIGHT = 1;
    private static String CONFIG = "config";
    private static String SkinKey = "SkinKey";
    private static SharedPreferences sp;

    public static final String IntentExtra_SkinTheme = "IntentExtra_SkinTheme";
    private static final String IntentActionSkin = "com.change_skin.action";
    private static final IntentFilter intentFilter = new IntentFilter(IntentActionSkin);
    private static final Intent intent = new Intent(IntentActionSkin);

    /**
     * 获取当前主题的Type
     *
     * @param context
     * @return 0：白天主题；1：夜间主题
     */
    public static int getCurrentSkinType(Context context) {
        return getSharePreSkin(context, THEME_DAY);
    }

    private static void setSkinType(Context context, int theme) {
        saveSharePreSkin(context, theme);
    }

    /**
     * 获取当前主题
     *
     * @param context
     * @return
     */
    public static int getCurrentSkinTheme(Context context) {
        int saveSkinType = getCurrentSkinType(context);
        int currentTheme;
        switch (saveSkinType) {
            default:
            case THEME_DAY:
                currentTheme = R.style.DayTheme;
                break;
            case THEME_NIGHT:
                currentTheme = R.style.NightTheme;
                break;
        }
        return currentTheme;
    }

    /**
     * 改变主题皮肤
     *
     * @param activity 当前Activity
     * @param theme    两种选择
     */
    public static void changeSkin(Activity activity, int theme) {
        setSkinType(activity.getApplicationContext(), theme);
        //发送广播
        intent.putExtra("MY_SKIN_ACTION", theme);
        intent.setAction("MY_SKIN_ACTION");
        activity.sendBroadcast(intent);
    }


    public static void onActivityCreateSetSkin(Activity activity) {
        int currentSkinTheme = getCurrentSkinTheme(activity.getApplicationContext());
        activity.setTheme(currentSkinTheme);
    }

    private static void saveSharePreSkin(Context context, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(SkinKey, value).apply();
    }

    public static int getSharePreSkin(Context context, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getInt(SkinKey, defValue);
    }
    /*--------------------WebView 夜间模式-----------------*/
    public static void setupWebView(Context context,WebView webView, String backgroudColor, String fontColor, String urlColor) {
        if (webView != null) {
            webView.setBackgroundColor(0);
            Log.i("wjx","getCurrentSkinType(context):"+getCurrentSkinType(context));
            if (getCurrentSkinType(context) == THEME_NIGHT) {
                String js = String.format(jsStyle, backgroudColor, fontColor, urlColor, backgroudColor);
                webView.loadUrl(js);
            }
        }
    }

    private static String jsStyle = "javascript:(function(){\n" +
            "\t\t   document.body.style.backgroundColor=\"%s\";\n" +
            "\t\t    document.body.style.color=\"%s\";\n" +
            "\t\t\tvar as = document.getElementsByTagName(\"a\");\n" +
            "\t\tfor(var i=0;i<as.length;i++){\n" +
            "\t\t\tas[i].style.color = \"%s\";\n" +
            "\t\t}\n" +
            "\t\tvar divs = document.getElementsByTagName(\"div\");\n" +
            "\t\tfor(var i=0;i<divs.length;i++){\n" +
            "\t\t\tdivs[i].style.backgroundColor = \"%s\";\n" +
            "\t\t}\n" +
            "\t\t})()";


}
