package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.views.fragments.HomeFragment;

public class GetHomeProductTask extends AsyncTask<String, Void, String> {

    private HomeFragment homeFragment;

    public GetHomeProductTask(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }


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
            JSONArray productJsonArr = object.getJSONArray("products");

            // Clear existing data
            homeFragment.arrHomeProduct.clear();

            for (int i = 0; i < productJsonArr.length(); i++) {
                JSONObject home_product = productJsonArr.getJSONObject(i);
                String home_prod_id = home_product.getString("_id");
                String home_prod_title = home_product.getString("title");
                String home_prod_desc = home_product.getString("desc");
                double home_prod_price = home_product.getDouble("cost");
                String home_prod_img = home_product.getString("images");
                String home_prod_cate_id = home_product.getString("categoryId");
                homeFragment.arrHomeProduct.add(new HomeProduct(home_prod_id, home_prod_title, home_prod_desc, home_prod_price, home_prod_img, home_prod_cate_id));
                homeFragment.originalArrHomeProduct.add(new HomeProduct(home_prod_id, home_prod_title, home_prod_desc, home_prod_price, home_prod_img, home_prod_cate_id));
            }

            // Notify the adapter that the data has changed
            homeFragment.homeProductAdapter.notifyDataSetChanged();

            homeFragment.loader.end();

            // set adapter for listview
            homeFragment.lvHomeProduct.setAdapter(homeFragment.homeProductAdapter);
            homeFragment.lvHomeProduct.setFullHeight();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
