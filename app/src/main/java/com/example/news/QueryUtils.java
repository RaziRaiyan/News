package com.example.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasneem on 10/10/2017.
 */

public class QueryUtils {
    private static final String LOG_TAG=QueryUtils.class.getSimpleName();
    public QueryUtils(){

    }
    public static List<NewsData> fetchNewsData(String requestUrl){
        URL url=createUrl(requestUrl);
        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem making Http request",e);
        }
        List<NewsData> newses=extractFeatureFromJson(jsonResponse);
        return  newses;
    }

    private static String makeHttpRequest(URL url)throws IOException {
        String jsonResponse=null;
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            /*urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000  milliseconds );
            urlConnection.setConnectTimeout(15000 milliseconds );
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();*/
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStraem(inputStream);
            }
            else{
                Log.e(LOG_TAG,"Error response code:"+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retreiving NewsData JSON result");
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStraem(InputStream inputStream)throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader isr=new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(isr);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url=null;
        try{
            url=new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem Building url",e);
        }
        return url;
    }


    private static List<NewsData> extractFeatureFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<NewsData> newses=new ArrayList<>();
        try{
            JSONObject baseJsonObject=new JSONObject(jsonResponse);
            JSONObject responseObject=baseJsonObject.getJSONObject("response");
            JSONArray newsArray=responseObject.getJSONArray("results");
            for(int i=0;i<newsArray.length();i++){
                JSONObject currentNewsData=newsArray.getJSONObject(i);
                String section=currentNewsData.getString("sectionName");
                String date=currentNewsData.getString("webPublicationDate");
                String url=currentNewsData.getString("webUrl");
                String title=currentNewsData.getString("webTitle");
                newses.add(new NewsData(title,date,url,section));
            }
        }catch (JSONException e){
            Log.e(LOG_TAG,"Problem parsing the newsdata JSON result",e);
        }
        return newses;
    }

}
