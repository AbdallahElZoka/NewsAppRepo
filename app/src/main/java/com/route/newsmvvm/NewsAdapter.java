package com.route.newsmvvm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.route.newsmvvm.API.Model.ArticlesItem;

import java.util.List;

/**
 * Created by Mohamed Nabil Mohamed on 6/21/2019.
 * m.nabil.fci2015@gmail.com
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    List<ArticlesItem> articlesItems;

    public NewsAdapter(List<ArticlesItem> articlesItems) {
        this.articlesItems = articlesItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_news_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        ArticlesItem item=articlesItems.get(pos);
        viewHolder.title.setText(item.getTitle());
        viewHolder.date.setText(item.getPublishedAt());
        //image ->> glide
        Glide.with(viewHolder.itemView)
                .load(item.getUrlToImage())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if(articlesItems==null)return 0;
        return articlesItems.size();
    }

    public void changeData(List<ArticlesItem> items){
        articlesItems=items;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.image_view);
        }
    }
}
