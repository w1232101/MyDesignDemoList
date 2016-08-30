/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.constant;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.administrator.designdemo.BaseApplication;
import com.example.administrator.designdemo.tools.DLog;
import com.example.administrator.designdemo.tools.UrlDecryption;


public class ApiClient {
	
	public static void BugRepot(Throwable ex){
	Context mCotext= BaseApplication.getInstance();
	try {
		PackageManager pm = mCotext.getPackageManager();
		PackageInfo pinfo = pm.getPackageInfo(mCotext.getPackageName(),
				PackageManager.GET_ACTIVITIES);
	} catch (NameNotFoundException e) {
		DLog.d("获取包信息失败");
		e.printStackTrace();
	}
	String userName = "";
	String systemversion = "Android:" + android.os.Build.VERSION.RELEASE;
	String phonetype = android.os.Build.MODEL;// 手机的具体机型
	String exceptiondes = ex.getMessage().replaceAll(" ", "");
	String PlatformType = "1";
	String AppName="dangjianyun";
	Constant.ExceptionLog=Constant.ExceptionLog+ "?username=" + userName
			+ "&systemversion=" + UrlDecryption.MY(systemversion)
			+ "&phonetype=" + UrlDecryption.MY(phonetype)
			+ "&exceptiondes=" + exceptiondes
			+ "&PlatformType=" + UrlDecryption.MY(PlatformType)
	        + "&appname="+UrlDecryption.MY(AppName);
//	OkHttpClientUtils.getDataAsync(mCotext,Constant.ExceptionLog, null,"BugRepot");
	}

	
	
	

}
