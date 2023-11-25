package com.man293.food_ordering_spoon.asynctasks;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.man293.food_ordering_spoon.models.CartItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class CartInteractiveTasks {

    public static class GetTask extends AsyncTask<String, Integer, ArrayList<CartItem>> {

        private WeakReference<ICallback<ArrayList<CartItem>>> callback;

        public void setOnCartLoadedListener(ICallback<ArrayList<CartItem>> callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<CartItem> doInBackground(String... params) {
            ArrayList<CartItem> cart = new ArrayList<>();
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(params[0]);
                Request req =  builder.build();

                Response res = client.newCall(req).execute();
                if(res.body() == null) return  cart;
                JSONArray cartJson = new JSONObject(res.body().string()).getJSONArray("cart");
                for(int i = 0; i < cartJson.length(); i++) {
                    JSONObject item = cartJson.getJSONObject(i);
                    Log.d("CART_ITEM", String.valueOf(item));
                    cart.add(new CartItem(
                            item.getString("_id"),
                            item.getString("images"),
                            item.getString("title"),
                            item.getString("desc"),
                            item.getDouble("cost"),
                            item.getString("categoryId"),
                            item.getInt("quantity")
                    ));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return cart;
        }

        @Override
        protected void onPostExecute(ArrayList<CartItem> cartItems) {
            super.onPostExecute(cartItems);
            ICallback<ArrayList<CartItem>> cb = callback.get();
            if(cb != null) {
                cb.execute(cartItems);
            }
        }

    }

    public static class AddTask extends AsyncTask<String, Integer, Boolean> {
        private WeakReference<ICallback<Boolean>> callback;
        private String productId;
        private int quantity;
        public void setOnAddedListener(ICallback<Boolean> callback) {
            this.callback = new WeakReference<>(callback);
        }

        public AddTask(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                OkHttpClient client = new OkHttpClient();

                Map<String, Object> data = new HashMap<>();
                data.put("productId", this.productId);
                data.put("quantity", this.quantity);

                String json = new Gson().toJson(data);
                Log.d("JSON_DATA", json);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

                Request req = new Request.Builder()
                        .url(strings[0])
                        .post(body)
                        .build();

                Response res = client.newCall(req).execute();
                if(!res.isSuccessful()) {
                    throw new Exception("Unexpected code " + res);
                }
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isAdded) {
            super.onPostExecute(isAdded);
            ICallback<Boolean> cb = callback.get();
            if(cb != null) {
                cb.execute(isAdded);
            }
        }
    }

    public static class CreateBillTask extends AsyncTask<String, Integer, Boolean> {
        private String address, productIds, notificationTitle, notificationBody; // [123457,12345] <-- ArrayList.toString()
        private Double price;
        private WeakReference<ICallback<Boolean>> callback;
        public CreateBillTask(String productIds, Double price, String address,String notificationTitle, String notificationBody) {
            this.productIds = productIds;
            this.price = price;
            this.address = address;
            this.notificationTitle = notificationTitle;
            this.notificationBody = notificationBody;
        }
        public CreateBillTask setOnBillCreated(ICallback<Boolean> callback) {
            this.callback = new WeakReference<>(callback);
            return  this;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                OkHttpClient client = new OkHttpClient();
                HashMap<String,Object> data = new HashMap<>();
                data.put("productIds", productIds);
                data.put("address", address);
                data.put("price", price);
                data.put("notificationTitle", notificationTitle);
                data.put("notificationBody", notificationBody);
                String json = new Gson().toJson(data) ;
                Log.d("CREATE_BILL", json);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                Request req = new Request.Builder()
                        .url(strings[0])
                        .post(body)
                        .build();
                Response res = client.newCall(req).execute();
                if(!res.isSuccessful()) {
                    throw new Exception("Unexpected code " + res);
                }
                return  true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isCreated) {
            super.onPostExecute(isCreated);
            ICallback<Boolean> cb = callback.get();
            if(cb != null) {
                cb.execute(isCreated);
            }
        }
    }
    public interface ICallback<T> {
        void execute(T result);
    }
}
