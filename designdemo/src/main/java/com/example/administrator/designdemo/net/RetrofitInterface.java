package com.example.administrator.designdemo.net;


import com.example.administrator.designdemo.bean.HttpResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


/**
 * 接口调用的工具类
 */
public interface RetrofitInterface {

//    //这里填写全部路径就会覆盖掉Build得BaseUrl
//    @Headers("Cache-Control: public, max-age=3600")
//    @GET(Constants.URL_HistoryDate)
//    Call<HttpResult<List<String>>> getGankHistoryDate();

    //http://gank.io/api/data/Android/10/1   http://gank.io/api/data/福利/1/1

    @Headers("Cache-Control: public, max-age=120")
    @GET("data/{type}/{count}/{pageIndex}")
    Call<HttpResult> getCommonDateNew(@Path("type") String type,
                                                        @Path("count") int count,
                                                        @Path("pageIndex") int pageIndex
    );


}
