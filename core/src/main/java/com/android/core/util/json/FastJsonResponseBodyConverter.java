package com.android.core.util.json;

import com.alibaba.fastjson.JSON;
import com.android.core.util.CodeUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/7/28.
 */
public class FastJsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {

    private Type type;
    private Charset charset;

    public FastJsonResponseBodyConverter() {
    }

    public FastJsonResponseBodyConverter(Type type, Charset charset) {
        this.type = type;
        this.charset = charset;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        try {
            return JSON.parseObject(CodeUtils.decodeToSgtring(value.string()), type);
        } finally {
            value.close();
        }
    }
}
