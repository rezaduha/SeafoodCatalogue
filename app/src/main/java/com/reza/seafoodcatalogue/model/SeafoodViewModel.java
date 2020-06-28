package com.reza.seafoodcatalogue.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.reza.seafoodcatalogue.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SeafoodViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Data>> listSeafood = new MutableLiveData<>();

    public void setSeafood() {
        final AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Data> listItems = new ArrayList<>();
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("meals");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject seafood = list.getJSONObject(i);
                        Data data = new Data();
                        data.setId(seafood.getString("idMeal"));
                        data.setTitle(seafood.getString("strMeal"));
                        data.setImage(seafood.getString("strMealThumb"));
                        listItems.add(data);
                    }
                    listSeafood.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<Data>> getSeafood() {
        return listSeafood;
    }
}