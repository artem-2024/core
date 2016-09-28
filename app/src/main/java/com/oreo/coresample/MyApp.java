package com.oreo.coresample;

import com.android.core.MainApp;
import com.android.core.control.logcat.BuildConfig;
import com.android.core.control.logcat.Logcat;



/**
 * Created by Oreo at 2016/7/31.
 * @GitHub https://github.com/oreo
 * <p/>
 * 1.ApiConstant
 * 2.model->result->JSR(JsonResult)
 * 3.BaseHttpService
 * 4.XxxContract
 * 5.XxxPresenter
 * 6.Activity
 */
public class MyApp extends MainApp {



    @Override
    public void onCreate() {
        super.onCreate();

        /*LogicProxy.getInstance().init(
                LoginContract.class, MainContract.class);

        //Android crash 上传服务器回掉
        HttpReportCallback report = new HttpReportCallback() {
            @Override
            public void uploadException2remote(File file) {
                //可以直接上传文件
            }
        };
        AndroidCrash.getInstance().setCrashReporter(report).init(this);*/

       /* LogicProxy.getInstance().init(
                LoginContract.class);*/
        //初始化logcat

        if (BuildConfig.DEBUG)
            Logcat.init("com.oreo.coresample").hideThreadInfo().methodCount(3);
    }


}
