/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.view;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.designdemo.R;


/**
 * 
 * Date: 2014-06-19 <br>
 * 
 */
public class LoadingDialog extends Dialog {

	private TextView tv;
	private boolean cancelable = true;

	public LoadingDialog(Context context) {
		super(context, R.style.loadingdialog);
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(),
				R.layout.activity_custom_loding_dialog_layout, null);
		setContentView(contentView);

		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
		tv = (TextView) findViewById(R.id.tv);
		getWindow().setWindowAnimations(R.anim.alpha_in);
	}

	@SuppressLint("NewApi")
	@Override
	public void show() {
		super.show();
	}

	@SuppressLint("NewApi")
	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}

	@Override
	public void setTitle(CharSequence title) {
		tv.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getString(titleId));
	}
}
