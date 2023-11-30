package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.man293.food_ordering_spoon.models.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetCategoryTask extends AsyncTask<String, Integer, ArrayList<Category>> {
    private WeakReference<IOnLoadedListener> listener;

    public void setOnLoadedListener(IOnLoadedListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    protected ArrayList<Category> doInBackground(String... strings) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            Request req = new Request.Builder()
                    .get()
                    .url(strings[0])
                    .build();
            Response res = new OkHttpClient().newCall(req).execute();
            if(res.isSuccessful() && res.body() != null) {

                String jsonString = res.body().string();
                Log.d("CATEGORIES", jsonString);
                JSONArray categoriesJson = new JSONObject(jsonString).getJSONArray("categories");
                for(int i = 0 ; i < categoriesJson.length(); i++) {
                    JSONObject c = categoriesJson.getJSONObject(i);
                    categories.add(new Category(c.getString("_id"), c.getString("name")));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categories;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
        IOnLoadedListener cb = listener.get();
        if(cb != null) {
            cb.callback(categories);
        }
    }

    public interface IOnLoadedListener {
        void callback(ArrayList<Category> categories);
    }
}
