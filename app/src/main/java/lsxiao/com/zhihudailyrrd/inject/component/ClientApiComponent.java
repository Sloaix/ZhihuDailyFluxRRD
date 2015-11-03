package lsxiao.com.zhihudailyrrd.inject.component;


import javax.inject.Singleton;

import dagger.Component;
import lsxiao.com.zhihudailyrrd.inject.module.ClientApiModule;
import lsxiao.com.zhihudailyrrd.service.impl.BaseManager;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
@Singleton
@Component(modules = ClientApiModule.class)
public interface ClientApiComponent {

    void inject(BaseManager manager);
    
    class Instance {
        private static ClientApiComponent sComponent;

        public static void init(ClientApiComponent component) {
            sComponent = component;
        }

        public static ClientApiComponent get() {
            return sComponent;
        }
    }

}
