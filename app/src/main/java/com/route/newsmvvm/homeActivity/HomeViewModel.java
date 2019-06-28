package com.route.newsmvvm.homeActivity;

import android.app.Application;

import com.route.newsmvvm.API.APIManager;
import com.route.newsmvvm.API.Model.ArticlesItem;
import com.route.newsmvvm.API.Model.NewsResponse;
import com.route.newsmvvm.API.Model.SourcesItem;
import com.route.newsmvvm.API.Model.SourcesResponse;
import com.route.newsmvvm.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Nabil Mohamed on 6/28/2019.
 * m.nabil.fci2015@gmail.com
 */
public class HomeViewModel extends AndroidViewModel {

    public MutableLiveData<List<SourcesItem>> sources=new MutableLiveData<>();
    public MutableLiveData<Boolean> showLoading=new MutableLiveData<>();
    public MutableLiveData<List<ArticlesItem>> articles=new MutableLiveData<>();
    public MutableLiveData<String> alertMessage=new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadNewsSource(){
        showLoading.setValue(true);
        APIManager.
                getApis().
                getNewsSources(Constants.API_KEY,Constants.LANGUAGE)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                        showLoading.setValue(false);

                        if(response.body()!=null){
                            sources.postValue(response.body().getSources());
                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        showLoading.setValue(false);
                        alertMessage.setValue(t.getLocalizedMessage());

                    }
                });
    }

    public void loadNewsBySourceId(String sourceId){
        showLoading.setValue(true);
        APIManager.getApis()
                .getNews(Constants.API_KEY,Constants.LANGUAGE,sourceId)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        showLoading.setValue(false);
                        if(response.body()!=null)
                        articles.setValue(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        showLoading.setValue(false);
                        alertMessage.setValue(t.getLocalizedMessage());

                    }
                });
    }
}
