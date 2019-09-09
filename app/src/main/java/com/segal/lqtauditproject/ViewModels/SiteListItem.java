package com.segal.lqtauditproject.ViewModels;

import com.segal.lqtauditproject.Models.Site;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hossein on 10/30/2017.
 */

public class SiteListItem {
    private String siteId;
    private String siteName;
    private String createdAt;
    private String userId;
    private String status;

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


    public static List<SiteListItem> getSiteListItemsFrom(List<Site> sites) {
        List<SiteListItem> siteItems = new ArrayList<>();
        for (Site site : sites) {
            SiteListItem siteItem = new SiteListItem();
            siteItem.setSiteId(site.getSiteId());
            siteItem.setSiteName(site.getSiteName());
            if (site.getLocalCreatedAt() != null) {
                String d= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(site.getLocalCreatedAt());
                siteItem.setCreatedAt(d);
            }
            siteItem.setUserId(site.getUserId());
            siteItem.setStatus(site.getStatus());
            siteItems.add(siteItem);
        }
        return siteItems;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
