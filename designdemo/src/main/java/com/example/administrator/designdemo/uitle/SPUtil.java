/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 封装首选项
 * 
 * @author ccy
 * 
 */
public class SPUtil {
	/**
	 * 获取SharedPreference
	 * 
	 * @param context
	 * @return
	 */
	private static SharedPreferences getSharedPreferece(Context context) {
		return context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	/**
	 * 获取String值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences sp = getSharedPreferece(context);
		String result = sp.getString(key, null);
		return result;
	}

	/**
	 * 获取boolean值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sp = getSharedPreferece(context);
		boolean result = sp.getBoolean(key, false);
		return result;
	}

	/**
	 * 获取int值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key) {
		SharedPreferences sp = getSharedPreferece(context);
		int result = sp.getInt(key, 0);
		return result;
	}
	/**
	 * 向首选项存取数据(仅限于String,Boolean,Integer)
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, Object value) {
		SharedPreferences sp = getSharedPreferece(context);
		Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		}

		editor.commit();
	}

}
