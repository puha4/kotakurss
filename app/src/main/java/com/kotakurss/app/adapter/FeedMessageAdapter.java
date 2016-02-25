package com.kotakurss.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kotakurss.app.R;
import com.kotakurss.app.rss.FeedMessage;

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
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }

        FeedMessage feedMessage = getFeedMessage(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(feedMessage.getTitle());

        return view;
    }

    private FeedMessage getFeedMessage(int position) {
        return (FeedMessage) getItem(position);
    }
}
