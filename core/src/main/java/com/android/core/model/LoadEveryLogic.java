package com.android.core.model;

import com.android.core.base.BaseView;

/**
 * @author: 蜡笔小新
 * @date: 2016-08-01 15:03
 * @GitHub: https://github.com/meikoz
 */
public interface LoadEveryLogic<T> {

    void onLoadCompleteData( T body,int tag);

    void onFailer(String msg,int tag);

    interface LoadEveryView<T> extends BaseView {
        void onLoadComplete( T body,int tag);

        void onLoadFailer(String msg,int tag);
    }
}
