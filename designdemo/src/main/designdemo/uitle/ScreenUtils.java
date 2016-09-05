/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
/**隐藏键盘*/
public class ScreenUtils {
	public static void hideInput(Context context, View editView) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(editView.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}
	public static int getStatusHeight(Activity activity) { 
		int statusHeight = 0; 
		Rect localRect = new Rect(); 
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect); 
		statusHeight = localRect.top; 
		if (0 == statusHeight) { 
		Class<?> localClass; 
		try { 
		localClass = Class.forName("com.android.internal.R$dimen"); 
		Object localObject = localClass.newInstance(); 
		int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString()); 
		statusHeight = activity.getResources().getDimensionPixelSize(i5); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		} 
		return statusHeight; 
		}
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
    /** 
     * 获取屏幕宽度 
     */  
    public static int getScreenWidthPX(Context context) {  
    	return context.getResources().getDisplayMetrics().widthPixels;
    }  
    /** 
     * 获取屏幕高度 
     */  
    public static int getScreenHeightPX(Context context) {  
    	return context.getResources().getDisplayMetrics().heightPixels;
    }  

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */
  //转换dip为px 
    public static int convertDipOrPx(Context context, int dip) { 
        float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dip*scale + 0.5f*(dip>=0?1:-1)); 
    } 
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    //转换px为dip 
    public static int convertPxOrDip(Context context, int px) { 
        float scale = context.getResources().getDisplayMetrics().density; 
       return (int)(px/scale + 0.5f*(px>=0?1:-1)); 
   } 
    /** 
     * 根据手机的分辨率从 sp(像素) 的单位 转成为 px
     */  
    public static int sp2px(Context context, float spValue) {
          float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
          return (int) (spValue * fontScale + 0.5f);
      }
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp 
     */  
      public static int px2sp(Context context, float pxValue) {
          float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
          return (int) (pxValue / fontScale + 0.5f);
      }
    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        if((flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true;
        }else {
            return false;
        }
    }
    // 获取指定Activity的截屏
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
