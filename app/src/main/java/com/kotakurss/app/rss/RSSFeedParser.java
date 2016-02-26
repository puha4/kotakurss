package com.kotakurss.app.rss;

import android.os.AsyncTask;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSSFeedParser extends AsyncTask<String, Void, Feed> {

    private URL url;
    private final String TITLE = "title";
    private final String LINK = "link";
    private final String DESCRIPTION = "description";
    private final String PUBDATE = "pubDate";
    private static final String IMG_URL_PATTERN = ".*\\<img\\s*src\\s*=\\s*\\\"(.*?)\\\"(.|\\s)*";
    private static final int MATCHED_GROUP = 1;

    private final int CURRENT_URL = 0;
    private final int TRUE_XML_ITEM_DEPTH = 4;

    private String parsedTitle = "";
    private String parsedImgUrl = "";
    private String parsedLink = "";
    private String parsedDescription = "";
    private String parsedDate = "";

    private Feed feed = new Feed();

    private final String LOG_TAG = RSSFeedParser.class.getSimpleName();

    @Override
    protected Feed doInBackground(String... urls) {
        try {
            this.url = new URL(urls[CURRENT_URL]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        readFeed();

        return getFeed();
    }

    public void readFeed() {
        int event;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(this.url.openConnection().getInputStream(), "UTF_8");

            event = xpp.getEventType();
            checkEventTypeAndSetProperty(xpp, event);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkEventTypeAndSetProperty(XmlPullParser xpp, int event) throws IOException, XmlPullParserException {
        String tagText = "";

        while (event != XmlPullParser.END_DOCUMENT)
        {
            String tagName = xpp.getName();

            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    break;

                case XmlPullParser.END_TAG:
                    if(xpp.getDepth() != TRUE_XML_ITEM_DEPTH) break;

                    checkCurrentTagText(tagName, tagText);
                    break;

                case XmlPullParser.TEXT:
                    tagText = xpp.getText();
                    break;

                default:
                    break;
            }

            event = xpp.next();
        }
    }

    private void checkCurrentTagText(String tagName, String tagText) {
        if(tagName.equals(TITLE)){
            this.parsedTitle = tagText;
        }
        if(tagName.equals(LINK)){
            this.parsedLink = tagText;
        }
        if(tagName.equals(DESCRIPTION)){
            this.parsedDescription = tagText;

            if(isMatchImgUrlFromDescription(tagText))
                this.parsedImgUrl = getImgUrlFromDescription(tagText);
            else {
                this.parsedImgUrl = "";
            }

//            Log.i(LOG_TAG, tagText);
//            Log.i(LOG_TAG, getImgUrlFromDescription(tagText) +" "+ isMatchImgUrlFromDescription(tagText));
//            Log.i(LOG_TAG, "--------------");
        }
        if(tagName.equals(PUBDATE)){
            this.parsedDate = tagText;

            addMessage();
        }
    }

    private void addMessage() {
        FeedMessage feedMessage = new FeedMessage(this.parsedTitle, this.parsedDescription, this.parsedLink, this.parsedDate, this.parsedImgUrl);
        feed.getMessages().add(feedMessage);
    }

    private Feed getFeed() {
//        for (FeedMessage message : this.feed.getMessages())
//            Log.i(LOG_TAG, message.getTitle());
        return this.feed;
    }

    public boolean isMatchImgUrlFromDescription(String parsedDescription){
//        Pattern p = Pattern.compile(IMG_URL_PATTERN);
//        Matcher m = p.matcher(parsedDescription);
//Log.i(LOG_TAG, parsedDescription + parsedDescription.matches(IMG_URL_PATTERN));
        return parsedDescription.matches(IMG_URL_PATTERN);
    }

    private String getImgUrlFromDescription(String parsedDescription) {
        String matchedUrl = "";

        Pattern regexp = Pattern.compile(IMG_URL_PATTERN);
        Matcher m = regexp.matcher(parsedDescription);

        while (m.find()) {
//            Log.i(LOG_TAG, m.group(MATCHED_GROUP));
            matchedUrl = m.group(MATCHED_GROUP);
        }

        return matchedUrl;
    }


}
