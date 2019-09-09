package com.segal.lqtauditproject.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Models.Antenna;
import com.segal.lqtauditproject.Models.Site;

import io.realm.RealmList;

/**
 * Created by Hossein on 10/27/2017.
 */

public class AntennaViewModel extends ViewModel {
    private int id;
    private boolean hasBand900;
    private boolean hasBand1800;
    private boolean hasBand2100;
    private boolean hasBand2600;
    private boolean hasBand3500;
    private String technology900;
    private String technology1800;
    private String technology2100;
    private String technology2600;
    private String technology3500;
    private String planAntennaName;
    private String onSiteAntennaName;
    private String onSiteAntennaNameImagePath;
    private String antennaViewImagePath;
    private String deviceOnAntennaImagePath;
    private String planETilt900;
    private String onSiteETilt900;
    private String onSiteETilt900ImagePath;
    private String changeETilt900;
    private String changeETilt900ImagePath;
    private String planETilt1800;
    private String onSiteETilt1800;
    private String onSiteETilt1800ImagePath;
    private String changeETilt1800;
    private String changeETilt1800ImagePath;
    private String planETilt2100;
    private String onSiteETilt2100;
    private String onSiteETilt2100ImagePath;
    private String changeETilt2100;
    private String changeETilt2100ImagePath;
    private String planETilt2600;
    private String onSiteETilt2600;
    private String onSiteETilt2600ImagePath;
    private String changeETilt2600;
    private String changeETilt2600ImagePath;
    private String planETilt3500;
    private String onSiteETilt3500;
    private String onSiteETilt3500ImagePath;
    private String changeETilt3500;
    private String changeETilt3500ImagePath;
    private String eTiltMechanism;
    private String planMTilt;
    private String onSiteMTilt;
    private String onSiteMTiltImagePath;
    private String changeMTilt;
    private String changeMTiltImagePath;
    private String planHeight;
    private String onSiteHeight;
    private String onSiteHeightImagePath;
    private String changeHeight;
    private String changeHeightImagePath;
    private String planAzimuth;
    private String onSiteAzimuth;
    private String onSiteAzimuthImagePath;
    private String changeAzimuth;
    private String changeAzimuthImagePath;
    private boolean ret;
    private String retImagePath;
    private boolean selfAwareAddon;
    private String rru1SerialNumber;
    private String rru1SerialNumberImagePath;
    private String rru1Band;
    private boolean rru1Technology2g;
    private boolean rru1Technology3g;
    private boolean rru1Technology4g;
    private String rru2SerialNumber;
    private String rru2SerialNumberImagePath;
    private String rru2Band;
    private boolean rru2Technology2g;
    private boolean rru2Technology3g;
    private boolean rru2Technology4g;
    private String rru3SerialNumber;
    private String rru3SerialNumberImagePath;
    private String rru3Band;
    private boolean rru3Technology2g;
    private boolean rru3Technology3g;
    private boolean rru3Technology4g;
    private String rru4SerialNumber;
    private String rru4SerialNumberImagePath;
    private String rru4Band;
    private boolean rru4Technology2g;
    private boolean rru4Technology3g;
    private boolean rru4Technology4g;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getHasBand900() {
        return hasBand900;
    }

    public void setHasBand900(boolean hasBand900) {
        this.hasBand900 = hasBand900;
    }

    public boolean getHasBand1800() {
        return hasBand1800;
    }

    public void setHasBand1800(boolean hasBand1800) {
        this.hasBand1800 = hasBand1800;
    }

    public boolean getHasBand2100() {
        return hasBand2100;
    }

    public void setHasBand2100(boolean hasBand2100) {
        this.hasBand2100 = hasBand2100;
    }

    public boolean getHasBand2600() {
        return hasBand2600;
    }

    public void setHasBand2600(boolean hasBand2600) {
        this.hasBand2600 = hasBand2600;
    }

    public boolean getHasBand3500() {
        return hasBand3500;
    }

    public void setHasBand3500(boolean hasBand3500) {
        this.hasBand3500 = hasBand3500;
    }

    public String getTechnology900() {
        return technology900;
    }

    public void setTechnology900(String technology900) {
        this.technology900 = technology900;
    }

    public String getTechnology1800() {
        return technology1800;
    }

    public void setTechnology1800(String technology1800) {
        this.technology1800 = technology1800;
    }

    public String getTechnology2100() {
        return technology2100;
    }

    public void setTechnology2100(String technology2100) {
        this.technology2100 = technology2100;
    }

    public String getTechnology2600() {
        return technology2600;
    }

    public void setTechnology2600(String technology2600) {
        this.technology2600 = technology2600;
    }

    public String getTechnology3500() {
        return technology3500;
    }

    public void setTechnology3500(String technology3500) {
        this.technology3500 = technology3500;
    }

    public String getPlanAntennaName() {
        return planAntennaName;
    }

    public void setPlanAntennaName(String planAntennaName) {
        this.planAntennaName = planAntennaName;
    }

    public String getOnSiteAntennaName() {
        return onSiteAntennaName;
    }

    public void setOnSiteAntennaName(String onSiteAntennaName) {
        this.onSiteAntennaName = onSiteAntennaName;
    }

    public String getOnSiteAntennaNameImagePath() {
        return onSiteAntennaNameImagePath;
    }

    public void setOnSiteAntennaNameImagePath(String onSiteAntennaNameImagePath) {
        this.onSiteAntennaNameImagePath = onSiteAntennaNameImagePath;
    }

    public String getAntennaViewImagePath() {
        return antennaViewImagePath;
    }

    public void setAntennaViewImagePath(String antennaViewImagePath) {
        this.antennaViewImagePath = antennaViewImagePath;
    }

    public String getPlanETilt900() {
        return planETilt900;
    }

    public void setPlanETilt900(String planETilt900) {
        this.planETilt900 = planETilt900;
    }

    public String getOnSiteETilt900() {
        return onSiteETilt900;
    }

    public void setOnSiteETilt900(String onSiteETilt900) {
        this.onSiteETilt900 = onSiteETilt900;
    }

    public String getOnSiteETilt900ImagePath() {
        return onSiteETilt900ImagePath;
    }

    public void setOnSiteETilt900ImagePath(String onSiteETilt900ImagePath) {
        this.onSiteETilt900ImagePath = onSiteETilt900ImagePath;
    }

    public String getChangeETilt900() {
        return changeETilt900;
    }

    public void setChangeETilt900(String changeETilt900) {
        this.changeETilt900 = changeETilt900;
    }

    public String getChangeETilt900ImagePath() {
        return changeETilt900ImagePath;
    }

    public void setChangeETilt900ImagePath(String changeETilt900ImagePath) {
        this.changeETilt900ImagePath = changeETilt900ImagePath;
    }

    public String getPlanETilt1800() {
        return planETilt1800;
    }

    public void setPlanETilt1800(String planETilt1800) {
        this.planETilt1800 = planETilt1800;
    }

    public String getOnSiteETilt1800() {
        return onSiteETilt1800;
    }

    public void setOnSiteETilt1800(String onSiteETilt1800) {
        this.onSiteETilt1800 = onSiteETilt1800;
    }

    public String getOnSiteETilt1800ImagePath() {
        return onSiteETilt1800ImagePath;
    }

    public void setOnSiteETilt1800ImagePath(String onSiteETilt1800ImagePath) {
        this.onSiteETilt1800ImagePath = onSiteETilt1800ImagePath;
    }

    public String getChangeETilt1800() {
        return changeETilt1800;
    }

    public void setChangeETilt1800(String changeETilt1800) {
        this.changeETilt1800 = changeETilt1800;
    }

    public String getChangeETilt1800ImagePath() {
        return changeETilt1800ImagePath;
    }

    public void setChangeETilt1800ImagePath(String changeETilt1800ImagePath) {
        this.changeETilt1800ImagePath = changeETilt1800ImagePath;
    }

    public String getPlanETilt2100() {
        return planETilt2100;
    }

    public void setPlanETilt2100(String planETilt2100) {
        this.planETilt2100 = planETilt2100;
    }

    public String getOnSiteETilt2100() {
        return onSiteETilt2100;
    }

    public void setOnSiteETilt2100(String onSiteETilt2100) {
        this.onSiteETilt2100 = onSiteETilt2100;
    }

    public String getOnSiteETilt2100ImagePath() {
        return onSiteETilt2100ImagePath;
    }

    public void setOnSiteETilt2100ImagePath(String onSiteETilt2100ImagePath) {
        this.onSiteETilt2100ImagePath = onSiteETilt2100ImagePath;
    }

    public String getChangeETilt2100() {
        return changeETilt2100;
    }

    public void setChangeETilt2100(String changeETilt2100) {
        this.changeETilt2100 = changeETilt2100;
    }

    public String getChangeETilt2100ImagePath() {
        return changeETilt2100ImagePath;
    }

    public void setChangeETilt2100ImagePath(String changeETilt2100ImagePath) {
        this.changeETilt2100ImagePath = changeETilt2100ImagePath;
    }

    public String getPlanETilt2600() {
        return planETilt2600;
    }

    public void setPlanETilt2600(String planETilt2600) {
        this.planETilt2600 = planETilt2600;
    }

    public String getOnSiteETilt2600() {
        return onSiteETilt2600;
    }

    public void setOnSiteETilt2600(String onSiteETilt2600) {
        this.onSiteETilt2600 = onSiteETilt2600;
    }

    public String getOnSiteETilt2600ImagePath() {
        return onSiteETilt2600ImagePath;
    }

    public void setOnSiteETilt2600ImagePath(String onSiteETilt2600ImagePath) {
        this.onSiteETilt2600ImagePath = onSiteETilt2600ImagePath;
    }

    public String getChangeETilt2600() {
        return changeETilt2600;
    }

    public void setChangeETilt2600(String changeETilt2600) {
        this.changeETilt2600 = changeETilt2600;
    }

    public String getChangeETilt2600ImagePath() {
        return changeETilt2600ImagePath;
    }

    public void setChangeETilt2600ImagePath(String changeETilt2600ImagePath) {
        this.changeETilt2600ImagePath = changeETilt2600ImagePath;
    }

    public String getPlanETilt3500() {
        return planETilt3500;
    }

    public void setPlanETilt3500(String planETilt3500) {
        this.planETilt3500 = planETilt3500;
    }

    public String getOnSiteETilt3500() {
        return onSiteETilt3500;
    }

    public void setOnSiteETilt3500(String onSiteETilt3500) {
        this.onSiteETilt3500 = onSiteETilt3500;
    }

    public String getOnSiteETilt3500ImagePath() {
        return onSiteETilt3500ImagePath;
    }

    public void setOnSiteETilt3500ImagePath(String onSiteETilt3500ImagePath) {
        this.onSiteETilt3500ImagePath = onSiteETilt3500ImagePath;
    }

    public String getChangeETilt3500() {
        return changeETilt3500;
    }

    public void setChangeETilt3500(String changeETilt3500) {
        this.changeETilt3500 = changeETilt3500;
    }

    public String getChangeETilt3500ImagePath() {
        return changeETilt3500ImagePath;
    }

    public void setChangeETilt3500ImagePath(String changeETilt3500ImagePath) {
        this.changeETilt3500ImagePath = changeETilt3500ImagePath;
    }

    public String getETiltMechanism() {
        return eTiltMechanism;
    }

    public void setETiltMechanism(String eTiltMechanism) {
        this.eTiltMechanism = eTiltMechanism;
    }

    public String getPlanMTilt() {
        return planMTilt;
    }

    public void setPlanMTilt(String planMTilt) {
        this.planMTilt = planMTilt;
    }

    public String getOnSiteMTilt() {
        return onSiteMTilt;
    }

    public void setOnSiteMTilt(String onSiteMTilt) {
        this.onSiteMTilt = onSiteMTilt;
    }

    public String getOnSiteMTiltImagePath() {
        return onSiteMTiltImagePath;
    }

    public void setOnSiteMTiltImagePath(String onSiteMTiltImagePath) {
        this.onSiteMTiltImagePath = onSiteMTiltImagePath;
    }

    public String getChangeMTilt() {
        return changeMTilt;
    }

    public void setChangeMTilt(String changeMTilt) {
        this.changeMTilt = changeMTilt;
    }

    public String getChangeMTiltImagePath() {
        return changeMTiltImagePath;
    }

    public void setChangeMTiltImagePath(String changeMTiltImagePath) {
        this.changeMTiltImagePath = changeMTiltImagePath;
    }

    public String getPlanAzimuth() {
        return planAzimuth;
    }

    public void setPlanAzimuth(String planAzimuth) {
        this.planAzimuth = planAzimuth;
    }

    public String getOnSiteAzimuth() {
        return onSiteAzimuth;
    }

    public void setOnSiteAzimuth(String onSiteAzimuth) {
        this.onSiteAzimuth = onSiteAzimuth;
    }

    public String getOnSiteAzimuthImagePath() {
        return onSiteAzimuthImagePath;
    }

    public void setOnSiteAzimuthImagePath(String onSiteAzimuthImagePath) {
        this.onSiteAzimuthImagePath = onSiteAzimuthImagePath;
    }

    public String getChangeAzimuth() {
        return changeAzimuth;
    }

    public void setChangeAzimuth(String changeAzimuth) {
        this.changeAzimuth = changeAzimuth;
    }

    public String getChangeAzimuthImagePath() {
        return changeAzimuthImagePath;
    }

    public void setChangeAzimuthImagePath(String changeAzimuthImagePath) {
        this.changeAzimuthImagePath = changeAzimuthImagePath;
    }

    public String getRetImagePath() {
        return retImagePath;
    }

    public void setRetImagePath(String retImagePath) {
        this.retImagePath = retImagePath;
    }

    public boolean getSelfAwareAddon() {
        return selfAwareAddon;
    }

    public void setSelfAwareAddon(boolean selfAwareAddon) {
        this.selfAwareAddon = selfAwareAddon;
    }

    public String getRru1SerialNumber() {
        return rru1SerialNumber;
    }

    public void setRru1SerialNumber(String rru1SerialNumber) {
        this.rru1SerialNumber = rru1SerialNumber;
    }

    public String getRru1SerialNumberImagePath() {
        return rru1SerialNumberImagePath;
    }

    public void setRru1SerialNumberImagePath(String rru1SerialNumberImagePath) {
        this.rru1SerialNumberImagePath = rru1SerialNumberImagePath;
    }

    public String getRru1Band() {
        return rru1Band;
    }

    public void setRru1Band(String rru1Band) {
        this.rru1Band = rru1Band;
    }

    public boolean getRru1Technology2g() {
        return rru1Technology2g;
    }

    public void setRru1Technology2g(boolean rru1Technology2g) {
        this.rru1Technology2g = rru1Technology2g;
    }

    public boolean getRru1Technology3g() {
        return rru1Technology3g;
    }

    public void setRru1Technology3g(boolean rru1Technology3g) {
        this.rru1Technology3g = rru1Technology3g;
    }

    public boolean getRru1Technology4g() {
        return rru1Technology4g;
    }

    public void setRru1Technology4g(boolean rru1Technology4g) {
        this.rru1Technology4g = rru1Technology4g;
    }

    public String getRru2SerialNumber() {
        return rru2SerialNumber;
    }

    public void setRru2SerialNumber(String rru2SerialNumber) {
        this.rru2SerialNumber = rru2SerialNumber;
    }

    public String getRru2SerialNumberImagePath() {
        return rru2SerialNumberImagePath;
    }

    public void setRru2SerialNumberImagePath(String rru2SerialNumberImagePath) {
        this.rru2SerialNumberImagePath = rru2SerialNumberImagePath;
    }

    public String getRru2Band() {
        return rru2Band;
    }

    public void setRru2Band(String rru2Band) {
        this.rru2Band = rru2Band;
    }

    public boolean getRru2Technology2g() {
        return rru2Technology2g;
    }

    public void setRru2Technology2g(boolean rru2Technology2g) {
        this.rru2Technology2g = rru2Technology2g;
    }

    public boolean getRru2Technology3g() {
        return rru2Technology3g;
    }

    public void setRru2Technology3g(boolean rru2Technology3g) {
        this.rru2Technology3g = rru2Technology3g;
    }

    public boolean getRru2Technology4g() {
        return rru2Technology4g;
    }

    public void setRru2Technology4g(boolean rru2Technology4g) {
        this.rru2Technology4g = rru2Technology4g;
    }

    public String getRru3SerialNumber() {
        return rru3SerialNumber;
    }

    public void setRru3SerialNumber(String rru3SerialNumber) {
        this.rru3SerialNumber = rru3SerialNumber;
    }

    public String getRru3SerialNumberImagePath() {
        return rru3SerialNumberImagePath;
    }

    public void setRru3SerialNumberImagePath(String rru3SerialNumberImagePath) {
        this.rru3SerialNumberImagePath = rru3SerialNumberImagePath;
    }

    public String getRru3Band() {
        return rru3Band;
    }

    public void setRru3Band(String rru3Band) {
        this.rru3Band = rru3Band;
    }

    public boolean getRru3Technology2g() {
        return rru3Technology2g;
    }

    public void setRru3Technology2g(boolean rru3Technology2g) {
        this.rru3Technology2g = rru3Technology2g;
    }

    public boolean getRru3Technology3g() {
        return rru3Technology3g;
    }

    public void setRru3Technology3g(boolean rru3Technology3g) {
        this.rru3Technology3g = rru3Technology3g;
    }

    public boolean getRru3Technology4g() {
        return rru3Technology4g;
    }

    public void setRru3Technology4g(boolean rru3Technology4g) {
        this.rru3Technology4g = rru3Technology4g;
    }

    public String getRru4SerialNumber() {
        return rru4SerialNumber;
    }

    public void setRru4SerialNumber(String rru4SerialNumber) {
        this.rru4SerialNumber = rru4SerialNumber;
    }

    public String getRru4SerialNumberImagePath() {
        return rru4SerialNumberImagePath;
    }

    public void setRru4SerialNumberImagePath(String rru4SerialNumberImagePath) {
        this.rru4SerialNumberImagePath = rru4SerialNumberImagePath;
    }

    public String getRru4Band() {
        return rru4Band;
    }

    public void setRru4Band(String rru4Band) {
        this.rru4Band = rru4Band;
    }

    public boolean getRru4Technology2g() {
        return rru4Technology2g;
    }

    public void setRru4Technology2g(boolean rru4Technology2g) {
        this.rru4Technology2g = rru4Technology2g;
    }

    public boolean getRru4Technology3g() {
        return rru4Technology3g;
    }

    public void setRru4Technology3g(boolean rru4Technology3g) {
        this.rru4Technology3g = rru4Technology3g;
    }

    public boolean getRru4Technology4g() {
        return rru4Technology4g;
    }

    public void setRru4Technology4g(boolean rru4Technology4g) {
        this.rru4Technology4g = rru4Technology4g;
    }

    public boolean getRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getPlanHeight() {
        return planHeight;
    }

    public void setPlanHeight(String planHeight) {
        this.planHeight = planHeight;
    }

    public String getOnSiteHeight() {
        return onSiteHeight;
    }

    public void setOnSiteHeight(String onSiteHeight) {
        this.onSiteHeight = onSiteHeight;
    }

    public String getChangeHeight() {
        return changeHeight;
    }

    public void setChangeHeight(String changeHeight) {
        this.changeHeight = changeHeight;
    }

    public String getOnSiteHeightImagePath() {
        return onSiteHeightImagePath;
    }

    public void setOnSiteHeightImagePath(String onSiteHeightImagePath) {
        this.onSiteHeightImagePath = onSiteHeightImagePath;
    }

    public String getChangeHeightImagePath() {
        return changeHeightImagePath;
    }

    public void setChangeHeightImagePath(String changeHeightImagePath) {
        this.changeHeightImagePath = changeHeightImagePath;
    }

    public void fillFrom(Site site, int sectorIndex, int antennaIndex) {
        if(site.getAntennas() == null)
            return;

        for (Antenna antenna : site.getAntennas()) {
            if (antenna.getSectorIndex() == sectorIndex && antenna.getAntennaIndex() == antennaIndex) {
                this.fillFrom(antenna);
                return;
            }
        }
    }

    public void fillFrom(Antenna a) {
//        Log.d("AntennaViewModel", "requestData: site: " + new Gson().toJson(a));

        this.setHasBand900(a.getHasBand900());
        this.setHasBand1800(a.getHasBand1800());
        this.setHasBand2100(a.getHasBand2100());
        this.setHasBand2600(a.getHasBand2600());
        this.setHasBand3500(a.getHasBand3500());

        this.setTechnology900(a.getTechnology900());
        this.setTechnology1800(a.getTechnology1800());
        this.setTechnology2100(a.getTechnology2100());
        this.setTechnology2600(a.getTechnology2600());
        this.setTechnology3500(a.getTechnology3500());

        this.setPlanAntennaName(a.getPlanAntennaName());
        this.setOnSiteAntennaName(a.getOnSiteAntennaName());
        this.setOnSiteAntennaNameImagePath(a.getOnSiteAntennaNameImagePath());

        this.setAntennaViewImagePath(a.getAntennaViewImagePath());
        this.setDeviceOnAntennaImagePath(a.getDeviceOnAntennaImagePath());

        this.setPlanETilt900(a.getPlanETilt900());
        this.setOnSiteETilt900(a.getOnSiteETilt900());
        this.setOnSiteETilt900ImagePath(a.getOnSiteETilt900ImagePath());
        this.setChangeETilt900(a.getChangeETilt900());
        this.setChangeETilt900ImagePath(a.getChangeETilt900ImagePath());

        this.setPlanETilt1800(a.getPlanETilt1800());
        this.setOnSiteETilt1800(a.getOnSiteETilt1800());
        this.setOnSiteETilt1800ImagePath(a.getOnSiteETilt1800ImagePath());
        this.setChangeETilt1800(a.getChangeETilt1800());
        this.setChangeETilt1800ImagePath(a.getChangeETilt1800ImagePath());

        this.setPlanETilt2100(a.getPlanETilt2100());
        this.setOnSiteETilt2100(a.getOnSiteETilt2100());
        this.setOnSiteETilt2100ImagePath(a.getOnSiteETilt2100ImagePath());
        this.setChangeETilt2100(a.getChangeETilt2100());
        this.setChangeETilt2100ImagePath(a.getChangeETilt2100ImagePath());

        this.setPlanETilt2600(a.getPlanETilt2600());
        this.setOnSiteETilt2600(a.getOnSiteETilt2600());
        this.setOnSiteETilt2600ImagePath(a.getOnSiteETilt2600ImagePath());
        this.setChangeETilt2600(a.getChangeETilt2600());
        this.setChangeETilt2600ImagePath(a.getChangeETilt2600ImagePath());

        this.setPlanETilt3500(a.getPlanETilt3500());
        this.setOnSiteETilt3500(a.getOnSiteETilt3500());
        this.setOnSiteETilt3500ImagePath(a.getOnSiteETilt3500ImagePath());
        this.setChangeETilt3500(a.getChangeETilt3500());
        this.setChangeETilt3500ImagePath(a.getChangeETilt3500ImagePath());

        this.setETiltMechanism(a.geteTiltMechanism());

        this.setPlanMTilt(a.getPlanMTilt());
        this.setOnSiteMTilt(a.getOnSiteMTilt());
        this.setOnSiteMTiltImagePath(a.getOnSiteMTiltImagePath());
        this.setChangeMTilt(a.getChangeMTilt());
        this.setChangeMTiltImagePath(a.getChangeMTiltImagePath());

        this.setPlanHeight(a.getPlanHeight());
        this.setOnSiteHeight(a.getOnSiteHeight());
        this.setOnSiteHeightImagePath(a.getOnSiteHeightImagePath());
        this.setChangeHeight(a.getChangeHeight());
        this.setChangeHeightImagePath(a.getChangeHeightImagePath());

        this.setPlanAzimuth(a.getPlanAzimuth());
        this.setOnSiteAzimuth(a.getOnSiteAzimuth());
        this.setOnSiteAzimuthImagePath(a.getOnSiteAzimuthImagePath());
        this.setChangeAzimuth(a.getChangeAzimuth());
        this.setChangeAzimuthImagePath(a.getChangeAzimuthImagePath());

        this.setRet(a.getRet());
        this.setRetImagePath(a.getRetImagePath());

        this.setSelfAwareAddon(a.getSelfAwareAddon());

        this.setRru1SerialNumber(a.getRru1SerialNumber());
        this.setRru1SerialNumberImagePath(a.getRru1SerialNumberImagePath());
        this.setRru1Band(a.getRru1Band());
        this.setRru1Technology2g(a.getRru1Technology2g());
        this.setRru1Technology3g(a.getRru1Technology3g());
        this.setRru1Technology4g(a.getRru1Technology4g());

        this.setRru2SerialNumber(a.getRru2SerialNumber());
        this.setRru2SerialNumberImagePath(a.getRru2SerialNumberImagePath());
        this.setRru2Band(a.getRru2Band());
        this.setRru2Technology2g(a.getRru2Technology2g());
        this.setRru2Technology3g(a.getRru2Technology3g());
        this.setRru2Technology4g(a.getRru2Technology4g());

        this.setRru3SerialNumber(a.getRru3SerialNumber());
        this.setRru3SerialNumberImagePath(a.getRru3SerialNumberImagePath());
        this.setRru3Band(a.getRru3Band());
        this.setRru3Technology2g(a.getRru3Technology2g());
        this.setRru3Technology3g(a.getRru3Technology3g());
        this.setRru3Technology4g(a.getRru3Technology4g());

        this.setRru4SerialNumber(a.getRru4SerialNumber());
        this.setRru4SerialNumberImagePath(a.getRru4SerialNumberImagePath());
        this.setRru4Band(a.getRru4Band());
        this.setRru4Technology2g(a.getRru4Technology2g());
        this.setRru4Technology3g(a.getRru4Technology3g());
        this.setRru4Technology4g(a.getRru4Technology4g());
    }

    public String getDeviceOnAntennaImagePath() {
        return deviceOnAntennaImagePath;
    }

    public void setDeviceOnAntennaImagePath(String deviceOnAntennaImagePath) {
        this.deviceOnAntennaImagePath = deviceOnAntennaImagePath;
    }
}
