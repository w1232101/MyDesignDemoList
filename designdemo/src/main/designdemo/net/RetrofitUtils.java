package com.example.administrator.designdemo.net;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/8/30.
 */
public class RetrofitUtils {
    private static Retrofit retrofit;

    public static RetrofitInterface getRetrofitInterface() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(Constant.BASEURL) //设置Base的访问路径
//                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
//                    .client(BaseApplication.defaultOkHttpClient())
//                    .build();
//        }
        return retrofit.create(RetrofitInterface.class);
    }
}
