package com.kotakurss.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private EditText inputSearch;

    private static final String RSS_URL = "http://kotaku.com/rss";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feedlist_fragment, container, false);

        initRssList();
        initSearch();

        return view;
    }

    private void initRssList() {
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
    }

    private void initSearch() {
        inputSearch = (EditText) view.findViewById(R.id.searchInput);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messageAdapter.getFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
