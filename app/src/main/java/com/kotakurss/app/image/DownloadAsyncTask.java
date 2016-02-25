package com.kotakurss.app.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.kotakurss.app.R;

import java.io.IOException;
import java.net.URL;

public class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
    private Bitmap imageBitmap;

    @Override
    protected ViewHolder doInBackground(ViewHolder... params) {
        ViewHolder viewHolder = params[0];

        try {
            URL imageURL = new URL(viewHolder.getImgUrl());
            imageBitmap = BitmapFactory.decodeStream(imageURL.openStream());
        } catch (IOException e) {
            Log.e("error", "Downloading Image Failed "+ viewHolder.getImgUrl());
            imageBitmap = null;
        }

        return viewHolder;
    }

    @Override
    protected void onPostExecute(ViewHolder viewHolder) {
        if (imageBitmap == null) {
            viewHolder.getImageView().setImageResource(R.mipmap.android);
        } else {
            viewHolder.getImageView().setImageBitmap(imageBitmap);
        }
    }
}
