package com.route.newsmvvm.API;

import com.route.newsmvvm.API.Model.NewsResponse;
import com.route.newsmvvm.API.Model.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Nabil Mohamed on 6/21/2019.
 * m.nabil.fci2015@gmail.com
 */
public interface WebServices {

    @GET("sources")
   Call<SourcesResponse>  getNewsSources(@Query("apiKey") String apikey,
                                         @Query("language") String language);

    @GET("everything")
    Call<NewsResponse> getNews(@Query("apiKey") String apikey,
                               @Query("language") String language,
                               @Query("sources") String sources);
}
