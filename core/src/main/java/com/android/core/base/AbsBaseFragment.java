package com.android.core.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.core.control.ToastUtil;
import com.android.core.control.logcat.Logcat;
import com.android.core.control.rx.RxBus;
import com.android.core.model.LogicProxy;
import com.android.core.widget.dialog.DialogManager;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author: 蜡笔小新
 * @date: 2016-05-26 17:19
 * @GitHub: https://github.com/meikoz
 *
 * edit change by oreo at 160820
 * https://github.com/oreo
 */
public abstract class AbsBaseFragment extends Fragment implements BaseView {

    protected BasePresenter mPresenter;
    protected Context mContext;
    protected View mContentView;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getLayoutResource() != 0) {
            mContentView=inflater.inflate(getLayoutResource(), null);
        } else {
            mContentView=super.onCreateView(inflater, container, savedInstanceState);
        }

        init(savedInstanceState);
        return mContentView;
    }
    protected <T extends View> T $(int id) {
        return (T) mContentView.findViewById(id);
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logcat.d("Fragment Location (%s.java:0)", getClass().getSimpleName());
        mContext = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife.bind(this, view);

        mSubscription = RxBus.getInstance().toObserverable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                handleRxMsg(s);
            }
        });
        initViewOrData();
    }
    protected void handleRxMsg(String msg) {}

    protected abstract int getLayoutResource();


    /**
     * Initialize the view in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    protected void init(Bundle savedInstanceState){}

    /**
     * init view's events or data
     */
    protected void initViewOrData(){if(getLogic()!=null)mPresenter=getLogicImpl();}

    protected abstract Class getLogic();
    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogic(), this);
    }

   @Override
   public void onDestroy() {
       if(mSubscription != null)
           mSubscription.unsubscribe();
       if (mPresenter != null)
           mPresenter.detachView();
       //ButterKnife.unbind(this);
       super.onDestroy();
   }

    @Override
    public void onStart() {
        if(mPresenter!=null&&!mPresenter.isViewBind()){
            LogicProxy.getInstance().bind(getLogic(), this);
        }
        super.onStart();
    }

    protected void setOnClickEvent(final View v, final View.OnClickListener event){
        if(v==null||event==null){
            System.out.println("your view or event is null");
            return;
        }
        RxView.clicks(v)
                .throttleFirst(2, TimeUnit.MILLISECONDS)//两秒内只取一个点击事件
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        event.onClick(v);
                    }
                });
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showProgress(String msg) {
        DialogManager.showProgressDialog(mContext, msg);
    }

    @Override
    public void showProgress(String msg, int progress) {
        DialogManager.showProgressDialog(mContext, msg, progress);
    }

    @Override
    public void hideProgress() {
        DialogManager.hideProgressDialog();
    }

    @Override
    public void showErrorMessage(String msg, String content) {
        DialogManager.showErrorDialog(mContext, msg, content, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
}
