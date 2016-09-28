package com.oreo.coresample.presenter;

import com.android.core.model.LoadEveryLogicImpl;
import com.oreo.coresample.model.JSR_Base;

/**
 * @author: oreo
 * @date: 2016-08-01 15:13
 * @github https://github.com/oreo
 */
public class DataPresenter extends LoadEveryLogicImpl<JSR_Base> implements DataContract {
    @Override
    public void getPackageList() {
       /* Factory.provideHttpService().getPackageList(ApiConstant.Url.DATA, ApiConstant.Url.PACKAGE_LIST,
                encoding("token", APP_Sx.user.data.token)).enqueue(new BaseCallBack<PackageList>(this, 0) {
            @Override
            public void onResponseSuccess(Call<PackageList> call, Response<PackageList> response, int tag) {
                if (!ApiConstant.TIMEOUT_HTTPCODE.equals(response.body().errcode) && response.body().data == null) {
                    onFailer(ApiConstant.NO_DATA, tag);
                } else onLoadCompleteData(response.body(), tag);
            }
        });*/
    }


}
