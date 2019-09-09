package com.segal.lqtauditproject.Data;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Models.Antenna;
import com.segal.lqtauditproject.Models.LoginInfo;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.Models.SiteFields;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.ApiAntennaResult;
import com.segal.lqtauditproject.ViewModels.ApiSiteResult;
import com.segal.lqtauditproject.ViewModels.PanoramaViewModel;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Hossein on 10/30/2017.
 */

public class LQTAuditDataSource {

    private Realm realm;

    public void open() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }


    public boolean createSiteAsync(final Site site) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final Realm realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {

                                                  @Override
                                                  public void execute(Realm realm) {

                                                      realm.insertOrUpdate(site);
                                                  }
                                              },
                        new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                            }
                        },
                        new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable e) {
                                realm.close();
                            }
                        });
            }
        });
        return true;
    }

    public boolean createSite(final Site site) {
        Date now = Calendar.getInstance().getTime();
        Site oldEntity = getSite(site.getId());
        if (getSite(site.getId()) != null)
            site.setLocalCreatedAt(oldEntity.getLocalCreatedAt());
        else
            site.setLocalCreatedAt(now);
        site.setLastLocalModifiedAt(now);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(site);
            }
        });

        return true;
    }

    public boolean updateSiteWithApiSiteResult(final String siteId, final ApiSiteResult site) {
        final Date now = Calendar.getInstance().getTime();
        final Site oldEntity = getSite(siteId);


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (oldEntity.getPlanLatitude() != site.PlanLatitude
                        && oldEntity.getPlanLongitude() != site.PlanLongitude) {
                    oldEntity.setPlanLatitude(site.PlanLatitude);
                    oldEntity.setPlanLongitude(site.PlanLongitude);
                    oldEntity.setStatus(site.Status);
                    oldEntity.setAddress(site.Address);
                    for (ApiAntennaResult antenna : site.Antennas) {
                        Antenna a = getSiteAntennaByIndex(oldEntity.getId(), antenna.SectorIndex + 1, antenna.AntennaIndex + 1);
                        boolean exists = a != null;
                        if (!exists) {
                            a = new Antenna();
                            a.setAntennaIndex(antenna.AntennaIndex + 1);
                            a.setSectorIndex(antenna.SectorIndex + 1);
                        }

                        a.setPlanAzimuth(antenna.PlannedAzimuth);
                        a.setPlanHeight(antenna.PlannedHeight);
                        a.setPlanMTilt(antenna.PlannedMTilt);
                        a.setPlanAntennaName(antenna.PlannedAntenna);
                        a.setPlanETilt900(antenna.PlannedETilt900);
                        a.setPlanETilt1800(antenna.PlannedETilt1800);
                        a.setPlanETilt2100(antenna.PlannedETilt2100);
                        a.setPlanETilt2600(antenna.PlannedETilt2600);
                        a.setPlanETilt3500(antenna.PlannedETilt3500);
                    }
                    oldEntity.setLastLocalModifiedAt(now);
                }
                realm.insertOrUpdate(oldEntity);
            }
        });

        return true;
    }

    public List<Site> getAllSites() {
        return realm.where(Site.class).findAll();
    }

    public List<Site> getAllSites(String userId) {
        return realm.where(Site.class).equalTo(SiteFields.USER_ID, userId).findAll();
    }

    public List<Site> getCurrentUserSites() {
        LoginInfo user = getLastLoginInfo();
        if (user == null)
            return null;
        return getAllSites(user.getUserId());
    }

    public Site getSite(String id) {
        return realm.where(Site.class).equalTo(SiteFields.ID, id).findFirst();
    }

    public Antenna getSiteAntennaByIndex(String siteId, int sectorIndex, int antennaIndex) {
        Site site = realm.where(Site.class).equalTo(SiteFields.ID, siteId).findFirst();
        for (Antenna antenna : site.getAntennas()) {
            if (antenna.getSectorIndex() == sectorIndex && antenna.getAntennaIndex() == antennaIndex)
                return antenna;
        }

        return null;
    }

    public LoginInfo getLastLoginInfo() {
        return realm.where(LoginInfo.class).findFirst();
    }

    public Site getSiteBySiteId(String siteId) {
        return realm.where(Site.class).equalTo(SiteFields.SITE_ID, siteId).findFirst();
    }

    public void addSiteAntennaFrom(final Site site, final AntennaViewModel antennaViewModel, final int sectorIndex, final int antennaIndex) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                site.addAntenna(antennaViewModel, sectorIndex, antennaIndex);
            }
        });
    }

    public void fillSiteInfoFrom(final Site site, final SiteInfoViewModel siteInfoViewModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                site.fillSiteInfo(siteInfoViewModel);
            }
        });
    }

    public void fillSitePanoramaFrom(final Site site, final PanoramaViewModel panoramaViewModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                site.fillPanorama(panoramaViewModel);
            }
        });
    }

    public void createLoginInfo(String mEmail, String token, String userId) {
        final LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(mEmail);
        loginInfo.setToken(token);
        loginInfo.setLoggedInAt(Calendar.getInstance().getTime());
        loginInfo.setUserId(userId);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(loginInfo);
            }
        });

    }

    public void removeLoginInfo() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LoginInfo> result = realm.where(LoginInfo.class).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
