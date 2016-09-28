package com.android.core.api;

import com.android.core.util.json.FastJsonConverterFactory;

import retrofit2.Retrofit;

/**
 * @author: 蜡笔小新
 * @date: 2016-05-31 14:15
 * @GitHub: https://github.com/meikoz
 */
public class HttpClient {

    private static HttpClient mInstance;
    private Retrofit singleton;

    public static HttpClient getIns(String base_url) {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) mInstance = new HttpClient(base_url);
            }
        }
        return mInstance;
    }
    //public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();



    public HttpClient(String BASE_URL) {
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")..create();

        singleton = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(OKHttpHelper.create())
                .build();
    }

    public <T> T createService(Class<T> clz) {
        return singleton.create(clz);
    }
}
