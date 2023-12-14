package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.views.activities.ViewHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetHistoryProductTask extends AsyncTask<String,Void,String> {
    private ViewHistory viewHistory;
    public GetHistoryProductTask(ViewHistory viewHistory) {
        this.viewHistory = viewHistory;
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
            JSONArray arrayHis = object.getJSONArray("bills");

            for (int i = 0 ; i < arrayHis.length(); i++){
                JSONObject objectHis = arrayHis.getJSONObject(i);
                String His_ID = objectHis.getString("orderId");
                String His_add = objectHis.getString("address");
                double His_cost = objectHis.getDouble("price");
                String His_Od = objectHis.getString("orderDate");
                List<ProductHistory> listPH = new ArrayList<>();
                JSONArray arrayHisPro = objectHis.getJSONArray("items");
                for (int j = 0; j < arrayHisPro.length(); j++){
                    if (j < arrayHisPro.length()) {
                        JSONObject objectHisPro = arrayHisPro.getJSONObject(j);
                        String HP_ID = objectHisPro.getString("_id");
                        String HP_title = objectHisPro.getString("title");
                        double HP_cost = objectHisPro.getDouble("cost");
                        String HP_img = objectHisPro.getString("images");
                        String HP_qtt = objectHisPro.getString("count");
                        listPH.add(new ProductHistory(HP_ID, HP_img, HP_title, HP_cost, HP_qtt));
                    }
                }
                viewHistory.arrayHistory.add(new History(His_ID,His_Od,His_cost,listPH, His_add));
            }
            viewHistory.loader.end();
            viewHistory.listView.setAdapter(viewHistory.manageAdapter);
            viewHistory.listView.setFullHeight();
            viewHistory.manageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}