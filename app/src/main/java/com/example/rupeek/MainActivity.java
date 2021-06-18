package com.example.rupeek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    RecyclerView recyclerView;

    List<Model> modelList = new ArrayList<>();

    private static String jsonURL = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/placesofinterest39c1c48.json";
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        extract();
    }
    private void extract(){

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i= 0; i< 5 ; i++){

                                String s = String.valueOf(response.get(i));

                                JSONObject jsonObjectRequest = response.getJSONObject(i);
                                Model model = new Model();
                                model.setAddress(String.valueOf(jsonObjectRequest.get("address")));
                                model.setId(String.valueOf(jsonObjectRequest.get("id")));
                                model.setImage(String.valueOf(jsonObjectRequest.get("image")));
                                model.setLatitude(String.valueOf(jsonObjectRequest.get("latitude")));
                                model.setLongitude(String.valueOf(jsonObjectRequest.get("longitude")));
                                model.setName(String.valueOf(jsonObjectRequest.get("name")));
                                Log.i("jsonnn", String.valueOf(jsonObjectRequest.get("id")));

                                modelList.add(model);
                            }

                            final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemViewCacheSize(5);

                            adapter = new MyAdapter(MainActivity.this , modelList);
                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e) {
                            Log.i("jsonError1" , String.valueOf(1));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("jsonnnnError" , error.getLocalizedMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}