package com.android.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.android.core.control.ToastUtil;
import com.android.core.control.logcat.Logcat;
import com.android.core.control.rx.RxBus;
import com.android.core.model.LogicProxy;
import com.android.core.widget.dialog.DialogManager;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.functions.Action1;


/**
 * @author: 蜡笔小新
 * @date: 2016-05-26 17:17
 * @GitHub: https://github.com/meikoz
 *
 * Edit Change by Oreo at 160820
 * https://github.com/oreo
 */


public abstract class AbsBaseActivity extends AppCompatActivity implements BaseView {

    protected Context mContext = null;//context
    protected BasePresenter mPresenter;
    private Subscription mSubscription;

    private static final List<Activity> mActivities = new LinkedList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        mSubscription = RxBus.getInstance().toObserverable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                handleRxMsg(s);
            }
        });
        Logcat.i("Activity Location (%s.java:0)", getClass().getSimpleName());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        this.init(savedInstanceState);
        if(getLayoutResource()!=-1)setContentView(getLayoutResource());
       // ButterKnife.bind(this);
        this.initViewOrData();

    }
    protected void handleRxMsg(String msg) {

    }

    protected void setOnClickEvent(final View v,final View.OnClickListener event){
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




    /**
     * Initialize before the  {setContentView(..)}
     *
     * @param savedInstanceState savedInstanceState
     */
    protected  void init(Bundle savedInstanceState){}

    /**
     * init view's events or data
     */
    protected  void initViewOrData(){
        if(getLogic()!=null)mPresenter=getLogicImpl();
    }


    protected abstract int getLayoutResource();

    /**
     * Put a Contract
     * @return
     */
    protected abstract Class getLogic();


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // 打开Activity动画
    }

    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogic(), this);
    }

    @Override
    public void finish() {
        // 关闭动画
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        super.finish();
    }

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife.unbind(this);
        if(mSubscription != null)
            mSubscription.unsubscribe();
        if (mPresenter != null)
            mPresenter.detachView();
    }*/

    /*@Override
    protected void onResume() {
        if(mPresenter!=null&&!mPresenter.isViewBind()){
            LogicProxy.getInstance().bind(getLogic(), this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(mSubscription != null)
            mSubscription.unsubscribe();
        if (mPresenter != null)
            mPresenter.detachView();
        super.onPause();

    }*/

    @Override
    protected void onDestroy() {
        if(mSubscription != null)
            mSubscription.unsubscribe();
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        if(mPresenter!=null&&!mPresenter.isViewBind()){
            LogicProxy.getInstance().bind(getLogic(), this);
        }
        super.onStart();
    }


    @Override
    public void showMessage(String msg) {
        ToastUtil.show(msg);
    }

    public void showProgress(String message) {
        DialogManager.showProgressDialog(mContext, message);
    }

    @Override
    public void showProgress(String message, int progress) {
        DialogManager.showProgressDialog(mContext, message, progress);
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
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }
    /*
    杀死所有Activity
     */
    public static void killAll() {
        List<Activity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<>(mActivities);
        }
        for (Activity activity : copy) {
            activity.finish();
        }
    }


}
