package com.example.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tasneem on 10/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsData>{
    private static final String DATE_TIME_SEPARATOR="T";
    public NewsAdapter(Context context,List<NewsData> newses){
        super(context,0,newses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.custom_adapter,parent,false);
        }
        NewsData currentNewsData=getItem(position);
        TextView sectionTextView=(TextView) listItemView.findViewById(R.id.section);
        sectionTextView.setText(currentNewsData.getNews_section());
        TextView dateTextView=(TextView) listItemView.findViewById(R.id.date);
        TextView timeTextView=(TextView) listItemView.findViewById(R.id.time);
        TextView titleTextView=(TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentNewsData.getNews_title());
        String publicationDate=currentNewsData.getNews_date();
        String date;
        String time;
        if(publicationDate.contains(DATE_TIME_SEPARATOR)){
            String[] parts=publicationDate.split(DATE_TIME_SEPARATOR);
            date=parts[0];
            time=parts[1];
        }
        else {
            date=publicationDate;
            time=getContext().getString(R.string.unknown_time);
        }
        dateTextView.setText(date);
        timeTextView.setText(time);
        return listItemView;
    }
}
