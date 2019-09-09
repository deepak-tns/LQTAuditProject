package com.segal.lqtauditproject.Models;

import android.util.Log;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.PanoramaViewModel;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

import static com.segal.lqtauditproject.Helpers.ImageHelpers.storeImageFromFile;

/**
 * Created by Hossein on 10/27/2017.
 */

public class Site extends RealmObject {
    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String userId;

    private String siteId;
    private String siteName;
    private String planLatitude;
    private String planLongitude;
    private String onSiteLatitude;
    private String onSiteLongitude;
    private String address;
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
    private String riggerImagePath;

    private RealmList<Antenna> antennas;

    private String bearing0ImagePath;
    private boolean bearing0Blocking;
    private String bearing0Comments;

    private String bearing30ImagePath;
    private boolean bearing30Blocking;
    private String bearing30Comments;

    private String bearing60ImagePath;
    private boolean bearing60Blocking;
    private String bearing60Comments;

    private String bearing90ImagePath;
    private boolean bearing90Blocking;
    private String bearing90Comments;

    private String bearing120ImagePath;
    private boolean bearing120Blocking;
    private String bearing120Comments;

    private String bearing150ImagePath;
    private boolean bearing150Blocking;
    private String bearing150Comments;

    private String bearing180ImagePath;
    private boolean bearing180Blocking;
    private String bearing180Comments;

    private String bearing210ImagePath;
    private boolean bearing210Blocking;
    private String bearing210Comments;

    private String bearing240ImagePath;
    private boolean bearing240Blocking;
    private String bearing240Comments;

    private String bearing270ImagePath;
    private boolean bearing270Blocking;
    private String bearing270Comments;

    private String bearing300ImagePath;
    private boolean bearing300Blocking;
    private String bearing300Comments;

    private String bearing330ImagePath;
    private boolean bearing330Blocking;
    private String bearing330Comments;


    private Date lastModifiedAt;
    private Date localCreatedAt;
    private Date lastLocalModifiedAt;
    private Date lastUploadedAt;

    private String status;

    private boolean isCreatedLocal;

    @Ignore
    private boolean isTemp;

    public Site() {

    }

    public Site(Site site) {
        setId(site.getId());
        setLocalCreatedAt(site.getLocalCreatedAt());
        setLastModifiedAt(site.getLastModifiedAt());
        setLastLocalModifiedAt(site.getLastLocalModifiedAt());
        setLastUploadedAt(site.getLastUploadedAt());

        SiteInfoViewModel si = new SiteInfoViewModel();
        si.fillFrom(site);
        PanoramaViewModel p = new PanoramaViewModel();
        p.fillFrom(site);

        fillSiteInfo(si);
        fillPanorama(p);

        for (Antenna antenna : site.getAntennas()) {
            AntennaViewModel a = new AntennaViewModel();
            a.fillFrom(antenna);
            addAntenna(a, antenna.getSectorIndex(), antenna.getAntennaIndex());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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


    public String getOnSiteLongitude() {
        return onSiteLongitude;
    }

    public void setOnSiteLongitude(String onSiteLongitude) {
        this.onSiteLongitude = onSiteLongitude;
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

    public String getBearing0ImagePath() {
        return bearing0ImagePath;
    }

    public void setBearing0ImagePath(String bearing0ImagePath) {
        this.bearing0ImagePath = bearing0ImagePath;
    }

    public boolean getBearing0Blocking() {
        return bearing0Blocking;
    }

    public void setBearing0Blocking(boolean bearing0Blocking) {
        this.bearing0Blocking = bearing0Blocking;
    }

    public String getBearing0Comments() {
        return bearing0Comments;
    }

    public void setBearing0Comments(String bearing0Comments) {
        this.bearing0Comments = bearing0Comments;
    }

    public String getBearing30ImagePath() {
        return bearing30ImagePath;
    }

    public void setBearing30ImagePath(String bearing30ImagePath) {
        this.bearing30ImagePath = bearing30ImagePath;
    }

    public boolean getBearing30Blocking() {
        return bearing30Blocking;
    }

    public void setBearing30Blocking(boolean bearing30Blocking) {
        this.bearing30Blocking = bearing30Blocking;
    }

    public String getBearing30Comments() {
        return bearing30Comments;
    }

    public void setBearing30Comments(String bearing30Comments) {
        this.bearing30Comments = bearing30Comments;
    }

    public String getBearing60ImagePath() {
        return bearing60ImagePath;
    }

    public void setBearing60ImagePath(String bearing60ImagePath) {
        this.bearing60ImagePath = bearing60ImagePath;
    }

    public boolean getBearing60Blocking() {
        return bearing60Blocking;
    }

    public void setBearing60Blocking(boolean bearing60Blocking) {
        this.bearing60Blocking = bearing60Blocking;
    }

    public String getBearing60Comments() {
        return bearing60Comments;
    }

    public void setBearing60Comments(String bearing60Comments) {
        this.bearing60Comments = bearing60Comments;
    }

    public String getBearing120ImagePath() {
        return bearing120ImagePath;
    }

    public void setBearing120ImagePath(String bearing120ImagePath) {
        this.bearing120ImagePath = bearing120ImagePath;
    }

    public boolean getBearing120Blocking() {
        return bearing120Blocking;
    }

    public void setBearing120Blocking(boolean bearing120Blocking) {
        this.bearing120Blocking = bearing120Blocking;
    }

    public String getBearing120Comments() {
        return bearing120Comments;
    }

    public void setBearing120Comments(String bearing120Comments) {
        this.bearing120Comments = bearing120Comments;
    }

    public String getBearing150ImagePath() {
        return bearing150ImagePath;
    }

    public void setBearing150ImagePath(String bearing150ImagePath) {
        this.bearing150ImagePath = bearing150ImagePath;
    }

    public boolean getBearing150Blocking() {
        return bearing150Blocking;
    }

    public void setBearing150Blocking(boolean bearing150Blocking) {
        this.bearing150Blocking = bearing150Blocking;
    }

    public String getBearing150Comments() {
        return bearing150Comments;
    }

    public void setBearing150Comments(String bearing150Comments) {
        this.bearing150Comments = bearing150Comments;
    }

    public String getBearing180ImagePath() {
        return bearing180ImagePath;
    }

    public void setBearing180ImagePath(String bearing180ImagePath) {
        this.bearing180ImagePath = bearing180ImagePath;
    }

    public boolean getBearing180Blocking() {
        return bearing180Blocking;
    }

    public void setBearing180Blocking(boolean bearing180Blocking) {
        this.bearing180Blocking = bearing180Blocking;
    }

    public String getBearing180Comments() {
        return bearing180Comments;
    }

    public void setBearing180Comments(String bearing180Comments) {
        this.bearing180Comments = bearing180Comments;
    }

    public String getBearing210ImagePath() {
        return bearing210ImagePath;
    }

    public void setBearing210ImagePath(String bearing210ImagePath) {
        this.bearing210ImagePath = bearing210ImagePath;
    }

    public boolean getBearing210Blocking() {
        return bearing210Blocking;
    }

    public void setBearing210Blocking(boolean bearin270Blocking) {
        this.bearing210Blocking = bearin270Blocking;
    }

    public String getBearing210Comments() {
        return bearing210Comments;
    }

    public void setBearing210Comments(String bearing210Comments) {
        this.bearing210Comments = bearing210Comments;
    }

    public String getBearing240ImagePath() {
        return bearing240ImagePath;
    }

    public void setBearing240ImagePath(String bearing240ImagePath) {
        this.bearing240ImagePath = bearing240ImagePath;
    }

    public boolean getBearing240Blocking() {
        return bearing240Blocking;
    }

    public void setBearing240Blocking(boolean bearing240Blocking) {
        this.bearing240Blocking = bearing240Blocking;
    }

    public String getBearing240Comments() {
        return bearing240Comments;
    }

    public void setBearing240Comments(String bearing240Comments) {
        this.bearing240Comments = bearing240Comments;
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

    public void setHasTechnology4g(Boolean hasTechnology4g) {
        this.hasTechnology4g = hasTechnology4g;
    }


    public boolean fillSiteInfo(SiteInfoViewModel siteInfoViewModel) {

        this.setSiteId(siteInfoViewModel.getSiteId());
        this.setSiteName(siteInfoViewModel.getSiteName());
        this.setPlanLatitude(siteInfoViewModel.getPlanLatitude());
        this.setPlanLongitude(siteInfoViewModel.getPlanLongitude());
        this.setOnSiteLatitude(siteInfoViewModel.getOnSiteLatitude());
        this.setOnSiteLongitude(siteInfoViewModel.getOnSiteLongitude());
        this.setAddress(siteInfoViewModel.getAddress());
        this.setBuildingHeight(siteInfoViewModel.getBuildingHeight());
        this.setTowerHeight(siteInfoViewModel.getTowerHeight());
        this.setCoLocated(siteInfoViewModel.isCoLocated());
        this.setHasTechnology2g(siteInfoViewModel.getHasTechnology2g());
        this.setHasTechnology3g(siteInfoViewModel.getHasTechnology3g());
        this.setHasTechnology4g(siteInfoViewModel.getHasTechnology4g());
        this.setVendor(siteInfoViewModel.getVendor());
        this.setSiteType(siteInfoViewModel.getSiteType());
        this.setTower(siteInfoViewModel.getTower());
        this.setSiteLabel(siteInfoViewModel.getSiteLabel());

        this.setExtra1ImagePath(siteInfoViewModel.getExtra1ImagePath());
        this.setExtra2ImagePath(siteInfoViewModel.getExtra2ImagePath());

        if (getSiteId() == null)
            return true;

//        String createdAtString = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(getLocalCreatedAt());
//        String titlePrefix = getSiteId() + "_SI_";
////        String dirName = createdAtString;
//        String dirName = getSiteId();
//        if (isTemp)
//            dirName = dirName + "_temp";
//        String title = "BuildingHeight";
//        String fileName = getSiteId() + File.separator + titlePrefix + title;
//
//        if (siteInfoViewModel.getRiggerImagePath() != null) {
//            title = "Rigger";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getRiggerImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setRiggerImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getBuildingHeightImagePath() != null) {
//            title = "BuildingHeight";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getBuildingHeightImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBuildingHeightImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getTowerHeightImagePath() != null) {
//            title = "TowerHeight";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getTowerHeightImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setTowerHeightImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getSiteLabelImagePath() != null) {
//            title = "SiteLabel";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getSiteLabelImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setSiteLabelImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getBtsInsideImagePath() != null) {
//            title = "BTSInside";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getBtsInsideImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBtsInsideImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getBtsOutsideImagePath() != null) {
//            title = "BTSOutside";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getBtsOutsideImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBtsOutsideImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getAntBuildingZoomInImagePath() != null) {
//            title = "BuildingZoomIn";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getAntBuildingZoomInImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setAntBuildingZoomInImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getAntBuildingZoomOutImagePath() != null) {
//            title = "BuildingZoomOut";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getAntBuildingZoomOutImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setAntBuildingZoomOutImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getGpsSnapshotImagePath() != null) {
//            title = "GPSSnapshot";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getGpsSnapshotImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setGpsSnapshotImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getExtra1ImagePath() != null) {
//            title = "Extra1";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getExtra1ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setExtra1ImagePath(fileName);
//        }
//
//        if (siteInfoViewModel.getExtra2ImagePath() != null) {
//            title = "Extra2";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(siteInfoViewModel.getExtra2ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setExtra2ImagePath(fileName);
//        }

        return true;
    }

    public boolean addAntenna(AntennaViewModel antennaViewModel, int sectorIndex, int antennaIndex) {
        if (antennas == null)
            antennas = new RealmList<>();

        Antenna a = null;
        boolean exists = false;
        for (Antenna antenna : antennas) {
            if (antenna.getSectorIndex() == sectorIndex && antenna.getAntennaIndex() == antennaIndex) {
                a = antenna;
                exists = true;
            }
        }

        if (a == null) a = new Antenna();


        a.setSectorIndex(sectorIndex);
        a.setAntennaIndex(antennaIndex);

        a.setHasBand900(antennaViewModel.getHasBand900());
        a.setHasBand1800(antennaViewModel.getHasBand1800());
        a.setHasBand2100(antennaViewModel.getHasBand2100());
        a.setHasBand2600(antennaViewModel.getHasBand2600());
        a.setHasBand3500(antennaViewModel.getHasBand3500());

        a.setTechnology900(antennaViewModel.getTechnology900());
        a.setTechnology1800(antennaViewModel.getTechnology1800());
        a.setTechnology2100(antennaViewModel.getTechnology2100());
        a.setTechnology2600(antennaViewModel.getTechnology2600());
        a.setTechnology3500(antennaViewModel.getTechnology3500());

        a.setPlanAntennaName(antennaViewModel.getPlanAntennaName());
        a.setOnSiteAntennaName(antennaViewModel.getOnSiteAntennaName());

        a.setPlanETilt900(antennaViewModel.getPlanETilt900());
        a.setOnSiteETilt900(antennaViewModel.getOnSiteETilt900());

        a.setChangeETilt900(antennaViewModel.getChangeETilt900());

        a.setPlanETilt1800(antennaViewModel.getPlanETilt1800());
        a.setOnSiteETilt1800(antennaViewModel.getOnSiteETilt1800());

        a.setChangeETilt1800(antennaViewModel.getChangeETilt1800());

        a.setPlanETilt2100(antennaViewModel.getPlanETilt2100());
        a.setOnSiteETilt2100(antennaViewModel.getOnSiteETilt2100());

        a.setPlanETilt2600(antennaViewModel.getPlanETilt2600());
        a.setOnSiteETilt2600(antennaViewModel.getOnSiteETilt2600());
        a.setChangeETilt2100(antennaViewModel.getChangeETilt2100());

        a.setChangeETilt2600(antennaViewModel.getChangeETilt2600());

        a.setPlanETilt3500(antennaViewModel.getPlanETilt3500());
        a.setOnSiteETilt3500(antennaViewModel.getOnSiteETilt3500());

        a.setChangeETilt3500(antennaViewModel.getChangeETilt3500());

        a.seteTiltMechanism(antennaViewModel.getETiltMechanism());

        a.setPlanMTilt(antennaViewModel.getPlanMTilt());
        a.setOnSiteMTilt(antennaViewModel.getOnSiteMTilt());

        a.setChangeMTilt(antennaViewModel.getChangeMTilt());

        a.setPlanHeight(antennaViewModel.getPlanHeight());
        a.setOnSiteHeight(antennaViewModel.getOnSiteHeight());
        a.setChangeHeight(antennaViewModel.getChangeHeight());
        a.setPlanAzimuth(antennaViewModel.getPlanAzimuth());
        a.setOnSiteAzimuth(antennaViewModel.getOnSiteAzimuth());
        a.setChangeAzimuth(antennaViewModel.getChangeAzimuth());

        a.setRet(antennaViewModel.getRet());

        a.setSelfAwareAddon(antennaViewModel.getSelfAwareAddon());

        a.setRru1SerialNumber(antennaViewModel.getRru1SerialNumber());


        a.setRru1Band(antennaViewModel.getRru1Band());
        a.setRru1Technology2g(antennaViewModel.getRru1Technology2g());
        a.setRru1Technology3g(antennaViewModel.getRru1Technology3g());
        a.setRru1Technology4g(antennaViewModel.getRru1Technology4g());

        a.setRru2SerialNumber(antennaViewModel.getRru2SerialNumber());

        a.setRru2Band(antennaViewModel.getRru2Band());
        a.setRru2Technology2g(antennaViewModel.getRru2Technology2g());
        a.setRru2Technology3g(antennaViewModel.getRru2Technology3g());
        a.setRru2Technology4g(antennaViewModel.getRru2Technology4g());

        a.setRru3SerialNumber(antennaViewModel.getRru3SerialNumber());

        a.setRru3Band(antennaViewModel.getRru3Band());
        a.setRru3Technology2g(antennaViewModel.getRru3Technology2g());
        a.setRru3Technology3g(antennaViewModel.getRru3Technology3g());
        a.setRru3Technology4g(antennaViewModel.getRru3Technology4g());

        a.setRru4SerialNumber(antennaViewModel.getRru4SerialNumber());

        a.setRru4Band(antennaViewModel.getRru4Band());
        a.setRru4Technology2g(antennaViewModel.getRru4Technology2g());
        a.setRru4Technology3g(antennaViewModel.getRru4Technology3g());
        a.setRru4Technology4g(antennaViewModel.getRru4Technology4g());


        if (getSiteId() != null) {

//            String sectorSymbol = "A";
//            switch (sectorIndex) {
//                case 1:
//                    sectorSymbol = "A";
//                    break;
//                case 2:
//                    sectorSymbol = "B";
//                    break;
//                case 3:
//                    sectorSymbol = "C";
//                    break;
//                case 4:
//                    sectorSymbol = "D";
//                    break;
//                default:
//                    sectorSymbol = "A";
//            }
//
//            String createdAtString = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(getLocalCreatedAt());
////        String dirName = createdAtString;
//            String dirName = getSiteId();
//            if (isTemp)
//                dirName = dirName + "_temp";
////        String titlePrefix = createdAtString + "_" + sectorSymbol + "_" + antennaIndex + "_";
//            String titlePrefix = getSiteId() + "_S" + sectorSymbol + "_" + antennaIndex + "_";
//            String title = "OnSiteAntennaName";
//            String fileName = getSiteId() + File.separator + titlePrefix + title;
//
//
//            if (antennaViewModel.getOnSiteAntennaNameImagePath() != null) {
//                title = "OnSiteAntennaName";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteAntennaNameImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteAntennaNameImagePath(fileName);
//            }
//
//            if (antennaViewModel.getAntennaViewImagePath() != null) {
//                title = "AntennaView";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getAntennaViewImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setAntennaViewImagePath(fileName);
//            }
//
//            if (antennaViewModel.getDeviceOnAntennaImagePath() != null) {
//                title = "DeviceOnAntenna";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getDeviceOnAntennaImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setDeviceOnAntennaImagePath(fileName);
//            }
//
//
//            if (antennaViewModel.getOnSiteETilt900ImagePath() != null) {
//                title = "OnSiteETilt900";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteETilt900ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteETilt900ImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeETilt900ImagePath() != null) {
//                title = "ChangedETilt900";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeETilt900ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeETilt900ImagePath(fileName);
//            }
//
//
//            if (antennaViewModel.getOnSiteETilt1800ImagePath() != null) {
//                title = "OnSiteETilt1800";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteETilt1800ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteETilt1800ImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeETilt1800ImagePath() != null) {
//                title = "ChangedETilt1800";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeETilt1800ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeETilt1800ImagePath(fileName);
//            }
//
//
//            if (antennaViewModel.getOnSiteETilt2100ImagePath() != null) {
//                title = "OnSiteETilt2100";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteETilt2100ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteETilt2100ImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeETilt2100ImagePath() != null) {
//                title = "ChangedETilt2100";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeETilt2100ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeETilt2100ImagePath(fileName);
//            }
//
//
//            if (antennaViewModel.getOnSiteETilt2600ImagePath() != null) {
//                title = "OnSiteETilt2600";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteETilt2600ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteETilt2600ImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeETilt2600ImagePath() != null) {
//                title = "ChangedETilt2600";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeETilt2600ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeETilt2600ImagePath(fileName);
//            }
//            if (antennaViewModel.getOnSiteETilt3500ImagePath() != null) {
//                title = "OnSiteETilt3500";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteETilt3500ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteETilt3500ImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeETilt3500ImagePath() != null) {
//                title = "ChangedETilt3500";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeETilt3500ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeETilt3500ImagePath(fileName);
//            }
//            if (antennaViewModel.getOnSiteMTiltImagePath() != null) {
//                title = "OnSiteMTilt";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteMTiltImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteMTiltImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeMTiltImagePath() != null) {
//                title = "ChangedMTilt";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeMTiltImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeMTiltImagePath(fileName);
//            }
//            if (antennaViewModel.getOnSiteHeightImagePath() != null) {
//                title = "OnSiteHeight";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteHeightImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteHeightImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeHeightImagePath() != null) {
//                title = "ChangedHeight";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeHeightImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeHeightImagePath(fileName);
//            }
//            if (antennaViewModel.getOnSiteAzimuthImagePath() != null) {
//                title = "OnSiteAzimuth";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getOnSiteAzimuthImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setOnSiteAzimuthImagePath(fileName);
//            }
//            if (antennaViewModel.getChangeAzimuthImagePath() != null) {
//                title = "ChangedAzimuth";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getChangeAzimuthImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setChangeAzimuthImagePath(fileName);
//            }
//            if (antennaViewModel.getRetImagePath() != null) {
//                title = "RET";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getRetImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setRetImagePath(fileName);
//            }
//            if (antennaViewModel.getRru1SerialNumberImagePath() != null) {
//                title = "RRU_1_SerialNumber";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getRru1SerialNumberImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setRru1SerialNumberImagePath(fileName);
//            }
//            if (antennaViewModel.getRru2SerialNumberImagePath() != null) {
//                title = "RRU_2_SerialNumber";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getRru2SerialNumberImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setRru2SerialNumberImagePath(fileName);
//            }
//            if (antennaViewModel.getRru3SerialNumberImagePath() != null) {
//                title = "RRU_3_SerialNumber";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getRru3SerialNumberImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setRru3SerialNumberImagePath(fileName);
//            }
//            if (antennaViewModel.getRru4SerialNumberImagePath() != null) {
//                title = "RRU_4_SerialNumber";
//                fileName = dirName + File.separator + titlePrefix + title;
//                storeImageFromFile(antennaViewModel.getRru4SerialNumberImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//                a.setRru4SerialNumberImagePath(fileName);
//            }
        }

        if (!exists)
            antennas.add(a);

        return true;
    }

    public boolean fillPanorama(PanoramaViewModel panoramaViewModel) {

        this.setBearing0Blocking(panoramaViewModel.getBearing0Blocking());
        this.setBearing0Comments(panoramaViewModel.getBearing0Comments());

        this.setBearing30Blocking(panoramaViewModel.getBearing30Blocking());
        this.setBearing30Comments(panoramaViewModel.getBearing30Comments());

        this.setBearing60Blocking(panoramaViewModel.getBearing60Blocking());
        this.setBearing60Comments(panoramaViewModel.getBearing60Comments());

        this.setBearing90Blocking(panoramaViewModel.getBearing90Blocking());
        this.setBearing90Comments(panoramaViewModel.getBearing90Comments());

        this.setBearing120Blocking(panoramaViewModel.getBearing120Blocking());
        this.setBearing120Comments(panoramaViewModel.getBearing120Comments());

        this.setBearing150Blocking(panoramaViewModel.getBearing150Blocking());
        this.setBearing150Comments(panoramaViewModel.getBearing150Comments());

        this.setBearing180Blocking(panoramaViewModel.getBearing180Blocking());
        this.setBearing180Comments(panoramaViewModel.getBearing180Comments());

        this.setBearing210Blocking(panoramaViewModel.getBearing210Blocking());
        this.setBearing210Comments(panoramaViewModel.getBearing210Comments());

        this.setBearing240Blocking(panoramaViewModel.getBearing240Blocking());
        this.setBearing240Comments(panoramaViewModel.getBearing240Comments());

        this.setBearing270Blocking(panoramaViewModel.getBearing270Blocking());
        this.setBearing270Comments(panoramaViewModel.getBearing270Comments());

        this.setBearing300Blocking(panoramaViewModel.getBearing300Blocking());
        this.setBearing300Comments(panoramaViewModel.getBearing300Comments());

        this.setBearing330Blocking(panoramaViewModel.getBearing330Blocking());
        this.setBearing330Comments(panoramaViewModel.getBearing330Comments());


        if(getSiteId() == null)
            return true;
//
//        String createdAtString = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(getLocalCreatedAt());
////        String dirName = createdAtString;
//        String dirName = getSiteId();
//        if (isTemp)
//            dirName = dirName + "_temp";
//        String titlePrefix = getSiteId() + "_P_";
//        String title = "Bearing0";
//        String fileName = getSiteId() + File.separator + titlePrefix + title;
//
//
//        if (panoramaViewModel.getBearing0ImagePath() != null) {
//            title = "Bearing0";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing0ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing0ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing30ImagePath() != null) {
//            title = "Bearing30";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing30ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing30ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing60ImagePath() != null) {
//            title = "Bearing60";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing60ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing60ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing90ImagePath() != null) {
//            title = "Bearing90";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing90ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing90ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing120ImagePath() != null) {
//            title = "Bearing120";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing120ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing120ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing150ImagePath() != null) {
//            title = "Bearing150";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing150ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing150ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing180ImagePath() != null) {
//            title = "Bearing180";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing180ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing180ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing210ImagePath() != null) {
//            title = "Bearing210";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing210ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing210ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing240ImagePath() != null) {
//            title = "Bearing240";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing240ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing240ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing270ImagePath() != null) {
//            title = "Bearing270";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing270ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing270ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing300ImagePath() != null) {
//            title = "Bearing300";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing300ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing300ImagePath(fileName);
//        }
//
//        if (panoramaViewModel.getBearing330ImagePath() != null) {
//            title = "Bearing330";
//            fileName = dirName + File.separator + titlePrefix + title;
//            storeImageFromFile(panoramaViewModel.getBearing330ImagePath(), fileName, title, this.getOnSiteLatitude(), this.getOnSiteLongitude());
//            this.setBearing330ImagePath(fileName);
//        }

        return true;
    }

    public RealmList<Antenna> getAntennas() {
        return antennas;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public void setAntennas(RealmList<Antenna> antennas) {
        this.antennas = antennas;
    }

    public Date getLastUploadedAt() {
        return lastUploadedAt;
    }

    public void setLastUploadedAt(Date lastUploadedAt) {
        this.lastUploadedAt = lastUploadedAt;
    }

    public Date getLastLocalModifiedAt() {
        return lastLocalModifiedAt;
    }

    public void setLastLocalModifiedAt(Date lastLocalModifiedAt) {
        this.lastLocalModifiedAt = lastLocalModifiedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLocalCreatedAt() {
        return localCreatedAt;
    }

    public void setLocalCreatedAt(Date localCreatedAt) {
        this.localCreatedAt = localCreatedAt;
    }

    public static boolean equals(Site site, Site oldEntity) {
        boolean isEqual = true;


        return false;
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setTemp(boolean temp) {
        isTemp = temp;
    }

    public String getBearing90ImagePath() {
        return bearing90ImagePath;
    }

    public void setBearing90ImagePath(String bearing90ImagePath) {
        this.bearing90ImagePath = bearing90ImagePath;
    }

    public boolean getBearing90Blocking() {
        return bearing90Blocking;
    }

    public void setBearing90Blocking(boolean bearing90Blocking) {
        this.bearing90Blocking = bearing90Blocking;
    }

    public String getBearing90Comments() {
        return bearing90Comments;
    }

    public void setBearing90Comments(String bearing90Comments) {
        this.bearing90Comments = bearing90Comments;
    }

    public String getBearing270ImagePath() {
        return bearing270ImagePath;
    }

    public void setBearing270ImagePath(String bearing270ImagePath) {
        this.bearing270ImagePath = bearing270ImagePath;
    }

    public boolean getBearing270Blocking() {
        return bearing270Blocking;
    }

    public void setBearing270Blocking(boolean bearing270Blocking) {
        this.bearing270Blocking = bearing270Blocking;
    }

    public String getBearing270Comments() {
        return bearing270Comments;
    }

    public void setBearing270Comments(String bearing247Comments) {
        this.bearing270Comments = bearing247Comments;
    }

    public String getBearing300ImagePath() {
        return bearing300ImagePath;
    }

    public void setBearing300ImagePath(String bearing300ImagePath) {
        this.bearing300ImagePath = bearing300ImagePath;
    }

    public boolean getBearing300Blocking() {
        return bearing300Blocking;
    }

    public void setBearing300Blocking(boolean bearing300Blocking) {
        this.bearing300Blocking = bearing300Blocking;
    }

    public String getBearing300Comments() {
        return bearing300Comments;
    }

    public void setBearing300Comments(String bearing300Comments) {
        this.bearing300Comments = bearing300Comments;
    }

    public String getBearing330ImagePath() {
        return bearing330ImagePath;
    }

    public void setBearing330ImagePath(String bearing330ImagePath) {
        this.bearing330ImagePath = bearing330ImagePath;
    }

    public boolean getBearing330Blocking() {
        return bearing330Blocking;
    }

    public void setBearing330Blocking(boolean bearing330Blocking) {
        this.bearing330Blocking = bearing330Blocking;
    }

    public String getBearing330Comments() {
        return bearing330Comments;
    }

    public void setBearing330Comments(String bearing330Comments) {
        this.bearing330Comments = bearing330Comments;
    }

    public boolean isCreatedLocal() {
        return isCreatedLocal;
    }

    public void setCreatedLocal(boolean createdLocal) {
        isCreatedLocal = createdLocal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
