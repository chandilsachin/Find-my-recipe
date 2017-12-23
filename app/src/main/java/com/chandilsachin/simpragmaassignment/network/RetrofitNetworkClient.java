package com.chandilsachin.simpragmaassignment.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkClient {

    public static String BASE_URL = "http://www.recipepuppy.com/";

    private static Retrofit retrofit = null;

    private static Retrofit getClient(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Service getService(){
        return getClient().create(Service.class);
    }
}
