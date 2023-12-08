package com.man293.food_ordering_spoon.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.man293.food_ordering_spoon.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateProductTask extends AsyncTask<String, Integer, Product> {
    private static final String TAG = "CreateProductTask";
    private Context context;
    private Product product ;
    private File imageFile;

    private IOnProductUpdated onProductUpdated;

    public UpdateProductTask(Context context, File imageFile, Product product) {
        this.context = context;
        this.product = product;
        this.imageFile = imageFile;
    }

    public UpdateProductTask setOnProductUpdated(IOnProductUpdated onProductUpdated) {
        this.onProductUpdated = onProductUpdated;
        return this;
    }

    @Override
    protected Product doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody;
            if(imageFile != null) {
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", product.getName())
                        .addFormDataPart("desc", product.getDesc())
                        .addFormDataPart("cost", String.valueOf(product.getPrice()))
                        .addFormDataPart("categoryId", product.getCategoryId())
                        .addFormDataPart("image", product.getImageSrc(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                        .build();
            } else {
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("title", product.getName());
                data.put("cost", product.getPrice());
                data.put("desc", product.getDesc());
                data.put("image", product.getImageSrc());
                data.put("categoryId", product.getCategoryId());
                requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(data));
            }

            Request request = new Request.Builder()
                    .url(params[0])
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            Log.d(TAG, String.valueOf(response.body()));
            return new Product();

        } catch ( IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(Product result) {
        if(onProductUpdated != null) {
            onProductUpdated.callback(product);
        }
    }
    public interface IOnProductUpdated {
        void callback(Product product);
    }
}
