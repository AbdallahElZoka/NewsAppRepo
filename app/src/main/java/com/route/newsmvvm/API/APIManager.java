package com.route.newsmvvm.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Nabil Mohamed on 6/21/2019.
 * m.nabil.fci2015@gmail.com
 */
public class APIManager {
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        //b157611e9895467ebfb67ff2c10f9c5b
        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WebServices getApis(){
        return  getInstance().create(WebServices.class);
    }
}
