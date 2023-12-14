package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.man293.food_ordering_spoon.models.OrderItem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetOrderItemTask extends AsyncTask<String, Integer, HashMap<String, Object>> {
    private int month, year;
    private WeakReference<IOnGot> callback;
    public GetOrderItemTask(int month, int year){
        this.month = month;
        this.year = year;
    }
    public GetOrderItemTask setOnGotListener(IOnGot onGotListener) {
        this.callback = new WeakReference<>(onGotListener);
        return this;
    }
    @Override
    protected HashMap<String, Object> doInBackground(String... params) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("month", this.month);
            data.put("year", this.year);

            String json = new Gson().toJson(data);
             RequestBody body =  RequestBody.create(MediaType.parse("application/json"), json);
            Request req = new Request.Builder()
                    .url(params[0])
                    .post(body)
                    .build();
            Response res = new OkHttpClient().newCall(req).execute();
            if(!res.isSuccessful()) {
                throw  new Exception("Unexpected code" + res);
            }
            if(res.body() == null) return null;
            SimpleDateFormat dfm = new SimpleDateFormat("dd-MM-yyyy");
            JSONObject jsonObject = new JSONObject(res.body().string());
            JSONArray ordersJson = jsonObject.getJSONArray("orders");
            ArrayList<OrderItem> orders = new ArrayList<>();
            for (int i = 0 ; i < ordersJson.length(); i++) {
                JSONObject item = ordersJson.getJSONObject(i);
                orders.add(new OrderItem(
                        item.getString("orderId"),
                        item.getString("firstName") + " " + item.getString("lastName"),
                        item.getString("_id"), // image
//                        dfm.parse(item.getString("orderDate")),
                        LocalDateTime.ofInstant(Instant.parse(item.getString("orderDate")), ZoneId.systemDefault()),
                        item.getDouble("price")
                ));
            }
            HashMap<String, Object> result = new HashMap<>();
            result.put("revenueYear", jsonObject.getDouble("revenueYear"));
            result.put("revenueMonth", jsonObject.getDouble("revenueMonth"));
            result.put("orders", orders);
            return  result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> data) {
        super.onPostExecute(data);
        IOnGot cb = callback.get();
        if(cb != null) {
            cb.callback(data);
        }
    }
    public interface IOnGot {
        void callback(HashMap<String, Object> data);
    }
}
