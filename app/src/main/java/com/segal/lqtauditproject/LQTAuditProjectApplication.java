package com.segal.lqtauditproject;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.os.StrictMode;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Hossein on 10/30/2017.
 */

public class LQTAuditProjectApplication extends Application {

    private static Context mContext;
    private static Resources mResources;

    public static Resources getAppResources() {
        return mResources;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mResources = getResources();
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("LQTAuditDB.realm")
//                .deleteRealmIfMigrationNeeded()
                .build();

//        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);
    }


    public static Context getContext() {
        return mContext;
    }
}
