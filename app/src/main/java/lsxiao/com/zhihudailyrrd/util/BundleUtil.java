package lsxiao.com.zhihudailyrrd.util;

import android.os.Bundle;

import lsxiao.com.zhihudailyrrd.base.BundleKey;

/**
 * author lsxiao
 * date 2016-05-09 21:30
 */
public class BundleUtil {
    private BundleUtil() {
    }

    public static Bundle newInstance() {
        return new Bundle();
    }

    public static Bundle withThrowable(Throwable throwable) {
        Bundle bundle = newInstance();
        bundle.putSerializable(BundleKey.THROWABLE, throwable);
        return bundle;
    }

    public static Throwable getThrowable(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            throw new IllegalArgumentException("the bundle is null or empty");
        }
        return (Throwable) bundle.getSerializable(BundleKey.THROWABLE);
    }
}
