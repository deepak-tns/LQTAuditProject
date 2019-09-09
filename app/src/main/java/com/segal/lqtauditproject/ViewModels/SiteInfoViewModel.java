package com.segal.lqtauditproject.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.android.databinding.library.baseAdapters.BR;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Models.Site;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hossein on 10/28/2017.
 */

public class SiteInfoViewModel extends ViewModel {
//    private int sectorIndex;
//    private int antennaIndex;

    private int id;
    private String siteId;
    private String siteName;
    private String planLatitude;
    private String planLongitude;
    private String onSiteLatitude;
    private String onSiteLongitude;
    private String address;
    private String riggerImagePath;
    private String buildingHeight;
    private String buildingHeightImagePath;
    private String towerHeight;
    private String towerHeightImagePath;
    private boolean coLocated;
    private boolean hasTechnology2g;
    private boolean hasTechnology3g;
    private boolean hasTechnology4g;
    private String vendor;
    private String siteType;
    private String tower;
    private String siteLabel;
    private String siteLabelImagePath;
    private String btsInsideImagePath;
    private String btsOutsideImagePath;
    private String antBuildingZoomInImagePath;
    private String antBuildingZoomOutImagePath;
    private String gpsSnapshotImagePath;
    private String extra1ImagePath;
    private String extra2ImagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPlanLatitude() {
        return planLatitude;
    }

    public void setPlanLatitude(String planLatitude) {
        this.planLatitude = planLatitude;
    }

    public String getPlanLongitude() {
        return planLongitude;
    }

    public void setPlanLongitude(String planLongitude) {
        this.planLongitude = planLongitude;
    }

    public String getOnSiteLatitude() {
        return onSiteLatitude;
    }

    public void setOnSiteLatitude(String onSiteLatitude) {
        this.onSiteLatitude = onSiteLatitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(String buildingHeight) {

        this.buildingHeight = buildingHeight;
    }

    public String getBuildingHeightImagePath() {

        return buildingHeightImagePath;
    }

    public void setBuildingHeightImagePath(String buildingHeightImagePath) {
        this.buildingHeightImagePath = buildingHeightImagePath;
    }

    public String getTowerHeight() {
        return towerHeight;
    }

    public void setTowerHeight(String towerHeight) {
        this.towerHeight = towerHeight;
    }

    public boolean isCoLocated() {
        return coLocated;
    }

    public void setCoLocated(boolean coLocated) {
        this.coLocated = coLocated;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }

    public String getSiteLabel() {
        return siteLabel;
    }

    public void setSiteLabel(String siteLabel) {
        this.siteLabel = siteLabel;
    }

    public String getSiteLabelImagePath() {
        return siteLabelImagePath;
    }

    public void setSiteLabelImagePath(String siteLabelImagePath) {
        this.siteLabelImagePath = siteLabelImagePath;
    }

    public String getBtsInsideImagePath() {
        return btsInsideImagePath;
    }

    public void setBtsInsideImagePath(String btsInsideImagePath) {
        this.btsInsideImagePath = btsInsideImagePath;
    }

    public String getBtsOutsideImagePath() {
        return btsOutsideImagePath;
    }

    public void setBtsOutsideImagePath(String btsOutsideImagePath) {
        this.btsOutsideImagePath = btsOutsideImagePath;
    }

    public String getAntBuildingZoomInImagePath() {
        return antBuildingZoomInImagePath;
    }

    public void setAntBuildingZoomInImagePath(String antBuildingZoomInImagePath) {
        this.antBuildingZoomInImagePath = antBuildingZoomInImagePath;
    }

    public String getAntBuildingZoomOutImagePath() {
        return antBuildingZoomOutImagePath;
    }

    public void setAntBuildingZoomOutImagePath(String antBuildingZoomOutImagePath) {
        this.antBuildingZoomOutImagePath = antBuildingZoomOutImagePath;
    }

    public String getGpsSnapshotImagePath() {
        return gpsSnapshotImagePath;
    }

    public void setGpsSnapshotImagePath(String gpsSnapshotImagePath) {
        this.gpsSnapshotImagePath = gpsSnapshotImagePath;
    }

    public String getExtra1ImagePath() {
        return extra1ImagePath;
    }

    public void setExtra1ImagePath(String extra1ImagePath) {
        this.extra1ImagePath = extra1ImagePath;
    }

    public String getExtra2ImagePath() {
        return extra2ImagePath;
    }

    public void setExtra2ImagePath(String extra2ImagePath) {
        this.extra2ImagePath = extra2ImagePath;
    }

    public String getOnSiteLongitude() {
        return onSiteLongitude;
    }

    public void setOnSiteLongitude(String onSiteLongitude) {
        this.onSiteLongitude = onSiteLongitude;
    }


    public boolean getHasTechnology2g() {
        return hasTechnology2g;
    }

    public void setHasTechnology2g(boolean hasTechnology2g) {
        this.hasTechnology2g = hasTechnology2g;
    }

    public boolean getHasTechnology3g() {
        return hasTechnology3g;
    }

    public void setHasTechnology3g(boolean hasTechnology3g) {
        this.hasTechnology3g = hasTechnology3g;
    }

    public boolean getHasTechnology4g() {
        return hasTechnology4g;
    }

    public void setHasTechnology4g(boolean hasTechnology4g) {
        this.hasTechnology4g = hasTechnology4g;
    }

    public void fillFrom(Site site) {
        this.setSiteId(site.getSiteId());
        this.setSiteName(site.getSiteName());
        this.setPlanLatitude(site.getPlanLatitude());
        this.setPlanLongitude(site.getPlanLongitude());
        this.setOnSiteLatitude(site.getOnSiteLatitude());
        this.setOnSiteLongitude(site.getOnSiteLongitude());
        this.setAddress(site.getAddress());
        this.setRiggerImagePath(site.getRiggerImagePath());
        this.setBuildingHeight(site.getBuildingHeight());
        this.setBuildingHeightImagePath(site.getBuildingHeightImagePath());
        this.setTowerHeight(site.getTowerHeight());
        this.setTowerHeightImagePath(site.getTowerHeightImagePath());
        this.setCoLocated(site.isCoLocated());
        this.setHasTechnology2g(site.getHasTechnology2g());
        this.setHasTechnology3g(site.getHasTechnology3g());
        this.setHasTechnology4g(site.getHasTechnology4g());
        this.setVendor(site.getVendor());
        this.setSiteType(site.getSiteType());
        this.setTower(site.getTower());
        this.setSiteLabel(site.getSiteLabel());
        this.setSiteLabelImagePath(site.getSiteLabelImagePath());
        this.setBtsInsideImagePath(site.getBtsInsideImagePath());
        this.setBtsOutsideImagePath(site.getBtsOutsideImagePath());
        this.setAntBuildingZoomInImagePath(site.getAntBuildingZoomInImagePath());
        this.setAntBuildingZoomOutImagePath(site.getAntBuildingZoomOutImagePath());
        this.setGpsSnapshotImagePath(site.getGpsSnapshotImagePath());
        this.setExtra1ImagePath(site.getExtra1ImagePath());
        this.setExtra2ImagePath(site.getExtra2ImagePath());
    }

    public String getTowerHeightImagePath() {
        return towerHeightImagePath;
    }

    public void setTowerHeightImagePath(String towerHeightImagePath) {
        this.towerHeightImagePath = towerHeightImagePath;
    }

    public String getRiggerImagePath() {
        return riggerImagePath;
    }

    public void setRiggerImagePath(String riggerImagePath) {
        this.riggerImagePath = riggerImagePath;
    }

//    public int getSectorIndex() {
//        return sectorIndex;
//    }
//
//    public void setSectorIndex(int sectorIndex) {
//        this.sectorIndex = sectorIndex;
//    }
//
//    public int getAntennaIndex() {
//        return antennaIndex;
//    }
//
//    public void setAntennaIndex(int antennaIndex) {
//        this.antennaIndex = antennaIndex;
//    }
}
