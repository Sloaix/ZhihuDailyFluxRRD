package lsxiao.com.zhihudailyrrd.inject.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lsxiao.com.zhihudailyrrd.protocol.ClientAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xiaolishang
 * @date 2015-11-03 23:28
 */
@Module
public class ClientAPIModule {
    private static final String API_VERSION = "4";
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    /**
     * 创建一个ClientAPI的实现类单例对象
     *
     * @param client           OkHttpClient
     * @param converterFactory Converter.Factory
     * @return ClientAPI
     */
    @Provides
    @Singleton
    public ClientAPI provideClientApi(OkHttpClient client, Converter.Factory converterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ClientAPI.class);
    }

    /**
     * 日志拦截器单例对象,用于OkHttp层对日志进行处理
     *
     * @return HttpLoggingInterceptor
     */
    @Provides
    @Singleton
    public HttpLoggingInterceptor provideLogger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }


    /**
     * Gson转换器单例对象
     *
     * @param gson Gson
     * @return Converter.Factory
     */
    @Provides
    @Singleton
    public Converter.Factory provideConverter(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    /**
     * Gson 单例对象
     *
     * @return Gson
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    /**
     * OkHttp客户端单例对象
     *
     * @param loggingInterceptor HttpLoggingInterceptor
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    public OkHttpClient provideClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return client;
    }
}
