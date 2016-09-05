/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyFreshLayout extends SwipeRefreshLayout{

	private boolean isIn = true;
	public MyFreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyFreshLayout(Context context) {
		super(context);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		  switch (action) {
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_POINTER_DOWN:
				isIn = false;
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				return isIn?super.onInterceptTouchEvent(event):false;
			case MotionEvent.ACTION_UP:
				isIn = true;
			default:
				return super.onInterceptTouchEvent(event);
			}
		  
	}
}
