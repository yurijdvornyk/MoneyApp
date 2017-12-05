package com.example.yuriidvornyk.moneyapp.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class CurrencyRateService {
    private static String BASE_URL = "http://spreadsheets.google.com/feeds/list/0Av2v4lMxiJ1AdE9laEZJdzhmMzdmcW90VWNfUTYtM2c/2/public/";

    // http://currencyfeed.com/
    public interface CurrencyRateApi {
        @GET("basic")
        Flowable<ResponseBody> getCurrencyRates(@Query("alt") String alt);
    }

    public CurrencyRateApi getAPI() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(CurrencyRateApi.class);
    }
}
