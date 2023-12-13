package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetHistoryDetailTask;
import com.man293.food_ordering_spoon.asynctasks.GetHistoryProductTask;
import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.views.adapters.HistoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.ManageAdapter;
import com.man293.food_ordering_spoon.views.adapters.ManageHistoryAdapter;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.util.ArrayList;
import java.util.List;

public class ViewHistory extends AppCompatActivity {
    public ListViewComponent listView;
    public ArrayList<History> arrayHistory;
    public ManageHistoryAdapter manageAdapter;

    public LoaderComponent loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        listView = findViewById(R.id.list_history);
        loader = new LoaderComponent(this);

        loader.start();

        loadData(getString(R.string.API_GET_BILLS_BY_USER__GET));

        ImageButton bt_back = findViewById(R.id.button_back);
        bt_back.setOnClickListener(v -> {
            super.onBackPressed();
            onBackPressed();
        });
    }

    private void loadData(String url) {
        arrayHistory = new ArrayList<>();
        manageAdapter = new ManageHistoryAdapter(ViewHistory.this,arrayHistory);
        manageAdapter = new ManageHistoryAdapter(ViewHistory.this, arrayHistory);
        manageAdapter.setItemClickListener(history -> {
            // Chuyển sang ViewHistoryDetails Activity và chuyển dữ liệu cần thiết
            Intent intent = new Intent(ViewHistory.this, ViewHistoryDetails.class);
            intent.putExtra("selected_history", history);
            startActivity(intent);
        });

        User curentUser = User.getCurrentUser(ViewHistory.this);
        if(curentUser == null) return;

        listView.setAdapter(manageAdapter);
//        listView.setAdapter(manageAdapter);
//        listView.setFullHeight();
        new GetHistoryProductTask(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_BILLS_BY_USER__GET, curentUser.getId()));
    }
}