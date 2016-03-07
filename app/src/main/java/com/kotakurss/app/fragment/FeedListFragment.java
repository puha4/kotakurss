package com.kotakurss.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.kotakurss.app.R;
import com.kotakurss.app.adapter.FeedMessageAdapter;
import com.kotakurss.app.rss.Feed;
import com.kotakurss.app.rss.FeedMessage;
import com.kotakurss.app.rss.RSSFeedParser;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FeedListFragment extends Fragment {
    private ListView rssListView;
    private Feed feed;
    private View view;
    private FeedMessageAdapter messageAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String RSS_URL = "http://kotaku.com/rss";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feedlist_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipeRefreshColor);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                initRssList();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRssList();
            }
        });

        return view;
    }

    private void initRssList() {
        swipeRefreshLayout.setRefreshing(true);

        rssListView = (ListView) view.findViewById(R.id.rssListView);
        feed = getFeed();

        List<FeedMessage> feedMessagesList = feed.getMessages();

        messageAdapter = new FeedMessageAdapter(this.getActivity().getApplicationContext(), feedMessagesList);

        rssListView.setAdapter(messageAdapter);

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedMessage itemAtPosition = (FeedMessage) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString("url", itemAtPosition.getLink());

                showOtherFragment(bundle);
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    public void setSearchQuery(String searchQuery) {
        messageAdapter.getFilter(searchQuery);
    }

    private Feed getFeed() {
        try {
            feed = new RSSFeedParser().execute(RSS_URL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return feed;
    }

    public void showOtherFragment(Bundle bundle)
    {
        Fragment fr = new ViewerFragment();
        fr.setArguments(bundle);

        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr);
    }
}
