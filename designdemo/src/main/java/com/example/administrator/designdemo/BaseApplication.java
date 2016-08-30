/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.administrator.designdemo.uitle.CrashHandler;
import com.example.administrator.designdemo.uitle.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseApplication extends Application {
	public static Context CONTEXT = null;
	protected boolean isNeedCaughtExeption = true;// 是否捕获未知异常

	public static Context applicationContext;
	private static BaseApplication instance;
	public static final String TAG = "BaseApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		CONTEXT = getApplicationContext();
		instance = this;

		if (isNeedCaughtExeption) {
			initCarshHandler();
		}
		OkHttpUtils.initClient(BaseApplication.getOkHttpClient());
	}

	public static BaseApplication getInstance() {
		return instance;
	}

	private void initCarshHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}


	/**
	 * okHttp初始化配置
	 */
	public static OkHttpClient getOkHttpClient() {
		if (client != null) {
			return client;
		}
		synchronized (BaseApplication.class) {
			if (client == null) {

				OkHttpClient.Builder builder = new OkHttpClient.Builder();
				builder.writeTimeout(15 * 1000, TimeUnit.MILLISECONDS);
				builder.readTimeout(15 * 1000, TimeUnit.MILLISECONDS);
				builder.connectTimeout(8 * 1000, TimeUnit.MILLISECONDS);
				// 设置缓存路径
				File httpCacheDirectory = new File(applicationContext.getCacheDir(), "okhttpCache");
				// 设置缓存 10M
				Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
				builder.cache(cache);
				// 设置拦截器(缓存策略) 网络与应用拦截
				builder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
				builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
				//设置拦截器(失败重连次数) 应用拦截
				builder.addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						Request request = chain.request();

						Response response = chain.proceed(request);

						int tryCount = 0;
						while (!response.isSuccessful() && tryCount < 3) {

							Log.d("intercept", "Request is not successful - " + tryCount);
							tryCount++;
							response = chain.proceed(request);
						}

						// otherwise just pass the original response on
						return response;
					}
				});
				return builder.build();
			}
		}
		return client;
	}

	private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

		@Override
		public Response intercept(Interceptor.Chain chain) throws IOException {
			// 方案一：有网和没有网都是先读缓存
			// Request request = chain.request();
			// Log.i(TAG, "request=" + request);
			// Response response = chain.proceed(request);
			// Log.i(TAG, "response=" + response);
			//
			// String cacheControl = request.cacheControl().toString();
			// if (TextUtils.isEmpty(cacheControl)) {
			// cacheControl = "public, max-age=60";
			// }
			// return response.newBuilder()
			// .header("Cache-Control", cacheControl)
			// .removeHeader("Pragma")
			// .build();

			// 方案二：无网读缓存，有网根据过期时间重新请求
			boolean netWorkConection = NetUtils.hasNetWorkConection(BaseApplication.getInstance());
			Request request = chain.request();
			if (!netWorkConection) {
				request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
			}

			Response response = chain.proceed(request);
			if (netWorkConection) {
				// 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
				String cacheControl = request.cacheControl().toString();
				Log.i(TAG, "cacheControl:" + cacheControl);
				response.newBuilder().removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
						.header("Cache-Control", cacheControl).build();
			} else {
				int maxStale = 60 * 60 * 24 * 7;
				response.newBuilder().removeHeader("Pragma")
						.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
			}
			return response;
		}
	};
	private static OkHttpClient client;


}
