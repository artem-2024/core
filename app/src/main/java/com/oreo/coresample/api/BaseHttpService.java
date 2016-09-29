package com.oreo.coresample.api;

import com.oreo.coresample.model.Classify;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: Oreo
 * @date: 2016-08-22 10:50
 */
public interface BaseHttpService {


   /* @FormUrlEncoded
    @POST("{target}/{method}")
    Call<EmployeeList> getEmployeeList(@Path("target") String data, @Path("method") String groupList, @Field("data") String token);*/

    @GET("tnfs/api/list")
    Call<Classify> getImageClassify(@Query("id") int id);

}
