package com.android.core.model;

import com.android.core.base.BasePresenter;

/**
 * @author: 蜡笔小新
 * @date: 2016-08-01 15:02
 * @GitHub: https://github.com/meikoz
 *
 * Edit change by oreo at 160822
 */
public class LoadEveryLogicImpl<T> extends BasePresenter<LoadEveryLogic.LoadEveryView> implements LoadEveryLogic<T> {



    @Override
    public void onLoadCompleteData( T body,int tag) {
        if(getView()==null){
            System.out.println("your view is null");
            return;
        }
        getView().hideProgress();
        if (body != null)
            getView().onLoadComplete(body,tag);
    }

    @Override
    public void onFailer(String msg,int tag) {
        if(getView()==null){
            System.out.println("your view is null");
            return;
        }
        getView().hideProgress();
        getView().onLoadFailer(msg,tag);
    }
}
