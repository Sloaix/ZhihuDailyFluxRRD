package lsxiao.com.zhihudailyrrd.inject.component;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import lsxiao.com.zhihudailyrrd.base.BaseActivity;
import lsxiao.com.zhihudailyrrd.base.BaseDialogFragment;
import lsxiao.com.zhihudailyrrd.base.BaseFragment;
import lsxiao.com.zhihudailyrrd.inject.module.DataLayerModule;

/**
 * @author lsxiao
 * @date 2015-11-04 00:47
 */
@Singleton
@Component(dependencies = DataLayerModule.class)
public interface DataLayerComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    void inject(BaseDialogFragment dialogFragment);


    class Instance {
        private static DataLayerComponent sComponent;

        public static void init(@NonNull DataLayerComponent component) {
            sComponent = component;
        }

        public static DataLayerComponent get() {
            return sComponent;
        }
    }
}
