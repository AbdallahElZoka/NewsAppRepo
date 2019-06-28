package com.route.newsmvvm.homeActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.route.Base.BaseActivity;
import com.route.newsmvvm.API.Model.ArticlesItem;
import com.route.newsmvvm.API.Model.SourcesItem;
import com.route.newsmvvm.NewsAdapter;
import com.route.newsmvvm.R;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends BaseActivity {

    TabLayout tabLayout;
    RecyclerView recyclerView;
    List<ArticlesItem> articles;
    NewsAdapter newsAdapter;

    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialize view Model
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        initView();
        initRecyclerView();
        homeViewModel.loadNewsSource();
        subscribeToLiveData();
    }

    public void initView(){
        tabLayout=findViewById(R.id.tablayout);
        recyclerView=findViewById(R.id.recycler_view);
    }

    public void subscribeToLiveData(){
        homeViewModel.sources.observe(this, new Observer<List<SourcesItem>>() {
            @Override
            public void onChanged(List<SourcesItem> sourcesItems) {
                setTabLayoutWithNewsSources(sourcesItems);
            }
        });

        homeViewModel.showLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if(show)
                    showProgressBar(R.string.loading);
                else hideProgressDialog();
            }
        });
        homeViewModel.articles.observe(this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                newsAdapter.changeData(articlesItems);
            }
        });
        homeViewModel.alertMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                showMessage(null,message,getString(R.string.ok));
            }
        });
    }

    public void initRecyclerView(){
        newsAdapter=new NewsAdapter(null);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }


    private void setTabLayoutWithNewsSources(final List<SourcesItem> sources) {
        for (int i=0;i<sources.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(sources.get(i).getName());
            tab.setTag(sources.get(i));
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               /* int tabPos=tab.getPosition();
                loadNewsBySourceId(sources.get(tabPos).getId());*/
                SourcesItem item=((SourcesItem) tab.getTag());
                homeViewModel.loadNewsBySourceId(item.getId());
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
        tabLayout.getTabAt(0).select();
    }

}
