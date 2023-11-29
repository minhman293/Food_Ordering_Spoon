package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteProductTask  extends AsyncTask<String,Integer, Boolean> {
    private ArrayList<String> productIds;
    private WeakReference<IOnDeletedListener> callback;

    public DeleteProductTask(ArrayList<String> productIds) {
        this.productIds = productIds;
    }

    public void setOnDeleteListener(IOnDeletedListener listener) {
        this.callback = new WeakReference<>(listener);
    }
    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();

            JsonArray jsonArray = new JsonArray();
            for (String productId : productIds ){
                jsonArray.add(productId);
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("productIds",jsonArray);
            String jsonString = new Gson().toJson(jsonObject);
            Log.d("IDS_JSON", jsonString);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonString);
            Request req = new Request.Builder()
                    .url(strings[0])
                    .post(body)
                    .build();

            Response res = client.newCall(req).execute();
            if(res.isSuccessful()) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isDeleted) {
        super.onPostExecute(isDeleted);
        IOnDeletedListener cb = callback.get();
        if(cb != null) {
            cb.callback(isDeleted);
        }
    }

    public interface IOnDeletedListener {
        void callback(boolean isDeleted);
    }
}
