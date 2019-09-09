package com.segal.lqtauditproject.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

/**
 * Created by Hossein on 10/26/2017.
 */

public class PermissionHelpers {
    public static final Integer LOCATION = 0x1;
    public static final Integer CALL = 0x2;
    public static final Integer WRITE_EXST = 0x3;
    public static final Integer READ_EXST = 0x4;
    public static final Integer CAMERA = 0x5;
    public static final Integer ACCOUNTS = 0x6;
    public static final Integer GPS_SETTINGS = 0x7;
    public static final Integer ACCESS_FINE_LOCATION = 0x8;
    public static final Integer ACCESS_COARSE_LOCATION = 0x9;
    public static final Integer Internet = 0x10;

    public static boolean askForPermission(Activity context, String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
            }
        }
         else {
            return true;
        }

        return false;
    }
}
