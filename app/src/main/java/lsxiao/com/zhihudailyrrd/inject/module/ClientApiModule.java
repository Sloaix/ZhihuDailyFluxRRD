package lsxiao.com.zhihudailyrrd.inject.module;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lsxiao.com.zhihudailyrrd.common.JacksonConverterFactory;
import lsxiao.com.zhihudailyrrd.protocol.ClientApi;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * @author xiaolishang
 * @date 2015-11-03 23:28
 */
@Module
public class ClientApiModule {
    private static final String API_VERSION = "4";
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    @Provides
    @Singleton
    public ClientApi provideClientApi(OkHttpClient client, JacksonConverterFactory converterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ClientApi.class);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providerLog() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    @Singleton
    public JacksonConverterFactory providerConverter() {
        return JacksonConverterFactory.create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(loggingInterceptor);
        return client;
    }

}
