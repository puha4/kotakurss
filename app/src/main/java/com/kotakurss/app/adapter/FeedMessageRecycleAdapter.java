package com.kotakurss.app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kotakurss.app.R;
import com.kotakurss.app.fragment.FragmentChangeListener;
import com.kotakurss.app.fragment.ViewerFragment;
import com.kotakurss.app.image.ImageLoader;
import com.kotakurss.app.rss.FeedMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FeedMessageRecycleAdapter extends RecyclerView.Adapter<FeedMessageRecycleAdapter.FeedViewHolder> {
    private List<FeedMessage> list;
    private ArrayList<FeedMessage> container;
    private ImageLoader imageLoader;
    private Context context;

    public FeedMessageRecycleAdapter(List<FeedMessage> list) {
        this.list = list;
        this.container = new ArrayList<FeedMessage>();
        this.container.addAll(list);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);

        context = viewGroup.getContext();
        imageLoader = new ImageLoader(viewGroup.getContext());

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder feedViewHolder, int i) {
        final FeedMessage feedMessage = list.get(i);
        feedViewHolder.title.setText(feedMessage.getTitle());

        String formattedDate = getFormattedDate(feedMessage.getDate());
        feedViewHolder.date.setText(formattedDate);

        if (feedMessage.getImgUrl().isEmpty()) {
            feedViewHolder.imageView.setVisibility(View.GONE);
        } else {
            feedViewHolder.imageView.setVisibility(View.VISIBLE);
            imageLoader.DisplayImage(feedMessage.getImgUrl(), feedViewHolder.imageView);
        }

        feedViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", feedMessage.getLink());
                showOtherFragment(bundle);
            }
        });
    }

    private String getFormattedDate(String date) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.US);
        SimpleDateFormat toFormat = new SimpleDateFormat("dd MMM yyyy hh:mm", Locale.US);

        String formattedDate = date;

        try {
            formattedDate = toFormat.format(fromFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public void showOtherFragment(Bundle bundle) {
        Fragment fr = new ViewerFragment();
        fr.setArguments(bundle);

        FragmentChangeListener fc = (FragmentChangeListener) context;
        fc.replaceFragment(fr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView date;
        ImageView imageView;

        public FeedViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.messageTitle);
            date = (TextView) itemView.findViewById(R.id.messageDate);
            imageView = (ImageView) itemView.findViewById(R.id.messagePicture);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    public void getFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(container);
        } else {
            for (FeedMessage feedMessage : container) {
                if (feedMessage.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(feedMessage);
                }
            }
        }
        notifyDataSetChanged();
    }
}
