package com.segal.lqtauditproject.ViewEvents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.ImageViewerActivity;

/**
 * Created by Hossein on 11/1/2017.
 */

public class SiteDetailsEvents {

    public static final String TAG = "SiteDetailsEvents";
    private final Context mContext;

    public SiteDetailsEvents(Context context)
    {
        mContext = context;
    }

    public void reviewImage(View view, String bitmapUrl){
        if(bitmapUrl == null || bitmapUrl.length() < 1)
            return;

        Intent intent = new Intent(mContext, ImageViewerActivity.class);
        intent.putExtra("imageUrl", ImageHelpers.getImageFullPath(bitmapUrl));

        mContext.startActivity(intent);
    }
}
