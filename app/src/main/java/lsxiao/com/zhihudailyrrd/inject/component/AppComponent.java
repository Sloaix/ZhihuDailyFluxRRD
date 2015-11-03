package lsxiao.com.zhihudailyrrd.inject.component;

import android.support.annotation.NonNull;

import lsxiao.com.zhihudailyrrd.base.BaseActivity;
import lsxiao.com.zhihudailyrrd.base.BaseDialogFragment;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;

/**
 * @author lsxiao
 * @date 2015-11-04 00:47
 */
public interface AppComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    void inject(BaseDialogFragment dialogFragment);

    class Instance {
        private static AppComponent sComponent;

        public static void init(@NonNull AppComponent component) {
            sComponent = component;
        }

        public static AppComponent get() {
            return sComponent;
        }
    }
}
