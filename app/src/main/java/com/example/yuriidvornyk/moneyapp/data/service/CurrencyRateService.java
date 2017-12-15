package com.example.yuriidvornyk.moneyapp.data.service;

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
    public static final String DOCUMENT_ID = "1b3T-mkqrJWtcZW73i7YmaeSqnvEmFJNvo3EBsDOHhdg";
    private static final String BASE_URL = "https://spreadsheets.google.com/";

    // http://currencyfeed.com/
    // https://www.telerik.com/blogs/google-spreadsheet-as-data-source-android
    public interface CurrencyRateApi {
        @GET("tq")
        Flowable<ResponseBody> getCurrencyRates(@Query("key") String key);
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
