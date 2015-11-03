package lsxiao.com.zhihudailyrrd.common;


import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * 支持直接返回字符串的处理
 *
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class JsonConverter extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return super.fromResponseBody(type, annotations);
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return super.toRequestBody(type, annotations);
    }
}