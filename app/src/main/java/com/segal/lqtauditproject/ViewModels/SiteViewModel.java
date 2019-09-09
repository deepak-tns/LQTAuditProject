package com.segal.lqtauditproject.ViewModels;

/**
 * Created by Hossein on 10/27/2017.
 */

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.widget.ImageView;


public class SiteViewModel extends ViewModel {
    private Bitmap buildingHeightBitmap;
    private ImageView imageView;



    public Bitmap getBuildingHeightBitmap() {
        return buildingHeightBitmap;
    }

    public void setBuildingHeightBitmap(Bitmap buildingHeightBitmap) {
        this.buildingHeightBitmap = buildingHeightBitmap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
