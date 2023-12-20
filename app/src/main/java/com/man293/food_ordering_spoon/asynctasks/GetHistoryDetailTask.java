package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.views.activities.ViewHistory;
import com.man293.food_ordering_spoon.views.activities.ViewHistoryDetails;

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

/*  Các bước tạo danh sách các món ăn của từng đơn hàng trong ViewHistoryDetails
    1. Tạo ListView trong ViewHistoryDetails để chứa danh sách 
       các món ăn của đơn hàng đã được đặt của user tương ứng khi click vào
       đơn hàng tương ứng từ ViewHistory
    2. Tạo arrayList arrayPH trong ViewHistoryDetails để truyền dữ liệu các 
       món ăn trong đơn hàng của user 
    3. Tạo adapter ViewHistoryDetailsAdapter để set adapter cho ListView 
       (listview <- adapter <- arraylist <- data(json))
    4. File asynctask này lấy dữ liệu json sau đó add dữ liệu các đơn hàng
       vào arrayPH để set adapter cho ListView trong ViewHistoryDetails
*/

public class GetHistoryDetailTask extends AsyncTask<String,Void,String> {
    private String orderID;
    private ViewHistoryDetails viewHistory;
    public GetHistoryDetailTask(ViewHistoryDetails viewHistory, String orderID) {
        this.viewHistory = viewHistory;
        this.orderID = orderID;
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

            for (int i = 0; i < arrayHis.length(); i++) {
                JSONObject objectHis = arrayHis.getJSONObject(i);
                String His_ID = objectHis.getString("orderId");
                // Kiểm tra nếu đúng đơn hàng
                if (His_ID.equals(orderID)) {
                    JSONArray arrayHisPro = objectHis.getJSONArray("items");
                    for (int j = 0; j < arrayHisPro.length(); j++) {
                        JSONObject objectHisPro = arrayHisPro.getJSONObject(j);
                        String HP_ID = objectHisPro.getString("_id");
                        String HP_title = objectHisPro.getString("title");
                        double HP_cost = objectHisPro.getDouble("cost");
                        String HP_img = objectHisPro.getString("images");
                        String HP_qtt = objectHisPro.getString("count");
                        viewHistory.arrayPH.add(new ProductHistory(HP_ID, HP_img, HP_title, HP_cost, HP_qtt));
                    }
                }
            }
            viewHistory.loader.end();
            viewHistory.listView.setAdapter(viewHistory.adapter);
//            viewHistory.listView.setFullHeight();
            viewHistory.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
