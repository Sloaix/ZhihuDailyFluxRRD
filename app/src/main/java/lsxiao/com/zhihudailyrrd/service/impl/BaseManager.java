package lsxiao.com.zhihudailyrrd.service.impl;


import com.google.gson.Gson;

import javax.inject.Inject;

import lsxiao.com.zhihudailyrrd.inject.component.ClientApiComponent;
import lsxiao.com.zhihudailyrrd.protocol.ClientApi;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class BaseManager {
    @Inject
    ClientApi mApi;
    @Inject
    Gson mGson;

    public BaseManager() {
        ClientApiComponent.Instance.get().inject(this);
    }

    public ClientApi getApi() {
        return mApi;
    }

    public Gson getGson() {
        return mGson;
    }
}
