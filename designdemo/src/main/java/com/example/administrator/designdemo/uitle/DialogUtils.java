/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.example.administrator.designdemo.BaseApplication;
import com.example.administrator.designdemo.view.CToast;
import com.example.administrator.designdemo.view.LoadingDialog;


/*
 * 弹窗类
 */
public class DialogUtils {

	static LoadingDialog progressDialog;
	private static CToast mCToast;

	public static void showToast(Context context,String msg, int time) {
		// Toast.makeText(BaseApplication.CONTEXT, msg,
		// Toast.LENGTH_SHORT).show();
		// int
		// time=TextUtils.isEmpty(mEditText.getText().toString())?CToast.LENGTH_SHORT:Integer.valueOf(mEditText.getText().toString());
		if (null != mCToast)
			mCToast.hide();
		int times = time == 0 ? 100 : CToast.LENGTH_SHORT;
		mCToast = CToast.makeText(context, msg, times);
		mCToast.setGravity( Gravity.BOTTOM, 0, 20);
		mCToast.show();
	}

	public static void showToast(Context context,int msgId, int time) {
		Res.init(context);
		if (null != mCToast)
			mCToast.hide();
		int times = time == 0 ? 100 : CToast.LENGTH_SHORT;
		String str = Res.getResources().getString(msgId);
		mCToast = CToast.makeText(BaseApplication.CONTEXT, str, times);
		mCToast.setGravity( Gravity.BOTTOM, 0, 20);
		mCToast.show();
		// Toast.makeText(BaseApplication.CONTEXT, msgId,
		// Toast.LENGTH_SHORT).show();
	}

	public static void showProgressDialog(Context context,String title) {
		progressDialog = new LoadingDialog(context);
		progressDialog.setTitle(title);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	public static void closeProgressDialog() {
		if (progressDialog != null&&progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public static void showConfirmDialog(
			Context context,
			String title,
			String message,
			String positiveBtnName,
			String negativeBtnName,
			android.content.DialogInterface.OnClickListener positiveBtnListener,
			android.content.DialogInterface.OnClickListener negativeBtnListener) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		builder.create().show();
	}
}
