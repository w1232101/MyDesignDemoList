/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

    /**
     * 判断是否具有网络连接
     *
     * @return
     */
    public static final boolean hasNetWorkConection(Context ctx) {
        // 获取连接活动管理器
        final ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取连接的网络信息
        final NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }
    
	public static int isWifi(Activity mActivity) {
		ConnectivityManager connectMgr = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null) {
			// 没网
			return 0;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return 1;
		} else {
			return 2;
		}
	}
}
