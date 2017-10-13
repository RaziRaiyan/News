package com.example.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by tasneem on 10/11/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {
    private static final String LOG_TAG=NewsLoader.class.getSimpleName();
    private String mUrl;
    public NewsLoader(Context context,String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsData> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        List<NewsData> Newses=QueryUtils.fetchNewsData(mUrl);
        return Newses;
    }
}
