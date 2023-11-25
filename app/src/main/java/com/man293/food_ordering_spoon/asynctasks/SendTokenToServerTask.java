package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendTokenToServerTask extends AsyncTask<String, Integer, Void> {
    private String json;
    public  SendTokenToServerTask(String json) {
        this.json = json;
    }
    @Override
    protected Void doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), this.json);
        Request req = new Request.Builder()
                .url(params[0])
                .post(body)
                .build();
        try {
            Response res = client.newCall(req).execute();
            if(res.body()!= null) {
                Log.d("SEND_TOKEN", res.body().string());
            } else {
                throw new Exception("Unexpected code " + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
