package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import com.man293.food_ordering_spoon.models.Category;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAdminCategoryTask extends AsyncTask<String,Void,String> {


    AdminProductActivity adminProductActivity;
    public GetAdminCategoryTask(AdminProductActivity adminProductActivity) {
        this.adminProductActivity = adminProductActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine())!= null){
                content.append(line);
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            JSONArray array = object.getJSONArray("categories");

            adminProductActivity.categories.add( new Category("ALL", "All"));
            for (int i = 0 ; i < array.length() ; i++){
                JSONObject object_Cate = array.getJSONObject(i);
                String id = object_Cate.getString("_id");
                String name = object_Cate.getString("name");
                adminProductActivity.categories.add(new Category(id, name));
            }
            adminProductActivity.categoryAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
