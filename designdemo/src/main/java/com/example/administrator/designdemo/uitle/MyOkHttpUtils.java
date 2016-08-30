/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.uitle;

import android.content.Context;

import com.example.administrator.designdemo.tools.DLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MyOkHttpUtils {
	RequestCall call;
	private static String TAG = "BaseActivity";
	private Context context;
	private static MyCallBack mCallBack;
	private static MyOkHttpUtils MyOkHttpUtils;

	private MyOkHttpUtils(Context context) {
		this.context = context;
	}

	public static MyOkHttpUtils getInstance(Context context, MyCallBack callBack) {
		if (MyOkHttpUtils == null) {

			MyOkHttpUtils = new MyOkHttpUtils(context);
		}
		mCallBack = callBack;
		return MyOkHttpUtils;
	}

	public void getHttp(String urlString, String tag) {
		this.TAG = tag;
//		if (!((Activity) context).isFinishing()) {
//			DialogUtils.showProgressDialog(context);
//		}
		call = OkHttpUtils.get().url(urlString).tag(tag).build();
		call.execute(new StringCallback() {
			@Override
			public void onResponse(String response, int arg1) {
				DLog.d("返回数据：" + response + "int" + arg1);

			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				error(arg0, arg1, arg2);
			}

		});
	}

	private void error(Call arg0, Exception arg1, int arg2) {
//		DialogUtils.closeProgressDialog();
		DialogUtils.showToast(context,"网络异常", 0);
		mCallBack.httpCallBackErr(TAG.hashCode(), arg1);
		DLog.d("网络错误：：" + arg0 + "Exception：" + arg1 + "代码号：" + arg2);
	}

	public <T> void getHttp(String urlString, String tag, final Class<T> cls) {
		this.TAG = tag;
//		if (!((Activity) context).isFinishing()) {
//			DialogUtils.showProgressDialog(context);
//		}
		call = OkHttpUtils.get().url(urlString).tag(tag).build();
		call.execute(new StringCallback() {
			@Override
			public void onResponse(String response, int arg1) {
				T t = null;
				try {
					Gson gson = new Gson();
					t = gson.fromJson(response.toString(), cls);
				} catch (Exception e) {
				}
				DLog.d("返回数据：" + response + "int" + arg1);
				succ(t);
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				error(arg0, arg1, arg2);
			}
		});
	}

	private <T> void succ(T t) {
		mCallBack.httpCallBackSucc(TAG.hashCode(), t);
//		DialogUtils.closeProgressDialog();
	}

	public <T> void getHttpList(String urlString, String tag, final Class<T> cls) {
		this.TAG = tag;
//		if (!((Activity) context).isFinishing()) {
//			DialogUtils.showProgressDialog(context);
//		}
		call = OkHttpUtils.get().url(urlString).tag(tag).build();
		call.execute(new StringCallback() {
			@Override
			public void onResponse(String response, int arg1) {
				List<Class<T>> t = null;
				try {
					Gson gson = new Gson();
					t = new Gson().fromJson(response.toString(), new TypeToken<List<Class<T>>>() {
					}.getType());
				} catch (Exception e) {
				}
				DLog.d("返回数据：" + response + "int" + arg1);
				succ(t);
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				error(arg0, arg1, arg2);
			}
		});
	}

	public <T> void postHttpList(String urlString, Map<String, String> params, String tag, final Class<T> cls) {
		// TODO
		this.TAG = tag;
//		if (!((Activity) context).isFinishing()) {
//			DialogUtils.showProgressDialog(context);
//		}
		call = OkHttpUtils.post().url(urlString).params(params).tag(tag).build();
		call.execute(new StringCallback() {
			@Override
			public void onResponse(String response, int arg1) {
				List<Class<T>> t = null;
				try {
					Gson gson = new Gson();
					t = new Gson().fromJson(response.toString(), new TypeToken<List<Class<T>>>() {
					}.getType());
				} catch (Exception e) {
				}
				DLog.d("返回数据：" + response + "int" + arg1);
				succ(t);
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				error(arg0, arg1, arg2);
			}
		});

	}

	public <T> void postHttp(String urlString, Map<String, String> params, String tag, final Class<T> cls) {
		// TODO
		this.TAG = tag;
//		if (!((Activity) context).isFinishing()) {
//			DialogUtils.showProgressDialog(context);
//		}
		call = OkHttpUtils.post().url(urlString).params(params).tag(tag).build();
		call.execute(new StringCallback() {
			@Override
			public void onResponse(String response, int arg1) {
				T t = null;
				try {
					t = new Gson().fromJson(response.toString(), cls);
				} catch (Exception e) {
				}
				DLog.d("返回数据：" + response + "int" + arg1);
				succ(t);
			}

			@Override
			public void onError(Call arg0, Exception arg1, int arg2) {
				error(arg0, arg1, arg2);
			}
		});

	}
	// 上传下载直接使用张鸿阳提供的github上的demo,使用简单！！！！！！！！！！！ TODO
	// public <T> void postUploadFiles(String urlString, String tag,File file,
	// final Class<T> cls) {
	// //TODO
	// File file = new File(Environment.getExternalStorageDirectory(),
	// "messenger_01.png");
	// if (!file.exists())
	// {
	// Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
	// return;
	// }
	// OkHttpUtils
	// .postFile()
	// .url(urlString)
	// .file(file)
	// .build()
	// .execute(new MyStringCallback());
	//
	// }
	// public <T> void postDownloadFile(String urlString, String tag, final
	// Class<T> cls) {
	// //TODO
	//
	//
	// }
}
