package com.oreo.coresample.ui;

import android.os.Bundle;

import com.android.core.base.AbsBaseActivity;
import com.android.core.control.ToastUtil;
import com.android.core.model.LoadEveryLogic;
import com.oreo.coresample.R;
import com.oreo.coresample.model.Classify;
import com.oreo.coresample.presenter.DataContract;

/**
 * https://github.com/oreo
 */
public class MainActivity extends AbsBaseActivity implements LoadEveryLogic.LoadEveryView<Classify> {


    // before the super.setContentView()
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected Class getLogic() {
        return DataContract.class;
    }

    // after the super.setContentView()
    @Override
    protected void initViewOrData() {
        super.initViewOrData();
        showProgress("load data");
        ((DataContract)mPresenter).getPackageList(1,1);
    }

    @Override
    public void onLoadComplete(Classify body, int tag) {
        ToastUtil.show(body.toString());
    }

    @Override
    public void onLoadFailer(String msg, int tag) {

    }
}
