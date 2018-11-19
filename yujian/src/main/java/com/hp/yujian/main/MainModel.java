package com.hp.yujian.main;

import com.hp.libcore.base.BaseResponse;
import com.hp.libcore.mvp.BaseModel;
import com.hp.yujian.TestApi;
import org.json.JSONObject;
import io.reactivex.Observable;

public class MainModel extends BaseModel implements MainContract.Model {

    @Override
    public Observable<BaseResponse<Object>> getHomeInfo() {
        return  mRepository.obtainRetrofitService(TestApi.class)
                .getHomeInfo(new JSONObject());
    }
}
