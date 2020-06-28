package com.reza.seafoodcatalogue.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.reza.seafoodcatalogue.Data;
import com.reza.seafoodcatalogue.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "data";

    TextView txtInstruction, txtArea;
    ImageView imgItem;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtArea = findViewById(R.id.tv_area);
        txtInstruction = findViewById(R.id.tv_instruction);
        imgItem = findViewById(R.id.expandedImage);
        progressBar = findViewById(R.id.progressBar);

        showDetailSeafood();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void showDetailSeafood() {
        progressBar.setVisibility(View.VISIBLE);

        final Data data = getIntent().getParcelableExtra(EXTRA_DATA);

        String title = data.getTitle();
        String image = data.getImage();

        Glide.with(this)
                .load(image)
                .into(imgItem);

        final ArrayList<Data> listItems = new ArrayList<>();
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + data.getId();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("meals");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject seafood = list.getJSONObject(i);
                        String area = data.setArea(seafood.getString("strArea")) + " Seafood";
                        String instruction = data.setInstruction(seafood.getString("strInstructions"));
                        final String video = data.setVideo(seafood.getString("strYoutube"));

                        FloatingActionButton fab = findViewById(R.id.fab);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse(video);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        txtArea.setText(area);
                        txtInstruction.setText(instruction);
                        listItems.add(data);
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}