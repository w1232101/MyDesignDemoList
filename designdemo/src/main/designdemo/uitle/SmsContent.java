/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
/*
 * 根据手机号获取短信中的验证码
 * （）
 */
public class SmsContent extends ContentObserver {

	private Cursor cursor = null;
	private Activity activity;
	private EditText edit;
	private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";

	public SmsContent(Handler handler) {
		super(handler);
	}

	public SmsContent(Handler handler, Activity activity, EditText edit) {
		super(handler);
		this.activity = activity;
		this.edit = edit;
	}

	@Override
	public void onChange(boolean selfChange) {

		super.onChange(selfChange);
		// 读取收件箱中指定号码的短信
		cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
				new String[] { "_id", "address", "read", "body" },
				" address=? and read=?", new String[] { "10690257363810892", "0" },
				"_id desc");// 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
		Log.d("读取收件箱中指定号码的短信",
				"cursor.isBeforeFirst() " + cursor.isBeforeFirst()
						+ " cursor.getCount() , " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			ContentValues values = new ContentValues();
			values.put("read", "1"); // 修改短信为已读模式
			cursor.moveToNext();
			int smsbodyColumn = cursor.getColumnIndex("body");
			String smsBody = cursor.getString(smsbodyColumn);
			Log.d("短信内容：", "smsBody = " + smsBody);
			edit.setText(patternCode(smsBody));
		}
		// 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
		if (Build.VERSION.SDK_INT < 14) {
			cursor.close();
		}
	}

	/**
	 * 匹配短信中间的6个数字（验证码等）
	 * 
	 * @param patternContent
	 * @return
	 */
	private String patternCode(String patternContent) {
		if (TextUtils.isEmpty(patternContent)) {
			return null;
		}
		Pattern p = Pattern.compile(patternCoder);
		Matcher matcher = p.matcher(patternContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

}
