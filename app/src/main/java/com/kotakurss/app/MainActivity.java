package com.kotakurss.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.kotakurss.app.adapter.FeedMessageAdapter;
import com.kotakurss.app.fragment.FeedListFragment;
import com.kotakurss.app.fragment.ViewerFragment;
import com.kotakurss.app.rss.Feed;
import com.kotakurss.app.rss.FeedMessage;
import com.kotakurss.app.rss.RSSFeedParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends FragmentActivity {

    static final String RSS_URL = "http://kotaku.com/rss";
    private Feed feed;
    private ListView rssListView;
    private WebView webView;
    private EditText inputSearch;
    private FeedMessageAdapter messageAdapter;

    private FeedListFragment feedListFragment;
    private ViewerFragment viewerFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedListFragment = new FeedListFragment();
        viewerFragment = new ViewerFragment();
        manager = getSupportFragmentManager();

        rssListView = (ListView) findViewById(R.id.rssListView);
        inputSearch = (EditText) findViewById(R.id.searchInput);
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);

        initRssList();

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

        initRssListOnClickListener();


    }

    private void initRssListOnClickListener() {
        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FeedMessage itemAtPosition = (FeedMessage) parent.getItemAtPosition(position);
//                Log.i("MainActivity", "" + itemAtPosition);
//                webView.loadUrl(itemAtPosition.getLink());

                Fragment f = new ViewerFragment();

                transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.feedlist_fragment, f);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void initRssList() {
        feed = getFeed();

        List<FeedMessage> feedMessagesList = feed.getMessages();

        messageAdapter = new FeedMessageAdapter(this, feedMessagesList);

        rssListView.setAdapter(messageAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
