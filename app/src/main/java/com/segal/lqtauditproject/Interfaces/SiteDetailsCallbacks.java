package com.segal.lqtauditproject.Interfaces;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.PanoramaViewModel;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;

/**
 * Created by Hossein on 10/26/2017.
 */

public interface SiteDetailsCallbacks {
    void addSiteSection(SiteSection siteSection);
    void onCameraBtnClicked(ImageView targetImageView, String namePrefix, String tag, String imgNameAddon);
    void setImageViewBitmap(ImageView imageView, String bitmapPath);

    void passSiteInfoViewModel(SiteInfoViewModel viewModel);
    void passAntennaViewModel(AntennaViewModel viewModel, int sectorIndex, int antennaIndex);
    void passPanoramaViewModel(PanoramaViewModel viewModel);

    Site requestSite();

    void saveSite();
    void uploadSite();
    void cancel();
}
