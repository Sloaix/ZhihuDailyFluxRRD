package lsxiao.com.zhihudailyrrd.util;

import android.content.Context;

/**
 * @author lsxiao
 * @date 2015-11-08 23:28
 */
public class AppContextUtil {
    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return sContext;
    }
}
