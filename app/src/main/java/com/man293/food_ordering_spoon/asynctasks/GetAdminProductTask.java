package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAdminProductTask extends AsyncTask <String,Void,String> {
    private AdminProductActivity adminProductActivity;
    public GetAdminProductTask(AdminProductActivity adminProductActivity) {
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
                inputStreamReader.close();
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
            JSONArray arrayPro = object.getJSONArray("products");

            for (int i = 0 ; i < arrayPro.length(); i++){
                JSONObject objectPro = arrayPro.getJSONObject(i);
                String Pro_ID = objectPro.getString("_id");
                String Pro_Title = objectPro.getString("title");
                String Pro_Desc = objectPro.getString("desc");
                double Pro_Cost = objectPro.getDouble("cost");
                String Pro_Image = objectPro.getString("images");
                String Pro_Category = objectPro.getString("categoryId");
                adminProductActivity.arrayProduct.add(new Product(Pro_ID,Pro_Image,Pro_Title,Pro_Desc,Pro_Cost,Pro_Category));
            }
                adminProductActivity.loader.end();
//                adminProductActivity.listView.setAdapter(adminProductActivity.manageAdapter);
                adminProductActivity.listView.setFullHeight();
                adminProductActivity.manageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
