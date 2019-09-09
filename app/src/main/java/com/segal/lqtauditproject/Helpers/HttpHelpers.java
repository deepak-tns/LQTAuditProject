package com.segal.lqtauditproject.Helpers;

import android.util.Log;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Models.Antenna;
import com.segal.lqtauditproject.Models.Site;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hossein on 11/16/2017.
 */

public abstract class HttpHelpers {
//    public static final String ROOTURL = "http://lqt.heeteh.com/";
    public static final String ROOTURL = "http://122.163.123.66:15000/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static Call getAsync(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Response get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }

    public static Call postAsync(String url, String json, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Response post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public static Response postSite(String url, Site site, String token) throws IOException {
        Site mSite = new Site(site);
        mSite.setAntennas(null);
        String siteJson = new Gson().toJson(mSite);
        List<String> antennasJsons = new ArrayList<>();
        for (Antenna antenna : site.getAntennas()) {
            antennasJsons.add(new Gson().toJson(antenna));
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(660, TimeUnit.SECONDS)
                .readTimeout(660, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", token);

        builder.addFormDataPart("site", siteJson);
        for (String antennasJson : antennasJsons) {
            builder.addFormDataPart("antennas", antennasJson);
        }

        ZipHelpers zipHelpers = new ZipHelpers();
        String zipPath= zipHelpers.zipSitePhotos(site);
        if(zipPath != null){
            File zipFile = new File(zipPath);
            builder.addFormDataPart("file", zipPath,
                    RequestBody.create(MediaType.parse("application/zip"), zipFile));
        }
        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .method("POST", RequestBody.create(JSON, new byte[0]))
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }


}
