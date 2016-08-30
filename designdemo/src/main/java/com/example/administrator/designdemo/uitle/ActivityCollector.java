/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {

	public static List<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : activities) {
			activity.finish();
		}
	}
}
