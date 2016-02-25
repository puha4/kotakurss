package com.kotakurss.app.image;

import android.widget.ImageView;

public class ViewHolder {
    private ImageView imageView;
    private String imgUrl;

    public ViewHolder(ImageView imageView, String imgUrl) {
        this.imageView = imageView;
        this.imgUrl = imgUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
