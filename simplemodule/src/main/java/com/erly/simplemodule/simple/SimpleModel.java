package com.erly.simplemodule.simple;

import com.erly.simplemodule.SimpleModuleAPI;
import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.helper.RxPoJoTransHelper;
import com.hp.libcore.mvp.BaseModel;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;

public class SimpleModel extends BaseModel implements SimpleContract.Model{
    @Override
    public Observable<JokeBean> getJokeInfo(int page) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("key","a81e20112369970dfe3509187b749296");
        parameter.put("sort","desc");
        parameter.put("time",System.currentTimeMillis()/1000);
        parameter.put("page",page);
        parameter.put("pagesize",10);
        return mRepository.obtainRetrofitService(SimpleModuleAPI.class)
                .getJokeInfo(parameter)
                .compose(RxPoJoTransHelper.getInstance().transformPoJo());
    }
}
