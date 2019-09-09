package com.segal.lqtauditproject.Data;

import android.os.Handler;
import android.util.Log;

import com.segal.lqtauditproject.Models.Site;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hossein on 11/13/2017.
 */

public class WebsiteDataSource {

    String rootUrl = "http://lqtaudit.hosseinalipour.ir/api/";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    Call get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private Handler handler;
    public List<Site> GetSites() throws IOException {
        Call response = get(rootUrl + "/SitesApi", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("OKHTTP", "Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("OKHTTP", "GetSites: " + response.body().string());

            }
        });
        return null;
    }
}
