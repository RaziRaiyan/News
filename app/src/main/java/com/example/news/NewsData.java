package com.example.news;

/**
 * Created by tasneem on 10/10/2017.
 */

public class NewsData {
    private String news_title;
    private String news_date;
    private String news_url;
    private String news_section;
    public NewsData(String title,String date,String url,String section){
        news_date=date;
        news_section=section;
        news_title=title;
        news_url=url;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_date() {
        return news_date;
    }

    public String getNews_url() {
        return news_url;
    }

    public String getNews_section() {
        return news_section;
    }
}
