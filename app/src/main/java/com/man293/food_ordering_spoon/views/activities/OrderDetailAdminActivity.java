package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetHistoryDetailTask;
import com.man293.food_ordering_spoon.asynctasks.GetOrderDetailTask;
import com.man293.food_ordering_spoon.models.OrderDetail;
import com.man293.food_ordering_spoon.models.OrderDetailItem;
import com.man293.food_ordering_spoon.models.UpdateUIListener;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.views.adapters.OrderDetailAdapter;
import com.man293.food_ordering_spoon.views.adapters.ViewHistoryDetailsAdapter;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderDetailAdminActivity extends AppCompatActivity implements UpdateUIListener {

    public ListViewComponent listView;
    public ArrayList<OrderDetailItem> arrayOD;
    public ArrayList<Object> arrayUser = new ArrayList<>();
    public OrderDetailAdapter adapter;
    public LoaderComponent loader;
    private Object lastName, firstName, cost, order_date, phone, add_ress;
    private TextView nameUser, price, orderDate, orderID, phoneNum, address;
    private String ORDER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_admin);
        findViewById(R.id.BackButton).setOnClickListener(v-> super.onBackPressed());

        nameUser = findViewById(R.id.name_user);
        price = findViewById(R.id.price);
        orderDate = findViewById(R.id.order_date);
        orderID = findViewById(R.id.order_id);
        phoneNum = findViewById(R.id.phoneNum);
        address = findViewById(R.id.address);

        listView = findViewById(R.id.list_orderDetail);
        loader = new LoaderComponent(this);

        loader.start();

        ORDER_ID = getIntent().getStringExtra("ORDER_ID");

        loadData();
        Toast.makeText(OrderDetailAdminActivity.this, "ORDER_ID: " + ORDER_ID, Toast.LENGTH_SHORT).show();
    }


    private void loadData() {
        arrayOD = new ArrayList<>();
        adapter = new OrderDetailAdapter(OrderDetailAdminActivity.this,arrayOD);
        adapter = new OrderDetailAdapter(OrderDetailAdminActivity.this, arrayOD);
        listView.setAdapter(adapter);
//        listView.setAdapter(manageAdapter);
//        listView.setFullHeight();
        GetOrderDetailTask getOrderDetailTask = new GetOrderDetailTask(this, ORDER_ID, new UpdateUIListener() {
            @Override
            public void onUpdateUI(String lastName, String firstName, double price, String orderDate, String phoneNum, String address) {
                // Xử lý cập nhật UI tại đây
            }
        });
        getOrderDetailTask.execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_ORDER_BY_ID__GET, ORDER_ID));
//        new GetOrderDetailTask(this,ORDER_ID).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_ORDER_BY_ID__GET, ORDER_ID));
//        new GetHistoryDetailTask(this,orderId).execute("http://192.168.1.211:5000/user/655a3582cd47699385f49e81/get-bill?gidzl=0rRvGjT_Vai4GVf0rsD-JW54_qlCPon15aRzISOrAqOKG_qUnpPz5nKL-aZ0PdfDHXVyGsRZ4BKyqdv_JW");
    }

    @Override
    public void onUpdateUI(String LastName, String FirstName, double Price, String OrderDate, String PhoneNum, String Address) {
        // Cập nhật TextView trong activity từ dữ liệu nhận được
        nameUser.setText("Get Money from " + LastName + " " + FirstName);
        price.setText(CurrencyUtils.format(Price));
        orderDate.setText(OrderDate);
        phoneNum.setText(PhoneNum);
        address.setText(Address);
    }
}