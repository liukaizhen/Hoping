package com.erly.simplemodule;


import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BasePoJo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * SimpleModule业务模块下的网络请求接口
 */
public interface SimpleModuleAPI {

    /**
     * 获取笑话列表信息
     * @param parameter
     * @return
     */
    @GET("joke/content/list.php")
    Observable<BasePoJo<JokeBean>> getJokeInfo(@QueryMap Map<String,Object> parameter);
}
