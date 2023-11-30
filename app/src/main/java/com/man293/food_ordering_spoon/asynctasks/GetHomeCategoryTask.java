package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.man293.food_ordering_spoon.models.HomeCategory;
import com.man293.food_ordering_spoon.views.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetHomeCategoryTask extends AsyncTask<String, Void, String> {

    private HomeFragment homeFragment;

    public GetHomeCategoryTask(HomeFragment homeFragment){this.homeFragment = homeFragment;}

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            JSONArray categoriesJsonArr = object.getJSONArray("categories");

            for (int i = 0; i < categoriesJsonArr.length(); i++) {
                JSONObject home_cate = categoriesJsonArr.getJSONObject(i);
                String home_cate_id = home_cate.getString("_id");
                String home_cate_name = home_cate.getString("name");
                homeFragment.arrHomeCategory.add(new HomeCategory(home_cate_id, home_cate_name));
            }

            // debug
            Log.d("CategorySize", String.valueOf(homeFragment.arrHomeCategory.size())); // log 4 cate

            // notify data change
            homeFragment.homeCategoryAdapter.notifyDataSetChanged();

            // set adapter
            homeFragment.rvHomeCategory.setAdapter(homeFragment.homeCategoryAdapter);
            homeFragment.lvHomeProduct.setFullHeight();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
