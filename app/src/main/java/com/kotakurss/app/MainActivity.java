package com.kotakurss.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.kotakurss.app.adapter.FeedMessageAdapter;
import com.kotakurss.app.rss.Feed;
import com.kotakurss.app.rss.FeedMessage;
import com.kotakurss.app.rss.RSSFeedParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    static final String RSS_URL = "http://kotaku.com/rss";
    private Feed feed;
    private ListView rssListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssListView = (ListView) findViewById(R.id.rssListView);

        feed = getFeed();

        List <FeedMessage> feedMessagesList = feed.getMessages();

        FeedMessageAdapter messageAdapter = new FeedMessageAdapter(this, feedMessagesList);

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
