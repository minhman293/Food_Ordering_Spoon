package com.man293.food_ordering_spoon.asynctasks;

import android.os.AsyncTask;

import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.models.OrderDetailItem;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.models.UpdateUIListener;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.views.activities.OrderDetailAdminActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*  Các bước tạo danh sách các món được order và lấy thông tin 
        người dùng tương ứng trong OrderDetailAdminActivity
    1. Tạo ListView trong OrderDetailAdminActivity để chứa danh sách 
       các món ăn được order
    2. Tạo arrayList arrayOD trong OrderDetailAdminActivity để truyền dữ liệu các 
       món ăn được order 
    3. Tạo interface UpdateUIListener để truyền dữ liệu của người order tương ứng
       sau khi nhấn vào từ StatisticalActivity và set dữ liệu người dùng vào các 
       TextView tương ứng ở hàm updateUi 
    4. Tạo adapter OrderDetaiAdapter để set adapter cho ListView 
       (listview <- adapter <- arraylist <- data(json))
    5. File asynctask này lấy dữ liệu json sau đó add dữ liệu các món được order
       vào arrayOD để set adapter cho ListView và add dữ liệu người dùng vào Interface
       UpdateUIListener để set dữ liệu người order cho các TextView ở 
       layout OrderDetailAdminActivity thông qua hàm loadData() trong 
       OrderDetailAdminActivity
    */

public class GetOrderDetailTask extends AsyncTask<String,Void,String> {
    private String orderID;
    private UpdateUIListener updateUIListener;
    private OrderDetailAdminActivity orderDetailAdminActivity;

    public GetOrderDetailTask(OrderDetailAdminActivity orderDetailAdminActivity, String orderID, UpdateUIListener updateUIListener) {
        this.orderDetailAdminActivity = orderDetailAdminActivity;
        this.orderID = orderID;
        this.updateUIListener = updateUIListener;
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
            String lastName = object.getString("lastName");
            String firstName = object.getString("firstName");
            double price = object.getDouble("price");
            String orderDate = ParseDate(object.getString("orderDate"));
            String phoneNum = object.getString("phone");
            String address = object.getString("address");
            JSONArray arrayItem = object.getJSONArray("items");
            for (int i = 0; i < arrayItem.length(); i++) {
                JSONObject objectItem = arrayItem.getJSONObject(i);
                String title = objectItem.getString("title");
                String qtt = objectItem.getString("count");
                double cost = objectItem.getDouble("price");
                orderDetailAdminActivity.arrayOD.add(new OrderDetailItem(title, qtt, cost));
            }
            updateUIListener.onUpdateUI(lastName, firstName, price, orderDate, phoneNum, address);

            orderDetailAdminActivity.loader.end();
            orderDetailAdminActivity.listView.setAdapter(orderDetailAdminActivity.adapter);
            orderDetailAdminActivity.listView.setFullHeight();
            orderDetailAdminActivity.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected String ParseDate(String date) {
        // Chuỗi ngày từ JSON
        String orderDateString = "2023-12-09T12:41:46.522Z";

        // Định dạng đầu vào "2023-12-09T12:41:46.522Z"
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm - dd/MM/yyyy");

        try {
            // Chuyển đổi chuỗi thành đối tượng Date
            Date orderDate = inputFormat.parse(orderDateString);

            // Chuyển đối tượng Date thành chuỗi định dạng mong muốn
            String formattedDate = outputFormat.format(orderDate);

            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
