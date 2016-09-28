package com.android.core.base;

import com.android.core.model.LoadEveryLogic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Oreo on 2016/9/2.
 * @Github https://github.com/oreo
 * @description
 */
public abstract class BaseCallBack<T> implements Callback<T>{
    private LoadEveryLogic logic;
    //请求标记  the call tag
    private int tag;

    public BaseCallBack(LoadEveryLogic logic,int tag){
        this.logic=logic;
        this.tag=tag;
    }

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        if(!response.isSuccessful()){
            onFailure(null,new Exception("successful is false"));
            return;
        }
        onResponseSuccess(call,response,tag);

    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        logic.onFailer(t.getMessage(),tag);
    }
    /**
     *
     * @param call  请求体
     * @param response 响应结果 call response
     * @param tag   请求标记 call tag
     */
    protected abstract void onResponseSuccess(Call<T> call, Response<T> response,int tag);
}
