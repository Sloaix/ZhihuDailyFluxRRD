package lsxiao.com.zhihudailyrrd.inject.component;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import lsxiao.com.zhihudailyrrd.base.BaseActivity;
import lsxiao.com.zhihudailyrrd.base.BaseDialogFragment;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.inject.module.ApplicationModule;
import lsxiao.com.zhihudailyrrd.service.base.BaseManager;

/**
 * @author lsxiao
 * @date 2015-11-04 00:47
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    void inject(BaseDialogFragment dialogFragment);

    void inject(BaseManager manager);

    Application getApplication();

    class Instance {
        private static ApplicationComponent sComponent;

        public static void init(@NonNull ApplicationComponent component) {
            sComponent = component;
        }

        public static ApplicationComponent get() {
            return sComponent;
        }
    }
}
