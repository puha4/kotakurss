package com.kotakurss.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kotakurss.app.R;
import com.kotakurss.app.image.DownloadAsyncTask;
import com.kotakurss.app.image.ViewHolder;
import com.kotakurss.app.rss.FeedMessage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FeedMessageAdapter extends BaseAdapter {

    private List<FeedMessage> list;
    private LayoutInflater layoutInflater;

    public FeedMessageAdapter(Context context, List<FeedMessage> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = layoutInflater.inflate(R.layout.item_layout_pro, parent, false);
        }

        FeedMessage feedMessage = getFeedMessage(position);

        TextView textViewTitle = (TextView) view.findViewById(R.id.title);
        textViewTitle.setText(feedMessage.getTitle());

        TextView textViewDate = (TextView) view.findViewById(R.id.date);
        textViewDate.setText(feedMessage.getDate());

        ImageView imageView = (ImageView) view.findViewById(R.id.picture);
        imageView.setImageResource(R.mipmap.android);
//        ViewHolder viewHolder = new ViewHolder(imageView, feedMessage.getImgUrl());

//        new DownloadAsyncTask().execute(viewHolder);

        return view;
    }

    private FeedMessage getFeedMessage(int position) {
        return (FeedMessage) getItem(position);
    }
}
