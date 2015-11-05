package lsxiao.com.zhihudailyrrd.common;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * 支持直接返回字符串的处理
 *
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class JacksonConverterFactory extends Converter.Factory {

    /**
     * Create an instance using a default {@link ObjectMapper} instance for conversion.
     */
    public static JacksonConverterFactory create() {
        return create(new ObjectMapper());
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    public static JacksonConverterFactory create(ObjectMapper mapper) {
        return new JacksonConverterFactory(mapper);
    }

    private final ObjectMapper mapper;

    private JacksonConverterFactory(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        this.mapper = mapper;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectReader reader = mapper.reader(javaType);
        return new JacksonResponseBodyConverter<>(reader);
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerWithType(javaType);
        return new JacksonRequestBodyConverter<>(writer);
    }

    final class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final ObjectReader adapter;

        JacksonResponseBodyConverter(ObjectReader adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Reader reader = value.charStream();
            try {
                return adapter.readValue(reader);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    final class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        private final ObjectWriter adapter;

        JacksonRequestBodyConverter(ObjectWriter adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            byte[] bytes = adapter.writeValueAsBytes(value);
            return RequestBody.create(MEDIA_TYPE, bytes);
        }
    }

}