package com.kotakurss.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kotakurss.app.R;
import com.kotakurss.app.image.ImageLoader;
import com.kotakurss.app.rss.FeedMessage;

import java.util.List;

public class FeedMessageAdapter extends BaseAdapter {

    private List<FeedMessage> list;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private final String LOG_TAG = FeedMessageAdapter.class.getSimpleName();

    public FeedMessageAdapter(Context context, List<FeedMessage> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = new ImageLoader(context);
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
//        imageView.setImageResource(R.mipmap.android);

        imageLoader.DisplayImage(feedMessage.getImgUrl(), imageView);

        return view;
    }

    private FeedMessage getFeedMessage(int position) {
        return (FeedMessage) getItem(position);
    }
}
