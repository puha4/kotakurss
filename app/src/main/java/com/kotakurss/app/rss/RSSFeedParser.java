package com.kotakurss.app.rss;

import android.os.AsyncTask;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RSSFeedParser extends AsyncTask<String, Void, Void> {
    private URL url;
    final String LOG_TAG = "myLogs";

    @Override
    protected Void doInBackground(String... urls) {
        try {
            this.url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        readFeed();

        return null;
    }

    public void readFeed() {
        String text = "blabla";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(this.url.openConnection().getInputStream(), "UTF_8");

            int event = xpp.getEventType();

            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name = xpp.getName();

                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.END_TAG:

                        if(xpp.getDepth() != 4) break;

                        if(name.equals("title")){
                            Log.i(LOG_TAG, text +" "+ xpp.getDepth());
                        }
                        if(name.equals("link")){
                            Log.i(LOG_TAG, text +" "+ xpp.getDepth());
                        }
                        if(name.equals("description")){
                            Log.i(LOG_TAG, text +" "+ xpp.getDepth());
                            Log.i("========","========");
                        }
                        // создаем FeedMessage и добавляем в Feed

                        break;

                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;

                    default:
                        break;
                }

                event = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
