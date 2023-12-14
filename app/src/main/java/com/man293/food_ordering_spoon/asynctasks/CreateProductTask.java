package com.man293.food_ordering_spoon.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.man293.food_ordering_spoon.models.Product;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateProductTask extends AsyncTask<String, Integer, Product> {
    private static final String TAG = "CreateProductTask";
    private Context context;
    private Product product ;
    private File imageFile;

    private IOnProductCreated onProductCreated;

    public CreateProductTask(Context context, File imageFile, Product product) {
        this.context = context;
        this.product = product;
        this.imageFile = imageFile;
    }

    public CreateProductTask setOnProductCreated(IOnProductCreated onProductCreated) {
        this.onProductCreated = onProductCreated;
        return this;
    }

    @Override
    protected Product doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();

            // Tạo yêu cầu POST
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", product.getName())
                    .addFormDataPart("desc", product.getDesc())
                    .addFormDataPart("cost", String.valueOf(product.getPrice()))
                    .addFormDataPart("categoryId", product.getCategoryId())
                    .addFormDataPart("image", product   .getImageSrc(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                    .build();

            Request request = new Request.Builder()
                    .url(params[0])
                    .post(requestBody)
                    .build();

            // Thực hiện yêu cầu
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Lấy phản hồi từ máy chủ
             Log.d(TAG, String.valueOf(response.body()));
            return new Product();

        } catch ( IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Product result) {
        if(onProductCreated != null) {
            onProductCreated.callback(product);
        }
    }
    public interface IOnProductCreated {
        void callback(Product product);
    }
}
