package com.oreo.coresample.ui;

import android.os.Bundle;

import com.android.core.base.AbsBaseActivity;
import com.android.core.model.LoadEveryLogic;
import com.oreo.coresample.R;
import com.oreo.coresample.model.JSR_Base;

/**
 * https://github.com/oreo
 */
public class MainActivity extends AbsBaseActivity implements LoadEveryLogic.LoadEveryView<JSR_Base> {


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
        return null;
    }

    @Override
    protected void initViewOrData() {
        super.initViewOrData();
    }

    @Override
    public void onLoadComplete(JSR_Base body, int tag) {

    }

    @Override
    public void onLoadFailer(String msg, int tag) {

    }
}
