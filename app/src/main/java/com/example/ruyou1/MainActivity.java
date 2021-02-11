package com.example.ruyou1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.ruyou1.API.APIHelper;
import com.example.ruyou1.Model.GET.Item;
import com.example.ruyou1.Model.GET.ItemList;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView itemsList;
    private ItemAdapter itemAdapter;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemsList = findViewById(R.id.list);
        itemsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                LoadItems();// метод загрузки пунктов
            }
        });

        itemAdapter = new ItemAdapter();
        itemsList.setAdapter(itemAdapter);
        swipeRefresh.setRefreshing(true);
        LoadItems();
    }

    ArrayList<Item> user_array;
    private void LoadItems(){

        APIHelper.getInstance().getItems("get_bodyParts", new APIHelper.APIHelperCallback<ItemList>() {
            @Override
            public void onResponse(ItemList response) {
                String user_status = response.getStatus();
                user_array = new ArrayList<>(response.getData());
                itemAdapter.setData(response);
                Snackbar
                        .make(itemsList, "Данные успешно получены!", Snackbar.LENGTH_SHORT)
                        .show();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError() {
                Snackbar
                        .make(itemsList, "Обновите еще раз", Snackbar.LENGTH_SHORT)
                        .show();
                swipeRefresh.setRefreshing(false);

            }
        });
    }
}
