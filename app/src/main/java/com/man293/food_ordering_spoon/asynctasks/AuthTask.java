package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.man293.food_ordering_spoon.models.User;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthTask extends AsyncTask<String, Integer, User> {
    private String token;
    private WeakReference<IOnAuthenticated> listener;
    public AuthTask(String token) {
        this.token = token;
    }

    public void setOnAuthenticated(IOnAuthenticated listener) {
        this.listener = new WeakReference<>(listener);
    }
    @Override
    protected User doInBackground(String... strings) {
        try {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("google_token", this.token);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(payload));
            Request req = new Request.Builder()
                    .url(strings[0])
                    .post(body)
                    .build();
            Response res = new OkHttpClient().newCall(req).execute();
            if (res.body() != null && res.isSuccessful()) {
                JSONObject userJson = new JSONObject(res.body().string());
                return new User(
                        userJson.getString("_id"),
                        userJson.getString("firstName"),
                        userJson.getString("lastName"),
                        userJson.getString("phone"),
                        userJson.getString("address"),
                        userJson.getInt("role"),
                        userJson.getString("picture")
                );
            } else {
                throw new Exception("something went wrong!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        IOnAuthenticated cb = listener.get();
        if(cb !=null) {
            cb.callback(user);
        }
    }
    public  interface IOnAuthenticated {
        void callback(User user);
    }
}
