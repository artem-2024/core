package com.android.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.android.core.control.logcat.Logcat;
import com.android.core.util.CodeUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 蜡笔小新
 * @date: 2016-06-21 17:41
 * @GitHub: https://github.com/meikoz
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T mView;

    @Override
    public void attachView(T mvpView) {
        this.mView = mvpView;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public boolean isViewBind() {
        return mView != null;
    }


    public T getView() {
        return mView;
    }

    protected String encoding(HashMap<String,String> para){
        try{
            JSONObject obj=new JSONObject();
            for(Map.Entry<String, String> kv : para.entrySet()){
                obj.put(kv.getKey(),kv.getValue());
            }
            return CodeUtils.encodeToString(obj.toString());
        }catch (Exception e){
            Logcat.e(e,"encoding error");
            e.printStackTrace();
            return "";
        }


    }
    protected String encoding(String key,Object value){
        try{
            JSONObject obj=new JSONObject();
            obj.put(key,value);
            return CodeUtils.encodeToString(obj.toString());
        }catch (Exception e){
            Logcat.e(e,"encoding error");
            e.printStackTrace();
            return "";
        }
    }
    protected String encoding(Object obj){
        return obj==null?"": CodeUtils.encodeToString(JSON.toJSONString(obj,SerializerFeature.BrowserCompatible).trim());
    }


}
