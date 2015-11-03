package lsxiao.com.zhihudailyrrd.inject.component;

import javax.inject.Singleton;

import dagger.Component;
import lsxiao.com.zhihudailyrrd.inject.module.ClientApiModule;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
@Singleton
@Component(modules = {
        ClientApiModule.class
})
public interface ProClientComponent extends ClientApiComponent {

}
