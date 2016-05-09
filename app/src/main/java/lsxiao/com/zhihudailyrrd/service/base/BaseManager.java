package lsxiao.com.zhihudailyrrd.service.base;


import com.google.gson.Gson;

import javax.inject.Inject;

import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;
import lsxiao.com.zhihudailyrrd.protocol.ClientAPI;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseManager {
    @Inject
    ClientAPI mApi;
    @Inject
    Gson mGson;

    public BaseManager() {
        ApplicationComponent.Instance.get().inject(this);
    }

    public ClientAPI getApi() {
        return mApi;
    }

    public Gson getGson() {
        return mGson;
    }
}
