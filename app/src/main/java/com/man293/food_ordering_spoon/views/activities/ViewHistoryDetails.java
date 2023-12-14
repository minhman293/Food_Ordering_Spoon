package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetHistoryDetailTask;
import com.man293.food_ordering_spoon.asynctasks.GetHistoryProductTask;
import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.views.adapters.ManageHistoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.ViewHistoryDetailsAdapter;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.util.ArrayList;

public class ViewHistoryDetails extends AppCompatActivity {

    public ListViewComponent listView;
    public ArrayList<ProductHistory> arrayPH;
    public ViewHistoryDetailsAdapter adapter;
    public LoaderComponent loader;
    private String orderId;
    private ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history_details);

        bt_back = findViewById(R.id.button_back);
        bt_back.setOnClickListener(v -> {
            super.onBackPressed();
            onBackPressed();
        });

        listView = findViewById(R.id.list_historyItems);
        loader = new LoaderComponent(this);

        loader.start();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_history")) {
            orderId = intent.getStringExtra("selected_history");

            loadData();
        }
    }

    private void loadData() {
        User currentUser = User.getCurrentUser(ViewHistoryDetails.this);
        if(currentUser == null) return;
        arrayPH = new ArrayList<>();
        adapter = new ViewHistoryDetailsAdapter(ViewHistoryDetails.this,arrayPH);
        adapter = new ViewHistoryDetailsAdapter(ViewHistoryDetails.this, arrayPH);
        listView.setAdapter(adapter);
//        listView.setAdapter(manageAdapter);
//        listView.setFullHeight();
        new GetHistoryDetailTask(this,orderId).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_BILLS_BY_USER__GET, currentUser.getId()));
//        new GetHistoryDetailTask(this,orderId).execute("http://192.168.1.211:5000/user/655a3582cd47699385f49e81/get-bill?gidzl=0rRvGjT_Vai4GVf0rsD-JW54_qlCPon15aRzISOrAqOKG_qUnpPz5nKL-aZ0PdfDHXVyGsRZ4BKyqdv_JW");
    }
}
