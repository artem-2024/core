package com.oreo.coresample.presenter;

import com.android.core.base.BaseCallBack;
import com.android.core.model.LoadEveryLogicImpl;
import com.oreo.coresample.api.Factory;
import com.oreo.coresample.model.Classify;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author: oreo
 * @date: 2016-08-01 15:13
 * @github https://github.com/oreo
 */
public class DataPresenter extends LoadEveryLogicImpl<Classify> implements DataContract {
    @Override
    public void getPackageList(int page,int tag) {
        Factory.provideHttpService().getImageClassify(page).enqueue(new BaseCallBack<Classify>(this,tag) {
            @Override
            protected void onResponseSuccess(Call<Classify> call, Response<Classify> response, int tag) {
                onLoadCompleteData(response.body(),tag);
            }
        });
    }



}
