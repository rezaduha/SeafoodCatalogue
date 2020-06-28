package com.reza.seafoodcatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reza.seafoodcatalogue.Data;
import com.reza.seafoodcatalogue.R;
import com.reza.seafoodcatalogue.adapter.CardAdapter;
import com.reza.seafoodcatalogue.model.SeafoodViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private CardAdapter cardAdapter;
    private SeafoodViewModel seafoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        RecyclerView recyclerView = findViewById(R.id.rv_seafood);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        cardAdapter = new CardAdapter();
        cardAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(cardAdapter);

        seafoodViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SeafoodViewModel.class);

        seafoodViewModel.setSeafood();

        seafoodViewModel.getSeafood().observe(this, new Observer<ArrayList<Data>>() {
            @Override
            public void onChanged(ArrayList<Data> data) {
                if (data != null) {
                    cardAdapter.setData(data);
                    showLoading(false);
                }
            }
        });
        showLoading(true);
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean doubleTapParam = false;

    @Override
    public void onBackPressed() {
        if (doubleTapParam) {
            super.onBackPressed();
            return;
        }

        this.doubleTapParam = true;
        Toast.makeText(this, getString(R.string.double_tap_alert), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTapParam = false;
            }
        }, 2000);
    }
}