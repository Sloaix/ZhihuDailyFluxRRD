package lsxiao.com.zhihudailyrrd.util;

import android.content.Context;

import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;

/**
 * @author lsxiao
 * @date 2015-11-08 23:28
 */
public class AppContextUtil {

    public static Context instance() {
        return ApplicationComponent.Instance.get().getApplication();
    }
}
