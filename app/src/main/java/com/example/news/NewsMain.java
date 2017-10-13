package com.example.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsMain extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>> {

    private static final String LOG_TAG=NewsMain.class.getSimpleName();
    private static final String NEWS_REQUEST_URL="https://content.guardianapis.com/search?api-key=86379673-6d23-4746-a840-9a01b2e89e01";
    private NewsAdapter mAdapter;
    private static  final int NEWS_LOADER_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView=(ListView) findViewById(R.id.list);

        mAdapter=new NewsAdapter(this,new ArrayList<NewsData>());
        newsListView.setAdapter(mAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsData currentNewsData=mAdapter.getItem(position);
                Uri newsUri=Uri.parse(currentNewsData.getNews_url());
                Intent webIntent=new Intent(Intent.ACTION_VIEW,newsUri);
                startActivity(webIntent);
            }
        });
        ConnectivityManager connMgr=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected())
            getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
        else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<List<NewsData>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this,NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> newsDatas) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);mAdapter.clear();
        if(newsDatas!=null&&!newsDatas.isEmpty()){
            mAdapter.addAll(newsDatas);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {

    }
}
