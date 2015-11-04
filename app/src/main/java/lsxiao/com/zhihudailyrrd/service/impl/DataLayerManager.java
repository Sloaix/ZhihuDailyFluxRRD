package lsxiao.com.zhihudailyrrd.service.impl;

import lsxiao.com.zhihudailyrrd.inject.component.ClientApiComponent;
import lsxiao.com.zhihudailyrrd.inject.component.DaggerClientApiComponent;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DataLayerManager {
    public static void init() {
        ClientApiComponent.Instance.init(DaggerClientApiComponent.builder().build());
    }
}
