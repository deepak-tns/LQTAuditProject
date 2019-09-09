package com.segal.lqtauditproject.ViewModels;

import java.util.List;

/**
 * Created by Hossein on 11/13/2017.
 */

public class ApiSiteResult
{
    public long Id;

    public String SiteId;

    public String SiteName;

    public String Address;

    public String PlanLatitude;

    public String PlanLongitude;

    public String Status;

    public String SubContractorComments;

    public String VendorComments;

    public List<ApiAntennaResult> Antennas;
}
