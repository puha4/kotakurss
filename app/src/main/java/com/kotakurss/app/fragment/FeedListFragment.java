package com.kotakurss.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kotakurss.app.R;
import com.kotakurss.app.adapter.FeedMessageRecycleAdapter;
import com.kotakurss.app.rss.Feed;
import com.kotakurss.app.rss.FeedMessage;
import com.kotakurss.app.rss.RSSFeedParser;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FeedListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Feed feed;
    private View view;
    private FeedMessageRecycleAdapter messageAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String RSS_URL = "http://kotaku.com/rss";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feedlist_fragment, container, false);

        hideBackToolbarButton();

        initSwipeRefreshLayout();

        return view;
    }

    private void hideBackToolbarButton() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipeRefreshColor);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                initRssList();
            }
        });

        setOnSwipeRefreshListener();
    }

    private void setOnSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRssList();
            }
        });
    }

    private void initRssList() {
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));

        feed = getFeed();
        List<FeedMessage> feedMessagesList = feed.getMessages();

        messageAdapter = new FeedMessageRecycleAdapter(feedMessagesList);
        recyclerView.setAdapter(messageAdapter);

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
}
