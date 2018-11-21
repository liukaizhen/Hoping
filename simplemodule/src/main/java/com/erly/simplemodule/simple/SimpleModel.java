package com.erly.simplemodule.simple;

import com.erly.simplemodule.SimpleModuleAPI;
import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BasePoJo;
import com.hp.libcore.mvp.BaseModel;
import com.hp.libcore.tools.Utils;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class SimpleModel extends BaseModel implements SimpleContract.Model{
    @Override
    public Observable<JokeBean> getJokeInfo(int page) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("key","a81e20112369970dfe3509187b74929");
        parameter.put("sort","desc");
        parameter.put("time",System.currentTimeMillis()/1000);
        parameter.put("page",page);
        parameter.put("pagesize",10);
        return mRepository.obtainRetrofitService(SimpleModuleAPI.class)
                .getJokeInfo(parameter)
                .compose(new ObservableTransformer<BasePoJo<JokeBean>, JokeBean>() {
                    @Override
                    public ObservableSource<JokeBean> apply(Observable<BasePoJo<JokeBean>> upstream) {
                        return upstream.filter(new Predicate<BasePoJo<JokeBean>>() {
                            @Override
                            public boolean test(BasePoJo<JokeBean> jokeBeanBasePoJo) throws Exception {
                                if (!jokeBeanBasePoJo.isSuccess())Utils.toast(jokeBeanBasePoJo.getMsg());
                                return jokeBeanBasePoJo.isSuccess();
                            }
                        }).flatMap(new Function<BasePoJo<JokeBean>, ObservableSource<JokeBean>>() {
                            @Override
                            public ObservableSource<JokeBean> apply(BasePoJo<JokeBean> jokeBeanBasePoJo) throws Exception {
                                return Observable.just(jokeBeanBasePoJo.getData());
                            }
                        });
                    }
                });
    }
}
