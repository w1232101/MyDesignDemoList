/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.administrator.designdemo.uitle.CrashHandler;
import com.example.administrator.designdemo.uitle.DialogUtils;
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
	protected boolean isNeedCaughtExeption = false;// 是否捕获未知异常
	private static BaseApplication instance;
	public static final String TAG = "BaseApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CONTEXT = getApplicationContext();
		if (isNeedCaughtExeption) {
			initCarshHandler();
		}
		OkHttpUtils.initClient(getOkHttpClient());
	}

	public static BaseApplication getInstance() {
		return instance;
	}

	private void initCarshHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}


	private static OkHttpClient client2;

	/**
	 * okHttp初始化配置
	 */
	public static OkHttpClient getOkHttpClient() {
		if (client2 != null) {
			return client2;
		}
//		synchronized (BaseApplication.class) {
			if (client2 == null) {

				OkHttpClient.Builder client = new OkHttpClient.Builder();
				client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
				client.readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
				client.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
				// 设置缓存路径
				File httpCacheDirectory = new File(instance.getCacheDir(), "okhttpCache");
				// 设置缓存 10M
				Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
				client.cache(cache);
				// 设置拦截器
				client.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
				client.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
				 client2  = client.build();
				return client2;
			}
//		}
		return client2;
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
				DialogUtils.showToast(BaseApplication.getInstance(),"网络请求失败,请检查网络后重试",0);
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


//    private static final String TAG = "okhttp";
//
//    private static BaseApplication application;
//    private static Handler mHandler;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        initBase();
//        //初始化异常捕获
//        initCrash();
//    }
//
//
//    private void initBase() {
//        application = this;
//        mHandler = new Handler();
//    }
//
//    private void initCrash() {
//        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
//    }
//
//    public static BaseApplication getIntstance() {
//        return application;
//    }
//
//    public static Handler getHandler() {
//        if (mHandler == null) {
//            mHandler = new Handler();
//        }
//        return mHandler;
//    }
//    private static Retrofit retrofit;
//    private static Context mcontext;
//    public static RetrofitInterface getRetrofitInterface(Context context) {
//        mcontext = context;
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(Constant.BASEURL) //设置Base的访问路径
//                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
//                    .client(defaultOkHttpClient(context))
//                    .build();
//        }
//        return retrofit.create(RetrofitInterface.class);
//    }
//
//    public static OkHttpClient defaultOkHttpClient(Context context) {
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
//        client.readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
//        client.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
//        //设置缓存路径
//        client.addInterceptor(LoggingInterceptor);
//        client.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
//        client.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
//        File httpCacheDirectory = new File(context.getCacheDir(), "okhttpCache");
//        //设置缓存 10M
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
//        client.cache(cache);
//        //设置拦截器
//        return client.build();
//    }
//
//    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//
//        @Override
//        public Response intercept(Interceptor.Chain chain) throws IOException {
//            //方案一：有网和没有网都是先读缓存
////                Request request = chain.request();
////                Log.i(TAG, "request=" + request);
////                Response response = chain.proceed(request);
////                Log.i(TAG, "response=" + response);
////
////                String cacheControl = request.cacheControl().toString();
////                if (TextUtils.isEmpty(cacheControl)) {
////                    cacheControl = "public, max-age=60";
////                }
////                return response.newBuilder()
////                        .header("Cache-Control", cacheControl)
////                        .removeHeader("Pragma")
////                        .build();
//
//            //方案二：无网读缓存，有网根据过期时间重新请求
//            boolean netWorkConection = NetUtils.hasNetWorkConection(mcontext);
//            Request request = chain.request();
//            if (!netWorkConection) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//            }
//
//            Response response = chain.proceed(request);
//            if (netWorkConection) {
//                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                String cacheControl = request.cacheControl().toString();
//                Log.i(TAG, "cacheControl:" + cacheControl);
//                response.newBuilder()
//                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                        .header("Cache-Control", cacheControl)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 7;
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//            return response;
//        }
//    };
//
//    private static final Interceptor LoggingInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Interceptor.Chain chain) throws IOException {
//            Request request = chain.request();
//            long t1 = System.nanoTime();
//            Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
//            Response response = chain.proceed(request);
//            long t2 = System.nanoTime();
//            Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//            return response;
//        }
//    };
//
//    //版本名
//    public static String getVersionName() {
//        return getPackageInfo().versionName;
//    }
//
//    //版本号
//    public static int getVersionCode() {
//        return getPackageInfo().versionCode;
//    }
//
//    private static PackageInfo getPackageInfo() {
//        PackageInfo pi = null;
//        try {
//            PackageManager pm = application.getPackageManager();
//            pi = pm.getPackageInfo(application.getPackageName(),
//                    PackageManager.GET_CONFIGURATIONS);
//            return pi;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return pi;
//    }


}
