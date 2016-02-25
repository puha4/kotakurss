package com.kotakurss.app.rss;

public class FeedMessage {

    private String title;
    private String description;
    private String link;
    private String date;
    private String imgUrl;

    public FeedMessage(String title, String description, String link, String date, String imgUrl) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
