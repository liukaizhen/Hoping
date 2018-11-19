package com.hp.yujian;

import com.hp.libcore.base.BaseResponse;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestApi {

    @POST("Home")
    Observable<BaseResponse<Object>> getHomeInfo(@Body JSONObject requestBody);
}
